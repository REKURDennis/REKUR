package org.glowa.danube.deepactors.actors.actor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.glowa.danube.deepactors.actors.Sensors;
import org.glowa.danube.deepactors.actors.TimeQuery;
import org.glowa.danube.deepactors.actors.constraint.Constraint;
import org.glowa.danube.deepactors.actors.history.HistoryCore;
import org.glowa.danube.deepactors.actors.history.HistoryCoreData;
import org.glowa.danube.deepactors.actors.history.HistoryEntry;
import org.glowa.danube.deepactors.actors.history.HistoryFactory;
import org.glowa.danube.deepactors.actors.plan.PlanCore;
import org.glowa.danube.deepactors.actors.plan.PlanFactory;
import org.glowa.danube.deepactors.sensors.event.EventSource;
import org.glowa.danube.deepactors.sensors.event.SensorEvent;
import org.glowa.danube.deepactors.sensors.sensor.Sensor;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.DeepActorLogger;
import org.glowa.danube.deepactors.util.MutableDataMap;
import org.glowa.danube.deepactors.util.PlanMap;
import org.glowa.danube.deepactors.util.PlanStorage;
import org.glowa.danube.deepactors.util.ProxelMapEntry;
import org.glowa.danube.deepactors.util.Zone;
import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActorCoreImpl.java,v 1.9 2007/10/29 08:09:33 janisch Exp $ 
 */
public class ActorCoreImpl implements ActorCore{

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ActorCoreImpl.class);
    
    // cfg parameter; if true all available plans are loaded and initialized before base.init().
    private final boolean loadAndRetainAllPlans;
    
    // the core impl administrates the base object:
    private AbstractActor<HistoryEntry> base;
    
    // the core impl administrates references to required interfaces:
    private Sensors sensors;
    private TimeQuery timeQuery;
    
    // the core impl uses the core roles of the history principle
    private HistoryCore history;
    
    // the core impl has a plan storage which uses the plan principle to create new plan objects
    private PlanStorage planStore;
    
    // the core impl executes active plans and stores their id's in the history
    private Set<Integer> failIds = new HashSet<Integer>();
    private Set<Integer> execIds = new HashSet<Integer>();
    private DanubiaCalendar historyTime;
    
    // available Sensors do not change during runtime: get them in init 
    private Sensor<Collaborator> actorSens;
    private Sensor<ProxelMapEntry> proxelSens;
    private Sensor<Constraint<ConstraintProperties>> constraintSens;
    
    // the core impl creates the base reference
    ActorCoreImpl(ActorInit ai, PlanFactory pf, HistoryFactory hf, Sensors s,
            TimeQuery tq, boolean loadAndRetainAllPlans){
        base = createAndSetBase(ai, pf, hf);
        // After create the factories provide also the framework roles:
        // note that plan core objs are retrieved dynamically from planStore
        history = hf.getHistoryCore();
        sensors = s;
        timeQuery = tq;
        this.loadAndRetainAllPlans = loadAndRetainAllPlans;
    }

    // ---------------------------------------------------------------
    // -- Roles of this principle which are implemented by the base reference
    // ---------------------------------------------------------------    
    Collaborator getCollaborator() { return base; }
    Actor getActor() { return base; }
    // Note that the ActionEnvironment role is used only hierachical, therefore no getter!
    
    // ---------------------------------------------------------------
    // Template implementation of provided interfaces
    // ---------------------------------------------------------------  
    public int getId() { return base.id(); }
    public void init(){
        // initialize base state
        base.setSimulationTime(timeQuery.getSimulationTime());

        DataMap<Constraint<ConstraintProperties>> emptyConstraintMap = 
            new MutableDataMap<Constraint<ConstraintProperties>>();
        
        DataMap<Collaborator> emptyCollabMap = new MutableDataMap<Collaborator>();
        
        DataMap<ProxelMapEntry> emptyProxelMap = new MutableDataMap<ProxelMapEntry>();
        
        DataMap<SensorEvent<EventSource>> emptyEventMap = new MutableDataMap<SensorEvent<EventSource>>();
        
        base.setSensorData(emptyCollabMap, emptyProxelMap, emptyConstraintMap);
        base.setSensorEvents(emptyEventMap, emptyEventMap, emptyEventMap);
        
        // plans are loaded and initialized before their owning actors
        if(loadAndRetainAllPlans) base.plans().load(base.plans().available());
        base.init();
        
        // initialize local core state
        actorSens = sensors.getActorSensor();
        proxelSens = sensors.getProxelSensor();
        constraintSens = sensors.getConstraintSensor();
    }
    
    public void query(){
        base.setSimulationTime(timeQuery.getSimulationTime());
        
        // upd proxel set of action env and sensor events
        base.setSensorData(
                actorSens.getDataMap(base),
                proxelSens.getDataMap(base),
                constraintSens.getDataMap(base));

        base.setSensorEvents(
                actorSens.getEventMap(base),
                proxelSens.getEventMap(base),
                constraintSens.getEventMap(base));

        base.query();
    }

    public void decide(){
        base.options();
        // in options the relevant plan objects for this time step has been loaded 
        Collection<PlanCore> curPlans = planStore.getPlanCoreObjs();
        for(PlanCore p:curPlans){
            if(p.isActive()) p.computeRating(); 
        }
        base.filter();
        // execute active plans and store id's of failed plans
        boolean executed = false;
        failIds.clear();
        execIds.clear();
        historyTime = base.simulationTime();
        curPlans = planStore.getPlanCoreObjs();
        for(PlanCore p:curPlans) {
            if(p.isActive()) {
                executed = p.execute();
                if(!executed) failIds.add(p.getId()); 
                else execIds.add(p.getId());
            }
        }
        curPlans = null;
        unloadPlans();
    }
     
    public void export(){
        base.setSimulationTime(timeQuery.getSimulationTime());
        base.export();
    }
    public void store() {
        createHistoryEntry();        
    }

    private void unloadPlans(){
        Set<Integer> effectivelyRemove = new HashSet<Integer>(planStore.getLoadedIds());
        effectivelyRemove.removeAll(base.plans().retained());
        planStore.remove(effectivelyRemove);
    }

    // ---------------------------------------------------------------
    // -- Private implementations
    // ---------------------------------------------------------------
    @SuppressWarnings({ "unchecked", "null" })
    private AbstractActor<HistoryEntry> createAndSetBase(ActorInit ai, 
            PlanFactory pf, HistoryFactory hf) {
        AbstractActor<HistoryEntry> baseActor = null;
        try { baseActor = ai.getActorClass().newInstance(); } 
        catch (InstantiationException e) { log.exception(e); }
        catch (IllegalAccessException e) { log.exception(e); }
        baseActor.setFrozenBasic(ai.getId(), new Zone(ai.getLocation()), ai.getCollabIds());
        this.planStore = new PlanStorage(ai.getId(), baseActor, pf);
        PlanMap pm = new PlanMap(planIdArrayToSet(ai.getPlanIds()),planStore);
        hf.createHistory();
        baseActor.setFrozenRef(pm, hf.getHistory());
        return baseActor;
    }

    private Set<Integer> planIdArrayToSet(int[] planIds) {
        Set<Integer> result = new HashSet<Integer>();
        for(int i=0; i<planIds.length; i++) result.add(planIds[i]);
        return result;
    }

    private void createHistoryEntry() {
        history.add(
                new HistoryCoreData(historyTime, failIds, execIds), 
                base.createHistoryEntry());
    }
}


/**
 * $Log: ActorCoreImpl.java,v $
 * Revision 1.9  2007/10/29 08:09:33  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.8  2006/08/16 13:47:33  janisch
 * - cfg parameter loadAndRetainAllPlans
 * - PlanStorage planStore instead of Set<PlanCore> plans;
 * - initial plan loading depends on cfg; retain is default implemented in
 *   PlanMap
 * - removed base.setActionEnvProxelSet(proxelMap) in query(). Computed
 *   dynamically now.
 * - in decide() get the plan core objects from the plan storage and unload
 *   them finally
 * - instantiate plan map/storage in createAndSetBase
 *
 * Revision 1.7  2006/08/01 16:43:53  janisch
 * Supress null warning in createAndSetBase.
 *
 * Revision 1.6  2006/01/31 14:37:45  janisch
 * Refactored generic type usage, introduced SuppressWarnings("unchecked")
 *
 * Revision 1.5  2006/01/27 13:13:55  janisch
 * Get sensors only once (during initialization).
 *
 * Revision 1.4  2005/12/22 14:31:40  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.3  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:48  janisch
 * Release 1.0.0
 *
 */