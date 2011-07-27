package org.glowa.danube.deepactors.actors.history;

import java.util.Set;

import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: HistoryCoreData.java,v 1.4 2007/10/29 09:01:39 janisch Exp $ 
 */
public class HistoryCoreData {
    
    private DanubiaCalendar time;
    private int[] failedPlanIds;
    private int[] execPlanIds;
    
    
    public HistoryCoreData(DanubiaCalendar time, Set<Integer> failedPlanIds, 
            Set<Integer> execPlanIds) {
        this.time = time;
        this.failedPlanIds = setToArray(failedPlanIds);
        this.execPlanIds = setToArray(execPlanIds);
    }
    
    DanubiaCalendar time(){return time;}
    int[] failedPlanIds(){return failedPlanIds;}
    int[] execPlanIds(){return execPlanIds;}
    
     private int[] setToArray(Set<Integer> set) {
        int[] array = new int[set.size()];
        int idx = 0;
        for(int elt:set) array[idx++] = elt;
        return array;
    }
}

/**
 * $Log: HistoryCoreData.java,v $
 * Revision 1.4  2007/10/29 09:01:39  janisch
 * - Aligned package imports to Danubia 2.0
 *
 * Revision 1.3  2005/11/10 12:34:28  janisch
 * Moved code block only.
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:31  janisch
 * Release 1.0.0
 *
 */