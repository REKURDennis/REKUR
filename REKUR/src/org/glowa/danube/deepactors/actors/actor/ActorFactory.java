package org.glowa.danube.deepactors.actors.actor;

import java.util.HashSet;
import java.util.Set;

import org.glowa.danube.deepactors.actors.ResourceAllocator;
import org.glowa.danube.deepactors.actors.ResourceKeys;
import org.glowa.danube.deepactors.actors.Sensors;
import org.glowa.danube.deepactors.actors.TimeQuery;
import org.glowa.danube.deepactors.actors.history.HistoryFactory;
import org.glowa.danube.deepactors.actors.plan.PlanFactory;
import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ActorFactory.java,v 1.5 2007/10/31 10:16:50 janisch Exp $ 
 */
public class ActorFactory{
    
    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ActorFactory.class);

    // Required interfaces 
    // resource is need only once - private ResourceAllocator resource;
    Sensors sensors;
    TimeQuery timeQuery;

    // Contained components
    PlanFactory planPrinciple;    
    HistoryFactory historyPrinciple = new HistoryFactory();    
    
    // Provided interfaces (roles)
    Set<ActorCore> actorsCore;
    Set<Actor> actors;
    Set<Collaborator> collaborators;
    
    // the actor init objects resulting from the resources' init file
    Set<ActorInit> actorInitObjects;

    // cfg parameter which determines if all plans are initially loaded
    private boolean initiallyLoadAndRetainAllPlans;
    
    // pre: ra.getFile(ResourceKeys.ActorsInit) <> null
    public ActorFactory(ResourceAllocator ra, Sensors s, TimeQuery tq){
        log.info("Instantiate and bind actor principle ...");
        sensors = s;
        timeQuery = tq;
        planPrinciple = new PlanFactory(ra);
        ActorInitTable ait = 
            new ActorInitTable(ra.getFile(ResourceKeys.ActorsInit), ra.getClassLoader());
        actorInitObjects = ait.getInitObjects(ait.getIdSet());
        initiallyLoadAndRetainAllPlans = ra.getConfigBoolean(ResourceKeys.PlansInitLoad);
    }
     
    /**
     * Creates all actor roles according to the init file which was read 
     * in the constructor of this factory. This method may be called more than 
     * once, resulting in a set of new instantiated actor objects. With this a 
     * reset of the actors component is feasible to be implemented in one of the
     * future releases.
     * 
     * @pre.condition {@code Todo:name} - ToDo:body.
     * 
     * @post.condition {@code Todo:name} - ToDo:body.
     * 
     */
    public void createActors() {
        log.info("Creating actors ...");
        clearRoles();
        // create actor impls and set provided Roles
        for(ActorInit ai:actorInitObjects) {
            ActorCoreImpl core = new ActorCoreImpl(ai, planPrinciple, 
                    historyPrinciple, sensors, timeQuery, initiallyLoadAndRetainAllPlans);
            actorsCore.add(core);
            actors.add(core.getActor());
            collaborators.add(core.getCollaborator());
        }
        log.info("Created "+actorInitObjects.size()+" actor objects.");
    }

    private void clearRoles() {
        // create new!! sets; clear would write through previous role queries
        actorsCore = new HashSet<ActorCore>();
        actors = new HashSet<Actor>();
        collaborators = new HashSet<Collaborator>();
    }
    
    public Set<ActorCore> getActorsCore(){ return actorsCore; }
    public Set<Actor> getActors(){ return actors; }
    public Set<Collaborator> getCollaborators(){ return collaborators; }
}
/**
 * $Log: ActorFactory.java,v $
 * Revision 1.5  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.4  2006/08/16 13:49:00  janisch
 * Cfg parameter initiallyLoadAndRetainAllPlans from
 * ResourceAlloc to ActorCoreImpl
 *
 * Revision 1.3  2005/11/10 12:27:54  janisch
 * Switched debug log to an info log.
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:45  janisch
 * Release 1.0.0
 *
 * 
 */