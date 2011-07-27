package org.glowa.danube.deepactors.actors;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.actors.actor.ActorCore;
import org.glowa.danube.deepactors.actors.actor.ActorFactory;
import org.glowa.danube.deepactors.actors.actor.Collaborator;
import org.glowa.danube.deepactors.actors.exec.MemberInvocation;
import org.glowa.danube.deepactors.actors.exec.GroupExecutionFactory;
import org.glowa.danube.deepactors.actors.exec.GroupExecService;
import org.glowa.danube.deepactors.util.DeepActorLogger;
import org.glowa.danube.deepactors.util.comp.AbstractComponentManager;
import org.glowa.danube.deepactors.util.comp.ComponentManager;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActorsManager.java,v 1.5 2006/08/16 13:25:53 janisch Exp $ 
 */
public class ActorsManager 
extends AbstractComponentManager
implements ComponentManager, Actors, ActorsControl {
    
    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ActorsManager.class);

    // req interfaces
    private ResourceAllocator resource;
    private Sensors sensors;
    private TimeQuery timeQuery;
    
    // contained components
    private ActorFactory actorPrinciple;
    private GroupExecService execPrinciple;
    
    public ActorsManager(){
        log.info("Instantiate Actors container ...");
        super.provInterfMap.put(Actors.class, this);
        super.provInterfMap.put(ActorsControl.class, this);
        
        super.reqInterfSet.add(ResourceAllocator.class);
        super.reqInterfSet.add(Sensors.class);
        super.reqInterfSet.add(TimeQuery.class);
    }

    protected <R> void handleBinding(Class<? extends R> reqInterf, R impl) {
        if(reqInterf == ResourceAllocator.class) { 
            resource = (ResourceAllocator)impl;
        }
        if(reqInterf == Sensors.class) {  
            sensors = (Sensors) impl;
        }
        if(reqInterf == TimeQuery.class) { 
            timeQuery = (TimeQuery) impl;
        }
    }

    // -------------------------------------------------------------------------
    // -- implementing provided interfaces
    // -------------------------------------------------------------------------
    public Set<Actor> getActors() {
        if(actorPrinciple == null) return Collections.emptySet();
        return actorPrinciple.getActors();
    }
    public Set<Collaborator> getCollaborators() {
        if(actorPrinciple == null) return Collections.emptySet();
        return actorPrinciple.getCollaborators();
    }
    // pre: isBound
    public void create() {
        log.info("Instantiating Actor principle ...");
        actorPrinciple = new ActorFactory(resource, sensors, timeQuery);
        actorPrinciple.createActors();
        Set<MemberInvocation> execProxies = new HashSet<MemberInvocation>(); 
        Set<ActorCore> actors = actorPrinciple.getActorsCore();
        for(ActorCore a:actors) execProxies.add(new ActorCoreProxy(a));
        GroupExecutionFactory execComponent = new GroupExecutionFactory(execProxies);
        execPrinciple = execComponent.getExecService();
    }

    // the following impl assume a correct client w.r.t the protocol
    //
    public void init(){ 
        execPrinciple.invoke("init"); 
    }
    
    public void query(){ 
        //timeMeas.startMeasurement();
        execPrinciple.invoke("query");
        //timeMeas.endMeassurement("ActorsManager", "actors.query");
    }
    
    public void decide(){ 
        //timeMeas.startMeasurement();
        execPrinciple.invoke("decide");
        //timeMeas.endMeassurement("ActorsManager", "actors.decide");        
    }
    
    public void export(){ 
        execPrinciple.invoke("export");
    }
    
    public void store(){ 
        execPrinciple.invoke("store"); 
    }
}

/**
 * $Log: ActorsManager.java,v $
 * Revision 1.5  2006/08/16 13:25:53  janisch
 * Renamed TimeMeas to PerformanceMeas.
 *
 * Revision 1.4  2006/01/31 14:32:08  janisch
 * Removed PerformanceMeas
 *
 * Revision 1.3  2006/01/27 13:12:28  janisch
 * Added PerformanceMeas for performance analysis
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:44  janisch
 * Release 1.0.0
 *
 */