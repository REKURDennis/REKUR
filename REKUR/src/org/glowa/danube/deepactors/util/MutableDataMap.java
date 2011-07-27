package org.glowa.danube.deepactors.util;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;


/**
 * Implements a mutable generic DataMap. 
 * 
 * @author janisch
 * @version $Id: MutableDataMap.java,v 1.10 2006/01/31 14:43:59 janisch Exp $
 */
public class MutableDataMap<E extends MapEntry> implements DataMap<E>
{
    private Hashtable<Integer,E> dataTable = new Hashtable<Integer, E>();
    
    /**
     * Constructs an empty data map.
     * @post.condition getEntries()->isEmpty()
     */
    public MutableDataMap() {
        
    }
    
    /**
     * Constructs this data map as a shallow copy of the given data.
     * @ocl-pre-notNull: data <> null
     * @ocl-post-shallowCopy: self.getEntries() = data.getEntries() 
     */
    public MutableDataMap(final DataMap<E> data){
        Set<E> entries = data.getEntries();
        for(E elt : entries) {
            this.add(elt);
        }
    }
    
    public Set<E> getEntries(){
        Set<E> values = new HashSet<E>(dataTable.values());
        return Collections.unmodifiableSet(values);
    }
    
    public <T extends E> DataMap<T> getEntries(Class<? extends T> type) {
        MutableDataMap<T> submap = new MutableDataMap<T>();
        Set<E> entries = getEntries();
        for(E e:entries) {
            if(type.isInstance(e)) submap.add(type.cast(e));
        }
        if(submap.size() == 0) {
            String mes = "There is no entry of type " + type.getName() 
            + " in this data map.";
            throw new NoSuchElementException(mes);
        }
        return submap;
    }

    public <T extends E> T getEntry(int id, Class<?extends T> type) {
        E entry = getEntry(id);
        if(entry == null) return null;
        if(!type.isInstance(entry)) {
            String mes = "The entry with id " + id + " is not of type " 
            + type.getName();
            throw new NoSuchElementException(mes);
        }
        return type.cast(entry);
    }

    public boolean contains(int key) {
        return dataTable.containsKey(key);
    }
    
    public E getEntry(int key) {
        return dataTable.get(key);
    }
    
    /** Add <code>elt</code> to this data map.
     * <p>
     * The element will be added to this map iff <code>elt</code> isn't null, 
     * <code>elt.getKey()</code> isn't already contained in this map and 
     * <code>elt.getKey()</code> is greater than 0. 
     * </p>
     * <p>
     * This map is unmodified if the element wasn't added to this map.
     * The element <code>elt</code> isn't modified in any case.
     * </p>
     *  
     * @post.condition (elt=null or containsKey(elt.getKey()) or 
     * elt.getKey < 0) implies getEntries() = getEntries@pre()
     * "else getEntries() = getEntries@pre()->including(elt) and
     *  getEntry(elt.getKey()) = elt" %% stored as reference.
     */
    public void add(final E elt) {
        if(elt == null) return;
        int key = elt.getId();
        if(dataTable.containsKey(key) || key < 0) return;
        dataTable.put(key, elt);
    }
    /**
     * Add all entries of <code>dataMap</code> to this mutable data map.
     * See {@link #add(E)}.
     */
    public void addAll(DataMap<E> dataMap) {
        Set<E> otherEntries = dataMap.getEntries();
        for(E e : otherEntries) { add(e); }
    }
    
    public E remove(int key) {return dataTable.remove(key);}
    
    public void removeAll() {dataTable.clear();}

    public E get(int id) {return getEntry(id);}
    
    /**
     * Returns modifiable set of all entries stored in this map.
     */
    public Set<E> getAll(){ return new HashSet<E>(dataTable.values()); }

    /**
     * ToDo:javadoc
     * 
     * @see org.glowa.danube.deepactors.util.DataMap#size()
     * 
     * @pre.condition {@code Todo:name} - ToDo:body.
     * 
     * @post.condition {@code Todo:name} - ToDo:body.
     * 
     * @return
     */
    public int size() {
        return dataTable.size();
    }
    
}

/**
 * 
 * $Log: MutableDataMap.java,v $
 * Revision 1.10  2006/01/31 14:43:59  janisch
 * Refactored generic type usage
 *
 * Revision 1.9  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.8  2005/08/26 11:15:11  janisch
 * Release 1.0.0
 *
 * Revision 1.7  2005/03/31 07:58:54  janisch
 * Typing error on javadoc of method add: should be elt.getKey()
 * instead of elt.getId()
 *
 * Revision 1.6  2005/03/08 10:33:22  janisch
 * Removed unnecessary cast to E.
 *
 * Revision 1.5  2005/03/04 17:31:32  janisch
 * - implement get and getAll
 * - rename clear to removeAll
 *
 * Revision 1.4  2005/03/04 13:27:48  janisch
 * Added @invariant, @pre, @post as a custom tag.
 *
 * Revision 1.3  2005/01/27 13:56:59  janisch
 * Formated javadoc in add(E elt).
 *
 * Revision 1.2  2005/01/27 10:27:23  janisch
 * Made some method parameter final and added javadoc.
 *
 * Revision 1.1.1.1  2005/01/03 10:12:25  janisch
 * Imported using TkCVS
 *
 * 
 */
