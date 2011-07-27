package org.glowa.danube.deepactors.util;

import java.util.Calendar;

import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * ToDo: Implement Support for yearly/monthly periods which contain 
 * different years, months resp. E.g. Patterns like 
 * 01.12.XX - 31.01.XX (switches year) and 15.XX.XX - 05.XX.XX (switches month)
 * @author janisch
 * @version $Id: TimePeriod.java,v 1.9 2007/10/29 09:38:02 janisch Exp $ 
 */
public class TimePeriod {

    public static final String ALWAYS = "Always"; 
    public static final int EVERY_YEAR = 9999;
    public static final int EVERY_MONTH = Calendar.UNDECIMBER;
    
    private boolean representsAlways;
    private boolean isYearlyPeriod = false;
    private boolean isMonthlyPeriod = false;
    
    private DanubiaCalendar start;
    private DanubiaCalendar end;
    
    /**
     * Creates a time period object which represents ALWAYS.
     */
    public TimePeriod() {
        representsAlways = true;
    }
    
    /**
     * ToDo:
     * 
     * @pre.condition: start < end
     */
    public TimePeriod(DanubiaCalendar start, DanubiaCalendar end) {
        representsAlways = false;
        if(start.getYear() == EVERY_YEAR) isYearlyPeriod = true;
        if(start.getMonth() == EVERY_MONTH) isMonthlyPeriod = true;
        this.start = start;
        this.end = end;
    }
    
    /**
     * Check includes lower and upper bound.
     */
    public boolean contains(DanubiaCalendar time) {
        if(time == null) return false;
        if(representsAlways) return true;
        
        if(isYearlyPeriod) useCurrentYear(time.getYear());
        if(isMonthlyPeriod) useCurrentYearAndMonth(time.getYear(), time.getMonth());
        
        long timeInMs = time.getTimeInMillis();
        long startInMs = start.getTimeInMillis();
        long endInMs = end.getTimeInMillis();
        
        if(! ((startInMs <= timeInMs) && (timeInMs <= endInMs)) ) return false;
        return true;
    }
    
    private void useCurrentYearAndMonth(int year, int month) {
        start = new DanubiaCalendar(year, month, start.getDay());
        end = new DanubiaCalendar(year, month, end.getDay());
    }

    private void useCurrentYear(int year) {
        start = new DanubiaCalendar(year, start.getMonth(), start.getDay());
        end = new DanubiaCalendar(year, end.getMonth(), end.getDay());
    }

    public String toString() {
        if(representsAlways) return ALWAYS;
        String s = start.toString() + "-" + end.toString();
        if(isYearlyPeriod) {
            s = "Every year between " + start.getMonth() + start.getDay()
            + " and " + end.getMonth() + end.getDay();
        }
        if(isMonthlyPeriod) {
            s = "Every month between " + start.getDay() + "."
            + " and " + end.getDay() + ".";
        }
        return s;
    }
    
    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof TimePeriod)) return false;
        if(! this.toString().equals(other.toString())) return false;
        return true;
    }
}
/**
 * $Log: TimePeriod.java,v $
 * Revision 1.9  2007/10/29 09:38:02  janisch
 * - Aligned package imports to Danubia 2.0
 * - GlowaInterface -> DanubiaInterface
 *
 * Revision 1.8  2005/12/22 14:32:49  janisch
 * Added ToDo.
 *
 * Revision 1.7  2005/09/28 09:44:50  janisch
 * Extension to support periods "every year" and "every month".
 *
 * Revision 1.6  2005/09/23 11:00:59  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:10  janisch
 * Release 1.0.0
 *
 * Revision 1.4  2005/01/21 18:21:06  janisch
 * equals implemented.
 *
 * Revision 1.3  2005/01/20 12:40:51  janisch
 * Bugfix: contains returned true for not contained time values.
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */