package org.glowa.danube.deepactors.actors.history;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: HistoryCore.java,v 1.2 2005/09/08 08:12:59 janisch Exp $ 
 */
public interface HistoryCore {

    void add(HistoryCoreData hcd, HistoryEntry he);
}

/**
 * $Log: HistoryCore.java,v $
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:32  janisch
 * Release 1.0.0
 *
 */