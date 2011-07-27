package org.glowa.danube.deepactors.actors.action;

import org.glowa.danube.deepactors.util.TimePeriod;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ActionInit.java,v 1.3 2006/01/31 14:34:24 janisch Exp $ 
 */
public class ActionInit {

    private int id;
    private Class<? extends AbstractAction> actionType;
    private TimePeriod period;
    private boolean mandatory;
    
    public ActionInit(int id, 
            Class<? extends AbstractAction> actionClass,
            TimePeriod period, boolean mandatory) {
        this.id = id;
        this.actionType = actionClass;
        this.period = period;
        this.mandatory = mandatory;
    }
    public final Class<? extends AbstractAction> getActionType() {
        return actionType;
    }
    public final int getId() {return id;}
    public final boolean isMandatory() {return mandatory;}
    public final TimePeriod getPeriod() {return period;}
}
/**
 * $Log: ActionInit.java,v $
 * Revision 1.3  2006/01/31 14:34:24  janisch
 * Refactored generic type usage, introduced SuppressWarnings("unchecked")
 *
 * Revision 1.2  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.1  2005/08/26 11:14:58  janisch
 * Release 1.0.0
 *
 * Revision 1.3  2005/02/18 16:00:32  janisch
 * An actions proxel map is now automatically set by the framework
 * implementation to refer to all proxel objects of the location of the
 * associated actor. The column 'pids" must be removed from any
 * action initialization file (action.init.csv) in order to be usable up
 * from this release..
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */