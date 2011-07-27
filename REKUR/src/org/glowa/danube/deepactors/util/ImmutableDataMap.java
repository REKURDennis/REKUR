package org.glowa.danube.deepactors.util;

import java.util.Set;

import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.MutableDataMap;

/**
 * Implementation of an immutable generic data map.
 * 
 * The implementation provides a readonly view to the underlying data map.
 * 
 * @author janisch
 * @version $Id: ImmutableDataMap.java,v 1.6 2006/01/31 14:43:59 janisch Exp $ 
 */
public class ImmutableDataMap<E extends MapEntry> implements DataMap<E> {

    /** Delegate DataMap operations to an hidden instance of MutableDataMap. */
    private MutableDataMap<E> delegateMap;

    /**
     * Construct this data map as a shallow copy of the given data.
     */
    public ImmutableDataMap(DataMap<E> data){
        delegateMap = new MutableDataMap<E>(data);
    }
    
    // ----------------------------------------------------------------
    // delegates to internal map
    // ----------------------------------------------------------------
    public Set<E> getEntries() { return delegateMap.getEntries(); }
    public boolean contains(int key) { return delegateMap.contains(key); }
    public E getEntry(int key) { return delegateMap.getEntry(key); }
    public int size() { return delegateMap.size(); }
    public <T extends E> DataMap<T> getEntries(Class<? extends T> type) {
        return delegateMap.getEntries(type);
    }
    public <T extends E> T getEntry(int id, Class<? extends T> type) {
        return delegateMap.getEntry(id,type);
    }

}
/**
 * $Log: ImmutableDataMap.java,v $
 * Revision 1.6  2006/01/31 14:43:59  janisch
 * Refactored generic type usage
 *
 * Revision 1.5  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.4  2005/08/26 11:15:19  janisch
 * Release 1.0.0
 *
 * Revision 1.3  2005/03/04 13:27:48  janisch
 * Added @invariant, @pre, @post as a custom tag.
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */