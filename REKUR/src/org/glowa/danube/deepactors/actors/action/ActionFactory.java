package org.glowa.danube.deepactors.actors.action;

import java.util.HashSet;
import java.util.Set;

import org.glowa.danube.deepactors.actors.ResourceAllocator;
import org.glowa.danube.deepactors.actors.ResourceKeys;
import org.glowa.danube.deepactors.actors.actor.ActionEnvironment;
import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActionFactory.java,v 1.4 2007/10/31 10:16:50 janisch Exp $ 
 */
public final class ActionFactory {

    private final DeepActorLogger log = DeepActorLogger.newInstance(this);

    // required 
    // created from init file as given by ra in the constructor
    final ActionInitTable actionInitTable;
    
    // provided interfaces
    public final class ActionRoles{
        private ActionCore coreObj;
        private Action baseObj;
        public ActionRoles(ActionCore coreObj, Action baseObj) {
            this.coreObj = coreObj;
            this.baseObj = baseObj;
        }
        public Action getAction() { return baseObj; }
        public ActionCore getActionCore() { return coreObj; }
    }
    Set<ActionCore> actionsCore;
    Set<Action> actions;
    
    public ActionFactory(ResourceAllocator ra) {
        log.info("Instantiate and bind action principle ...");
        actionInitTable = 
            new ActionInitTable(ra.getFile(ResourceKeys.ActionsInit),
                    ra.getClassLoader());
    }

    public synchronized ActionRoles createRoles(int id, ActionEnvironment ae, int planId) {
        return createAndSetRoles(id, ae, planId);
    }

    // convenient, using same ae for all actions
    public synchronized Set<ActionRoles> createRolesSet(int[] ids, ActionEnvironment ae, int planId) {
        Set<ActionRoles> result = new HashSet<ActionRoles>();
        for(int aid:ids){
            result.add(createAndSetRoles(aid, ae, planId));
        }
        return result;
    }
    
    // -- Private impl --------------------------------------------------------
    private ActionRoles createAndSetRoles(int id, ActionEnvironment ae, int planId) {
        ActionInit ai = actionInitTable.getInitObject(id);
        ActionCoreImpl core = new ActionCoreImpl(ai, ae, planId);
        return new ActionRoles(core, core.getAction());
    }
}

/**
 * $Log: ActionFactory.java,v $
 * Revision 1.4  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.3  2006/08/16 13:28:26  janisch
 * Role creation refactored. among other things, synchronized for
 * concurrent access during dynamic plan loading.
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:53  janisch
 * Release 1.0.0
 *
 */