package org.glowa.danube.deepactors.actors.plan;

import java.util.HashSet;
import java.util.Set;

import org.glowa.danube.deepactors.actors.ResourceAllocator;
import org.glowa.danube.deepactors.actors.ResourceKeys;
import org.glowa.danube.deepactors.actors.action.ActionFactory;
import org.glowa.danube.deepactors.actors.actor.ActionEnvironment;
import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: PlanFactory.java,v 1.4 2007/10/31 10:16:50 janisch Exp $ 
 */
public final class PlanFactory {

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(PlanFactory.class);
    
    // required interfaces
    // in contrast to the actor factory, the create methods are called more
    // than once. The resource is read only once and after that any call
    // to create uses this plan table which is an oo representation of the init
    // file retrieved through the resource ra as given in the constructor
    final PlanInitTable planInitTable;
    
    // contained components
    final ActionFactory actionPrinciple;

    // provided interfaces
    public class PlanRoles{
        private PlanCore coreObj;
        private Plan baseObj;
        public PlanRoles(PlanCore coreObj, Plan baseObj) {
            this.coreObj = coreObj;
            this.baseObj = baseObj;
        }
        public Plan getPlan() { return baseObj; }
        public PlanCore getPlanCore() { return coreObj; }
    }

    public PlanFactory(ResourceAllocator ra) {
        log.info("Instantiate and bind Plan principle ...");        
        actionPrinciple = new ActionFactory(ra);
        planInitTable = new PlanInitTable(ra.getFile(ResourceKeys.PlansInit), 
                ra.getClassLoader());
    }
    
    public synchronized PlanRoles createRoles(int planId, int actorId, ActionEnvironment ae){
        return createAndSetRoles(planId, actorId, ae);
    }

    // -- convenient
    public synchronized Set<PlanRoles> createRolesSet(int[] planIds, 
            int actorId, ActionEnvironment ae){
        Set<PlanRoles> result = new HashSet<PlanRoles>();
        for(int planId:planIds){
            result.add(createAndSetRoles(planId, actorId, ae));
        }
        return result;
    }
    
    // -- Private Impl -------------------------------------------------------
    private PlanRoles createAndSetRoles(int planId, int actorId, 
            ActionEnvironment ae) {
        PlanInit pi = planInitTable.getInitObject(planId);
        PlanCoreImpl core = new PlanCoreImpl(actorId, ae, pi, actionPrinciple);
        return new PlanRoles(core, core.getPlan());
    }
}

/**
 * $Log: PlanFactory.java,v $
 * Revision 1.4  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.3  2006/08/16 13:57:51  janisch
 * Role creation refactored. among other things, synchronized for
 * concurrent access during dynamic plan loading.
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:21  janisch
 * Release 1.0.0
 *
 */