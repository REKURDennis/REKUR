package org.glowa.danube.deepactors.actors.plan;

import java.util.Set;

import org.glowa.danube.deepactors.actors.action.Action;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.MutableDataMap;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: AbstractPlan.java,v 1.4 2007/10/29 09:02:31 janisch Exp $ 
 */
public abstract class AbstractPlan implements Plan{

    // ------------------------------------------------------------------------
    // -- State controlled by the core impl
    // ------------------------------------------------------------------------    
    // -- Frozen basic properties set by the core impl
    private int id;
    private int actorId;
    void setFrozenBasic(int id, int actorId){
        this.id = id;
        this.actorId = actorId;
    }
    // -- Frozen reference attributes set by the core impl
    private DataMap<Action> actionMap;
    void setFrozenRef(Set<Action> actions) {
        MutableDataMap<Action> am = new MutableDataMap<Action>();
        for(Action a:actions) am.add(a);
        actionMap = am;
    }
    // -- Variable attributes controlled by the core impl
    private float rating;
    void setRating(float r) { rating = r; }

    // ------------------------------------------------------------------------
    // -- State NOT controlled by the core impl
    // ------------------------------------------------------------------------    
    // activation/deactivation is part of the provided Action interface
    private boolean isActive = false;
   
    // ------------------------------------------------------------------------
    // -- Implementation of provided interfaces
    // ------------------------------------------------------------------------    
    final public int getId() {return id;}
    final public float getRating() {return rating;}
    final public boolean isActive() {return isActive;}
    final public boolean isExecutable(){
        // result <=> forall(a:actions) !a.isMandatory() or a.isExecutable()
        Set<Action> actions = actionMap.getEntries();
        for(Action a:actions) {
            if(a.isMandatory() && !a.isExecutable()) return false;
        }
        return true;
    }
    final public void activate() { isActive = true; }
    final public void deactivate() { isActive = false; }
    
    // -------------------------------------------------------------------------
    // -- Attribute access for core impl and subclasses
    // -------------------------------------------------------------------------    
    final protected int id() {return id;}
    final protected int actorId() {return actorId;}
    final protected float rating() {return rating;}
    final protected boolean active() {return isActive;}
    final protected boolean executable() {return isExecutable();}
    final protected DataMap<Action> actionMap() {return actionMap;}
    
    // -------------------------------------------------------------------------
    // -- Plug points
    // -------------------------------------------------------------------------    
    protected void init(){}
    protected float computeRating(){ 
        return rating; 
    }
}

/**
 * $Log: AbstractPlan.java,v $
 * Revision 1.4  2007/10/29 09:02:31  janisch
 * - Aligned package imports to Danubia 2.0
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.3  2005/12/22 14:31:40  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:25  janisch
 * Release 1.0.0
 *
 */