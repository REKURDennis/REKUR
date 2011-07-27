package org.glowa.danube.deepactors.util;

import java.util.Set;

import org.glowa.danube.deepactors.sensors.event.EventSource;

/**
 * A DataMap stores unique elements unequal null. 
 * 
 * Uniqueness is defined w.r.t the value e.getKey() for all e:DataMapEntry in this map.
 * A DataMap may always act as EventSource.   
 * 
 * @ocl-attr: keys:Collection(Integer) = getEntries()->collect(getKey())
 * @ocl-attr: entries:Collection(E) = getEntries()
 *   
 * @ocl-inv-noNullEntries: keys(getEntries())->forAll(k | getEntry(k) <> null)
 * @ocl-inv-posKeys: keys(getEntries())->forAll(k | k > 0)
 * @ocl-inv-uniqueEntries: entries(getEntries())->isUnique(getKey())
 * 
 * @author janisch
 * @version $Id: DataMap.java,v 1.6 2006/08/01 16:45:23 janisch Exp $ 
 */
public interface DataMap<E extends MapEntry> extends EventSource{
    
    /**
     * Returns a read-only set view of the stored elements.
     */
    public Set<E> getEntries();
    
    /**
     * Generic version of {@link DataMap#getEntries()}. Returns a data map of 
     * all entries which are objects of type <tt>T</tt> or of a subtype 
     * of <tt>T</tt>. Throws a NoSuchElementException if there is no entry of 
     * type <tt>T</tt> or of a subtype of <tt>T</tt>. 
     * <p>
     * Returning a data map instead of a set allows e.g. to chain calls to this
     * method with {@link DataMap#getEntry(int)}, e.g. you may write code like
     * <tt>SomeType obj = dataMap.getEntries(SomeType.class).getEntry(42);</tt>
     * </p> 
     */
    public <T extends E> DataMap<T> getEntries(Class<? extends T> type);
    
    /**
     * @ocl-post: result = (keys(getEntries)->exists(k | k=key))
     * Note: watch out for the equality impl when using Java-Integer!
     */
    public boolean contains(int id);
    
    /**
     * @ocl-post-null: not containsKey(key) implies result = null
     * @ocl-post-notNull: containsKey(key) implies result <> null
     */
    public E getEntry(int id);
    
    /**
     * Generic version of {@link DataMap#getEntry(int)}. If there is an entry
     * for the given key which is an object of type <tt>T</tt> or of a subtype of
     * <tt>T</tt>, then this object is returned. If there is an entry for the 
     * given key but this entry is not of type <tt>T</tt> or of a subtype of
     * <tt>T</tt>, then a NoSuchElementException will be thrown. If there is no 
     * entry at all for the given key, then null is returned. 
     */
    public <T extends E> T getEntry(int id, Class<? extends T> type);
    
    /**
     * Returns the number of entries of this data map.
     * 
     * @pre.condition {@code Todo:name} - ToDo:body.
     * 
     * @post.condition {@code Todo:name} - ToDo:body.
     * 
     * @return The number of entries of this data map.
     */
    public int size();
    
    
}
/**
 * $Log: DataMap.java,v $
 * Revision 1.6  2006/08/01 16:45:23  janisch
 * Added explicit public visibility for getEntries
 *
 * Revision 1.5  2006/01/31 14:41:54  janisch
 * Refactored generic type usage
 *
 * Revision 1.4  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.3  2005/08/26 11:15:16  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */