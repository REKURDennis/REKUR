package org.glowa.danube.deepactors.actors.history;

import java.util.NoSuchElementException;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: HistoryImpl.java,v 1.3 2007/03/05 14:27:42 janisch Exp $ 
 */
public class HistoryImpl implements History, HistoryCore{

    private static final int MAX_NUM_ENTRIES = 2;
    
    private HistoryEntry[] entries = new HistoryEntry[MAX_NUM_ENTRIES];
    private int index = -1;
    
    synchronized public void add(HistoryCoreData hcd, HistoryEntry he) {
        he.setCoreData(hcd);
        index++;
        if(index == MAX_NUM_ENTRIES) index = 0;
        entries[index] = he;
    }

    synchronized public HistoryEntry getLastEntry() {
        if(index == -1) throw new NoSuchElementException("History is empty.");
        return entries[index];
    }
}

/**
 * $Log: HistoryImpl.java,v $
 * Revision 1.3  2007/03/05 14:27:42  janisch
 * - made HistoryImpl fully synchronized: in combination with model::store
 *   and processExternalState race conditions on the array counter occured.
 * - added unit test for history
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:36  janisch
 * Release 1.0.0
 *
 */