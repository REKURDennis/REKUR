package org.glowa.danube.deepactors.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements some utilities on top of the java reflection API.
 * 
 * @author janisch
 * @version $Id: Reflections.java,v 1.10 2006/01/31 14:43:59 janisch Exp $ 
 */
public class Reflections {

    // no constructor needed until now.
    private Reflections() {}
    
    /**
     * Returns the full qualified name of the sub-interface of interf which is 
     * implemented by obj.
     * Assumes that there is at most one such sub-interface in the
     * implements list of the class of obj. If there is no sub-interface found,
     * null will be returned. If there is more than one sub-interfaces, the 
     * first interface name found in the list of the 'implements' declaration
     * will be returned.
     */
    public static String getUniqueSubInterface(Class<?> interf, Class obj) {
        Class[] objInterfaces = obj.getInterfaces(); 
        for(Class objInterf : objInterfaces) {
            if(interf.isAssignableFrom(objInterf)) return objInterf.getName();
        }
        return null;
    }
    /**
     * Returns the full qualified name of all sub-interfaces of interf which are 
     * implemented by obj. If there is no sub-interface found, an empty set will
     * be returned. 
     */
    public static Set<String> getAllSubInterfaces(Class<?> interf, Class obj) {
        Class[] objInterfaces = obj.getInterfaces(); 
        Set<String> interfaces = new HashSet<String>();
        for(Class objInterf : objInterfaces) {
            if(interf.isAssignableFrom(objInterf)) 
                interfaces.add(objInterf.getName());
        }
        return interfaces;
    }
    
    /**
     * Returns all objects of the given (exact) type contained in the given 
     * collection. 
     * 
     * @pre.condition 
     * {@code noNullArgs} - The given argument values may not be null and the 
     * given collection may not contain null.
     * 
     * @post.condition 
     * {@code exactType} - The returned objects have exactly the given 
     * type, i.e. objects of subtypes of the given type are <emph>not</emph> 
     * contained in the given collection. <br/> 
     * {@code invalidTypeArg} - If no class can be found for the given 
     * type string an empty collection is returned. <br/>
     * {@code ordering} - If the given collection is ordered, the ordering 
     * is preserved. 
     *  
     * @param objects the collection of objects which are examined.
     * @param type full qualified name of the required interface or class.
     * 
     */
    public static <E> Collection<E> objectsOfType(Class<E> type, Collection objects) {
        Collection<E> result = new ArrayList<E>();
        for(Object o : objects) {
            if(type.isAssignableFrom(o.getClass())) result.add(type.cast(o));
        }
        return result;
    }
}
/**
 * $Log: Reflections.java,v $
 * Revision 1.10  2006/01/31 14:43:59  janisch
 * Refactored generic type usage
 *
 * Revision 1.9  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.8  2005/03/23 08:20:28  janisch
 * Added utility method getAllSubInterfaces.
 *
 * Revision 1.7  2005/03/04 17:32:07  janisch
 * Added private no-arg constructor.
 *
 * Revision 1.6  2005/03/04 14:46:55  janisch
 * Javadoc.
 *
 * Revision 1.5  2005/03/04 13:27:48  janisch
 * Added @invariant, @pre, @post as a custom tag.
 *
 * Revision 1.4  2005/03/04 12:51:39  janisch
 * Renamed implementsInterface to getUniqueSubInterface.
 *
 * Revision 1.3  2005/03/03 11:35:01  janisch
 * Added utility method objectsForType: see javadoc for details.
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */