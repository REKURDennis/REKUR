package org.glowa.danube.deepactors.actors.action;

import org.glowa.danube.deepactors.actors.actor.ActionEnvironment;
import org.glowa.danube.deepactors.util.TimePeriod;
import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: AbstractAction.java,v 1.7 2007/10/29 08:06:16 janisch Exp $ 
 */
public abstract class AbstractAction<T extends ActionEnvironment> 
implements Action{
    
    //private final DeepActorLogger log = DeepActorLogger.newInstance(this);    
    // ------------------------------------------------------------------------
    // -- State controlled by the core impl
    // ------------------------------------------------------------------------    
    // -- Frozen basic attributes 
    private int id;
    private int planId;
    private TimePeriod period;
    private boolean mandatory;
    void setFrozenBasic(int id, TimePeriod tp, boolean mandatory, int planId){
        this.id = id;
        this.planId = planId;
        period = tp;
        this.mandatory = mandatory;
    }
    // -- Frozen reference attributes 
    private T environment;
    // may throw ClassCastException in case not ae.type =< T 
    void setFrozenRef(T ae) { environment = ae; }

    // ------------------------------------------------------------------------
    // -- Implementation of provided interfaces
    // ------------------------------------------------------------------------    
    final public int getId() {return id;}
    final public TimePeriod getPeriod() {return period;}
    final public boolean isExecutable(){
        DanubiaCalendar time = environment.getSimulationTime();
        return period.contains(time) && isApplicable();
    }
    final public boolean isMandatory() {return mandatory;}

    // -------------------------------------------------------------------------
    // -- Attribute access for core impl and subclasses
    // -------------------------------------------------------------------------        
    final protected int id() {return id;}
    final protected int planId() {return planId;}
    final protected TimePeriod period() {return period;}
    final protected boolean mandatory() {return mandatory;}
    final protected boolean executable() {
        return isExecutable();
    }
    final protected T environment() {return environment;}

    // -------------------------------------------------------------------------
    // -- Plug points
    // -------------------------------------------------------------------------    
    protected void init(){}
    // the default implementation is always applicable
    protected boolean isApplicable(){
        return true;
    }
    // pre: isExecutable() and environment().getProxel().notEmpty()
    protected void execute(){} 
}

/**
 * $Log: AbstractAction.java,v $
 * Revision 1.7  2007/10/29 08:06:16  janisch
 * - Aligned package imports to Danubia 2.0
 *
 * Revision 1.6  2005/12/22 14:31:41  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.5  2005/09/28 09:43:18  janisch
 * Added query period()
 *
 * Revision 1.4  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.3  2005/09/08 13:38:13  janisch
 * setFrozenRef may use the type parameter T
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:57  janisch
 * Release 1.0.0
 *
 */