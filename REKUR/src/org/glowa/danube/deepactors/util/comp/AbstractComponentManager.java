package org.glowa.danube.deepactors.util.comp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: AbstractComponentManager.java,v 1.3 2006/01/31 14:44:24 janisch Exp $ 
 */
public abstract class AbstractComponentManager implements ComponentManager {

    private final DeepActorLogger log = DeepActorLogger.newInstance(this);
    
    /** Provided interfaces are bound by the concrete subtype of this class */ 
    protected Map<Class, Object> provInterfMap = new HashMap<Class, Object>();
    /** Required interfaces are defined by the concrete subtype of this class */
    protected Set<Class> reqInterfSet = new HashSet<Class>();
    
    // store binding state to implement isBound
    private Set<Class> boundedReqs = new HashSet<Class>();
    
    /**
     * Returns object implementing the provided interface <tt>provInterf</tt>.
     * Returns null if <tt>provInterf</tt> isn't a provided interface of this
     * component.
     *  
     * @see org.glowa.danube.deepactors.util.comp.ComponentManager#getProvided(java.lang.Class)
     * 
     * @pre.condition {@code Todo:name} - ToDo:body.
     * 
     * @post.condition {@code Todo:name} - ToDo:body.
     * 
     * @param <P>
     * @param provInterf
     * @return
     */
    final public <P> P getProvided(Class<? extends P> provInterf) {
        return provInterf.cast(provInterfMap.get(provInterf));
    }

    /**
     * Template method which stores succesfull binding status for 
     * <tt>reqInterf</tt> iff <tt>reqInterf</tt> is required, 
     * <tt>impl <> null</tt> and <tt>reqInterf</tt> has not been bound yet. 
     * Note that this implies no support for dynamic reconfiguration. If the 
     * binding is acceptable the hook  
     * @link AbstractComponentManager#handleBinding(Class<R>, R) will be called.
     * 
     * @see org.glowa.danube.deepactors.util.comp.ComponentManager#bindRequired(java.lang.Class, R)
     * 
     * @pre.condition {@code Todo:name} - ToDo:body.
     * 
     * @post.condition {@code Todo:name} - ToDo:body.
     * 
     * @param <R>
     * @param reqInterf
     * @param impl
     */
    final public <R> void bindRequired(Class<? extends R> reqInterf, R impl) {
        if(reqInterfSet.contains(reqInterf) && impl != null && 
                !boundedReqs.contains(reqInterf)) {
            boundedReqs.add(reqInterf);
            log.debug("Bind "+reqInterf.getName()+" ...");            
            handleBinding(reqInterf, impl);
        }
    }
    
    /** Hook for subclasses to implement the specific binding reaction. */
    protected <R> void handleBinding(Class<? extends R> reqInterf, R impl) {}

    /**
     * Returns binding status of required interfaces. True iff all required 
     * interfaces are bound to an object <> null.   
     * 
     * @see org.glowa.danube.deepactors.util.comp.ComponentManager#isBound()
     * 
     * @pre.condition {@code Todo:name} - ToDo:body.
     * 
     * @post.condition {@code Todo:name} - ToDo:body.
     * 
     * @return
     */
    public boolean isBound() { return boundedReqs.equals(reqInterfSet); }
    
}

/**
 * $Log: AbstractComponentManager.java,v $
 * Revision 1.3  2006/01/31 14:44:24  janisch
 * Refactored generic type usage
 *
 * Revision 1.2  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:42  janisch
 * Release 1.0.0
 *
 */