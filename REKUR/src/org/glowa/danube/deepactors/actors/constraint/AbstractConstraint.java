package org.glowa.danube.deepactors.actors.constraint;

import org.glowa.danube.deepactors.actors.actor.ConstraintProperties;


/** ToDo:javadoc
 * @author janisch
 * @version $Id: AbstractConstraint.java,v 1.4 2006/01/31 14:38:27 janisch Exp $ 
 */
public abstract class AbstractConstraint 
    <P extends ConstraintProperties> 
    implements Constraint<P> {
    
    /**
     * @returns false if one of the following cases yields true 
     * and true otherwise: 
     * (1) <code>p == null</code>
     * (2) the cast <code>(P)p</code> throws an ClassCastException
     * (3) <code>checkImpl(p)</code> returns false  
     */
    public final boolean check(P p) {
        if(p != null && holds(p)) return true;
        return false;
    }
    
    public final int getId() { 
        return id(); 
    }
    
    /**
     * Implementation of the check associated with this constraint.
     */
    protected abstract boolean checkImpl(P p);
    protected abstract int id();
        
    /** 
     * todo: how to implement a stronger type requirement (oclIsTypeOf) ? 
     * Equality on the class.getName() strings doesn't work because
     * this class is generic in the relevant Parameter. */
    private boolean holds(P p){
        try {
            if (checkImpl(p)) return true;        
            return false;
        }
        catch(ClassCastException e){
            return false;
        }
    }
}
/**
 * $Log: AbstractConstraint.java,v $
 * Revision 1.4  2006/01/31 14:38:27  janisch
 * Introduced Type Parameter to interface
 *
 * Revision 1.3  2005/09/27 08:43:44  janisch
 * Added plug-point id(); implemented getId();
 *
 * Revision 1.2  2005/09/08 13:38:59  janisch
 * Removed actors.actor.constraint.ConstraintProperties; already defined in the
 * package actors.actor.
 *
 * Revision 1.1  2005/08/26 11:16:17  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */