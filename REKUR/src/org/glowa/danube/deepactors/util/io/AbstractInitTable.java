package org.glowa.danube.deepactors.util.io;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: AbstractInitTable.java,v 1.3 2006/01/31 14:44:49 janisch Exp $ 
 */
public abstract class AbstractInitTable<T>{

    // Subclasses put their init objects directly
    protected Map<Integer, T> initObjects = new HashMap<Integer, T>();

    // These public queries should be used by Factories -----------------------
   
    /** Returns an unmodifiable set of id's contained in this init table.*/
    public final Set<Integer> getIdSet() { 
         return initObjects.keySet();
    }

    /** Returns the init object associated with the given id. */
    public final T getInitObject(int id) { return initObjects.get(id); }
    
    /** Convenience: returns the set of init objects for the given ids. */    
    public final Set<T> getInitObjects(Set<Integer> idSet) {
        Set<T> subSet = new HashSet<T>();
        for(int i:idSet) subSet.add(initObjects.get(i));
        return subSet;
    }
    
    /** Convenience: returns all init objects */    
    public final Set<T> getAllInitObjects() {
        return new HashSet<T>(initObjects.values());
    }
    
}
/**
 * $Log: AbstractInitTable.java,v $
 * Revision 1.3  2006/01/31 14:44:49  janisch
 * Refactored generic type usage
 *
 * Revision 1.2  2005/08/26 11:17:11  janisch
 * Release 1.0.0
 *
 * Revision 1.1  2005/01/21 07:41:27  janisch
 * Refactored initialization tables to extend the new abstract base class AbstractInitTable.
 *
 */