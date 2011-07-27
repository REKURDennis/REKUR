package org.glowa.danube.deepactors.actors.plan;

import java.util.HashSet;
import java.util.Set;

import org.glowa.danube.deepactors.actors.action.Action;
import org.glowa.danube.deepactors.actors.action.ActionCore;
import org.glowa.danube.deepactors.actors.action.ActionFactory;
import org.glowa.danube.deepactors.actors.action.ActionFactory.ActionRoles;
import org.glowa.danube.deepactors.actors.actor.ActionEnvironment;
import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: PlanCoreImpl.java,v 1.6 2007/10/29 09:03:28 janisch Exp $ 
 */
public class PlanCoreImpl implements PlanCore{

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(PlanCoreImpl.class);

    private AbstractPlan base;
    private Set<ActionCore> actions;
    
    PlanCoreImpl(int actorId, ActionEnvironment ae, PlanInit pi, 
            ActionFactory af){
        base = createAndSetBase(actorId, ae, pi, af);
    }
    Plan getPlan() {return base;}

    // -------------------------------------------------------------------------    
    // -- Template implementation of provided core interface
    // -------------------------------------------------------------------------    
    public int getId() { return base.id(); }
    public void init(){
        for(ActionCore a:actions) {
            a.init();
        }
        base.init(); 
    }
    public void computeRating(){ 
        base.setRating(base.computeRating()); 
    }
    public boolean isActive() { return base.active(); }
    public boolean execute(){ 
        // a plan fails if it is not executable. This impl guarantees that a
        // plan doesn't have any side effect if it wasn't executable => it makes
        // more sense to reexecute it in the next time step
        if(!base.executable()) return false; 
        // excutability implies that all mandatory actions are executable.
        // Actions which are not mandatory are not executed obeying the pre-
        // condition of ActorCore::execute
        for(ActionCore ac:actions) if(ac.isExecutable()) ac.execute();
        return true;
    }
    
    // -------------------------------------------------------------------------
    // -- Private implementation
    // -------------------------------------------------------------------------    
    //@SuppressWarnings("null")
    private AbstractPlan createAndSetBase(int actorId, ActionEnvironment ae, 
            PlanInit pi, ActionFactory af) {
        AbstractPlan basePlan = null;
        try { basePlan = (AbstractPlan) pi.getPlanClass().newInstance(); } 
        catch (InstantiationException e) { log.exception(e); }
        catch (IllegalAccessException e) { log.exception(e); }
        basePlan.setFrozenBasic(pi.getId(), actorId);
        Set<ActionRoles> arSet = af.createRolesSet(pi.getActionIds(), ae, pi.getId());
        Set<Action> actionObjs = new HashSet<Action>();
        Set<ActionCore> actionCoreObjs = new HashSet<ActionCore>();
        
        for(ActionRoles ar:arSet){
            actionObjs.add(ar.getAction());
            actionCoreObjs.add(ar.getActionCore());
        }
        
        basePlan.setFrozenRef(actionObjs);
        this.actions = actionCoreObjs;
        return basePlan;
    }
}

/**
 * $Log: PlanCoreImpl.java,v $
 * Revision 1.6  2007/10/29 09:03:28  janisch
 * - Aligned package imports to Danubia 2.0
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.5  2006/08/16 13:56:51  janisch
 * Aligned createAndSetBase with refactored ActionFactory
 *
 * Revision 1.4  2006/08/01 16:44:15  janisch
 * Supress null warning in createAndSetBase
 *
 * Revision 1.3  2005/12/22 14:31:40  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:24  janisch
 * Release 1.0.0
 *
 */