package org.glowa.danube.deepactors.actors.history;

import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: HistoryEntry.java,v 1.3 2007/10/29 09:02:06 janisch Exp $ 
 */
public class HistoryEntry {
    
    private HistoryCoreData coreData;
    void setCoreData(HistoryCoreData data){ coreData = data; }

    final public DanubiaCalendar getTime() {return coreData.time();} 
    final public int[] getFailedPlanIds() {return coreData.failedPlanIds();}
    final public int[] getExecPlanIds() {return coreData.execPlanIds();}
}

/**
 * $Log: HistoryEntry.java,v $
 * Revision 1.3  2007/10/29 09:02:06  janisch
 * Aligned package imports to Danubia 2.0
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:35  janisch
 * Release 1.0.0
 *
 */