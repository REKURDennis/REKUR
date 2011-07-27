package org.glowa.danube.deepactors.actors.action;

import org.glowa.danube.deepactors.actors.actor.ActionEnvironment;
import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActionCoreImpl.java,v 1.5 2007/10/29 08:06:53 janisch Exp $ 
 */
public class ActionCoreImpl implements ActionCore{

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ActionCoreImpl.class);

    private AbstractAction<ActionEnvironment> base;

    @SuppressWarnings("unchecked")
    ActionCoreImpl(ActionInit ai, ActionEnvironment ae, int planId){
        base = null;
        try {
            Class<? extends AbstractAction> actionClass = ai.getActionType();
            base = actionClass.newInstance(); 
        } 
        catch (InstantiationException e) { log.exception(e); }
        catch (IllegalAccessException e) { log.exception(e); }
        base.setFrozenBasic(ai.getId(), ai.getPeriod(), 
                ai.isMandatory(), planId);
        base.setFrozenRef(ae);
    }
    
    Action getAction() {return base;}

    // -------------------------------------------------------------------------    
    // -- Template implementation of provided core interface
    // -------------------------------------------------------------------------    
    public boolean isExecutable() {return base.executable();}
    public void execute() { base.execute(); }
    public void init() { base.init(); }
    public int getId() { return base.id(); }
}

/**
 * $Log: ActionCoreImpl.java,v $
 * Revision 1.5  2007/10/29 08:06:53  janisch
 * - Aligned package imports to Danubia 2.0
 *
 * Revision 1.4  2006/01/31 14:34:24  janisch
 * Refactored generic type usage, introduced SuppressWarnings("unchecked")
 *
 * Revision 1.3  2005/12/22 14:31:41  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:55  janisch
 * Release 1.0.0
 *
 */