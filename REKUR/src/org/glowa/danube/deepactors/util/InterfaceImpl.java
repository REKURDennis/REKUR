package org.glowa.danube.deepactors.util;

import org.glowa.danube.deepactors.util.rac.PreconditionViolation;
import org.glowa.danube.components.DanubiaInterface;

/**
 * Datatype storing pairs of <tt>GLOWAInterface</tt> name and implementation.
 * 
 * @invariant: classExists && iName
 * @invariant-classExists: a class with the qualified name interfName exists 
 * on the class path. 
 * @invariant-iName: interfName is the full qualified name of a subtype of 
 * GLOWAInterface and implemented by the given implementation interfImpl.  
 * 
 * @author janisch
 * @version $Id: InterfaceImpl.java,v 1.4 2007/10/29 09:38:02 janisch Exp $ 
 */
final public class InterfaceImpl {
    private DanubiaInterface interfImpl;
    private String interfName;
    
    /**
     * ToDo: ...
     * 
     * @pre.condition: see inv.
     */
    public InterfaceImpl(DanubiaInterface interfImpl, String interfName) {
        this.interfImpl = interfImpl;
        this.interfName = interfName;
        assert(preConstructor());
    }
    public DanubiaInterface getInterfImpl() {return interfImpl;}
    public String getInterfName() {return interfName;}
    
    // -------------------------------------------------------------------
    // constraint implementation
    // -------------------------------------------------------------------
    private boolean preConstructor() {
        return preConstructorClassExists() && preConstructorIName();
    }
    private boolean preConstructorClassExists() {
        try {
            Class<?> interfClass = Class.forName(interfName);
        } catch (ClassNotFoundException e) {
            throw new PreconditionViolation("Constructor InterfaceImpl.");
        }
        return true;
    }
    @SuppressWarnings("null")
    private boolean preConstructorIName() {
        Class<?> interfClass = null;
        try { interfClass = Class.forName(interfName);
        } catch (ClassNotFoundException cannothappen) {}
        
        boolean subTypesGlowaInterface = 
            DanubiaInterface.class.isAssignableFrom(interfClass);
        boolean implementsInterfName = 
            interfClass.isAssignableFrom(interfImpl.getClass());
        
        if(subTypesGlowaInterface && implementsInterfName) {
            return true;
        }
        throw new PreconditionViolation("Constructor InterfaceImpl.");
    }
}
/**
 * $Log: InterfaceImpl.java,v $
 * Revision 1.4  2007/10/29 09:38:02  janisch
 * - Aligned package imports to Danubia 2.0
 * - GlowaInterface -> DanubiaInterface
 *
 * Revision 1.3  2006/08/01 16:48:13  janisch
 * Supress null warning in preConstructorIName
 *
 * Revision 1.2  2006/01/31 14:43:59  janisch
 * Refactored generic type usage
 *
 * Revision 1.1  2005/09/23 11:00:59  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:07  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */