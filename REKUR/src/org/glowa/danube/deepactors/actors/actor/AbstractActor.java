package org.glowa.danube.deepactors.actors.actor;

import java.util.HashSet;
import java.util.Set;

import org.glowa.danube.deepactors.actors.constraint.Constraint;
import org.glowa.danube.deepactors.actors.history.History;
import org.glowa.danube.deepactors.actors.history.HistoryEntry;
import org.glowa.danube.deepactors.actors.plan.Plan;
import org.glowa.danube.deepactors.sensors.event.EventSource;
import org.glowa.danube.deepactors.sensors.event.SensorEvent;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.PlanMap;
import org.glowa.danube.deepactors.util.ProxelMapEntry;
import org.glowa.danube.deepactors.util.Zone;
import org.glowa.danube.simulation.model.proxel.AbstractProxel;
import org.glowa.danube.utilities.time.DanubiaCalendar;


/**
 * Base class for actor type implementations.
 * 
 * <p>Allowed and prohibited upcalls:<br>
 * A concrete Subtype is allowed to call protected final methods only. Any 
 * other method should not be called from the subtype implementation. The final
 * public methods of this base class implement interfaces which are used by 
 * other principles, e.g. by plans, actions etc. The non-final protected methods
 * are methods which denote optional or mandatory plug points. These are used by
 * the framework implementation only. In both cases an upcall from the subclass
 * implentation would result in an undefined system state and is therefore 
 * prohibited.</p>
 * 
 * <p>Provided and required methods:<br>
 * The protected final methods of this base class are the only methods which are
 * allowed to be called by the concrete subclass and therefore are called 
 * provided methods. They may be used e.g. within the implementation of optional
 * or mandatory required methods. An optional required method is declared 
 * protected and not final. A mandatory required method is declared protected 
 * and abstract. 
 * </p>
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: AbstractActor.java,v 1.7 2007/10/29 08:08:09 janisch Exp $ 
 */
public abstract class AbstractActor<H extends HistoryEntry> implements 
Actor, ActionEnvironment, Collaborator{
// package visible operations are used by the core impl only
    
    // -----------------------------------------------------------------
    // State controlled by the core implementation
    // -----------------------------------------------------------------
    
    //-- Frozen basic Properties --
    private int id;
    private Zone location;
    private int[] collabIds;
    void setFrozenBasic(int actorId, Zone loc, int[] collab) {
        id = actorId;
        location = loc;
        collabIds = collab;
    }
    // -- Frozen reference attributes --
    private PlanMap plans; 
    private History history;
    void setFrozenRef(PlanMap pm, History h) {
        history = h;
        plans = pm;
    }
    // -- Variable attributes --
    private DanubiaCalendar simulationTime;
    void setSimulationTime(DanubiaCalendar time) {
        simulationTime = time;
    }
    private DataMap<Collaborator> actorSensorData;  
    private DataMap<ProxelMapEntry> proxelSensorData;
    private DataMap<Constraint<ConstraintProperties>> constraintSensorData;
    void setSensorData(DataMap<Collaborator> asd, DataMap<ProxelMapEntry> psd,
            DataMap<Constraint<ConstraintProperties>> csd) {
        actorSensorData = asd;
        proxelSensorData = psd;
        constraintSensorData = csd;
    }
    private DataMap<SensorEvent<EventSource>> actorSensorEvents;
    private DataMap<SensorEvent<EventSource>> constraintSensorEvents;
    private DataMap<SensorEvent<EventSource>> proxelSensorEvents;
    void setSensorEvents(
            DataMap<SensorEvent<EventSource>> ase, 
            DataMap<SensorEvent<EventSource>> pse,
            DataMap<SensorEvent<EventSource>> cse) {
        actorSensorEvents = ase;
        constraintSensorEvents = cse;
        proxelSensorEvents = pse;
    }
    //private Set<Proxel> actionEnvProxelSet;
    //void setActionEnvProxelSet(DataMap<ProxelMapEntry> aeps) {
      //  actionEnvProxelSet = new HashSet<Proxel>();
      //  Set<ProxelMapEntry> pmeSet = aeps.getEntries();
      //  for(ProxelMapEntry pme:pmeSet) actionEnvProxelSet.add(pme.getProxel());
    //}
    
    // -----------------------------------------------------------------
    // Implementation of provided interfaces
    // -----------------------------------------------------------------
    // -- Actor and Collaborator --
    final public int getId() { return id;}
    // -- Actor --
    final public Zone getLocation() { return location; }
    final public int[] getCollabIds() { return collabIds; }    
    // -- ActionEvironment --
    final public Set<AbstractProxel> getProxel() {
        Set<AbstractProxel> result = new HashSet<AbstractProxel>();
        Set<? extends ProxelMapEntry> pmeSet = proxelSensorData.getEntries();
        for(ProxelMapEntry pme:pmeSet) result.add(pme.getProxel());
        return result; 
    }
    final public DanubiaCalendar getSimulationTime() { return simulationTime; }

    // -----------------------------------------------------------------    
    // Attribute access for subclasses, dropping getter prefix to 
    // avoid name clashes with public ops' of provided interfaces. 
    // Additionally this fits well with the intentional readonly access to
    // the corresponding attributes.
    // -----------------------------------------------------------------    
    final protected int id() { return id;}
    final protected  Zone location() { return location; }
    final protected int[] collabIds() { return collabIds; }    
    final protected DanubiaCalendar simulationTime() { return simulationTime; }
    
    /**
     * Returns <code>plans.getPlanMapProjection(Plan.class)</code> and will be removed in the 
     * next release.  
     * @deprecated 
     */
    final protected DataMap<Plan> planMap(){ return plans.getPlanDataMap(); }
    final protected PlanMap plans() { return plans; }
    
    final protected DataMap<Constraint<ConstraintProperties>> constraintSensorData() {
        return constraintSensorData;
    }
    final protected DataMap<ProxelMapEntry> proxelSensorData() {
        return proxelSensorData;
    }
    final protected DataMap<Collaborator> actorSensorData() {
        return actorSensorData;
    }
    final protected DataMap<SensorEvent<EventSource>> actorSensorEvents() {
        return actorSensorEvents;
    }
    final protected DataMap<SensorEvent<EventSource>> constraintSensorEvents() {
        return constraintSensorEvents;
    }
    final protected DataMap<SensorEvent<EventSource>> proxelSensorEvents() {
        return proxelSensorEvents;
    }

    @SuppressWarnings("unchecked")
    final protected H lastHistoryEntry() {
        H e = (H) history.getLastEntry();
        return e ; 
    }
    
    // -----------------------------------------------------------------
    // -- PlugPoints (Hooks) --
    // -----------------------------------------------------------------
    /**
     * You may assume: all basic properties initialized including the plan map
     * and the simulation time is set to the simulation start date
     */
    protected void init(){}
    
    /**
     * You may assume: sensor data and events are up to date. Simulation time
     * had been set to the time provided by Danubia for processGetData
     */
    protected void query(){}
    
    /**
     * You may assume: initially all plans are deactivated. If a plan was
     * activated in the last timestep it is still active in the current timestep
     * The simulation time had not been modified.
     */
    protected void options(){}
    
    /**
     * You may assume: computeRating has been called for all active plans.
     * The simulation time had not been modified.
     */
    protected void filter(){}

    /**
     * You may assume: all active plans had been executed. If some plan failed
     * to execute none of its actions had been executed and the corresponding
     * plan id had been stored in the history.Simulation time had been set to 
     * the time provided by Danubia for processCommit
     */
    protected void export(){}
    
    /**
     * Note that it is necessary to provide an initial history entry within
     * the first simulation step. Simulation time had been set to the time 
     * provided by Danubia for processCommit
     */
    protected HistoryEntry createHistoryEntry() { 
        return new HistoryEntry(); 
    }
}

/**
 * $Log: AbstractActor.java,v $
 * Revision 1.7  2007/10/29 08:08:09  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Use new Danubia 2.0 base class AbstractProxel
 * - Refactored generics according to java 1.6 compiler warnings
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.6  2006/08/16 13:40:15  janisch
 * - removed field actionEnvProxelSet, instead AbstractActor::getProxel
 *   uses proxelSensor directly
 * - added query plans(), modified planMap() to return
 *   plans().getPlanDataMap() and tagged planMap() deprecated.
 *   Remove the latter in the next release
 * - replaced setFrozenRef(Set<Plan> ps, History h) with
 *   setFrozenRef(PlanMap pm, History h)
 *
 * Revision 1.5  2006/01/31 14:37:17  janisch
 * - changed return type of createHistoryEntry to HistoryEntry
 * - added SuppressWarnings("unchecked") to lastHE
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
 * Revision 1.1  2005/08/26 11:15:51  janisch
 * Release 1.0.0
 *
 */