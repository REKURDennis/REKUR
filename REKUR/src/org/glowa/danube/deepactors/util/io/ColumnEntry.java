package org.glowa.danube.deepactors.util.io;

import java.util.StringTokenizer;

import org.glowa.danube.deepactors.util.DeepActorLogger;
import org.glowa.danube.deepactors.util.TimePeriod;
import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ColumnEntry.java,v 1.10 2007/10/31 10:16:50 janisch Exp $ 
 */
public class ColumnEntry {

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ColumnEntry.class);
    
    private String id;
    private String content;
    private ColumnType type;
    
    
    public ColumnEntry(String id, ColumnType type, String content) {
        this.id = id;
        this.content = content;
        this.type = type;
    }

    public ColumnType getType() { return type; }
    public String getId() { return id; }

    /** ToDo: ... */
    public String contentToString() { return content; }
    
    /** @ocl-pre: getType() = ColumnType::intVal */
    public int contentToInt() {
        return Integer.parseInt(content);
    }
    
    /** ToDo: ... */
    public int[] contentToIntArray() {
        StringBuffer intSet = new StringBuffer(content);

        // throw away curly brackets
        intSet.deleteCharAt(0);
        intSet.deleteCharAt(intSet.length()-1);

        StringTokenizer tokenizer = new StringTokenizer(intSet.toString(),",");
        int[] resultArray = new int[tokenizer.countTokens()];
        int index = 0;
        while (tokenizer.hasMoreTokens()) {
            resultArray[index] = Integer.parseInt(tokenizer.nextToken());
            index++;
        }
        return resultArray;
    }
    
    /** ToDo: ... */
    public TimePeriod contentToTimePeriod() {
        if(content.equalsIgnoreCase(TimePeriod.ALWAYS)) return new TimePeriod();
        DanubiaCalendar[] period = parseTimePeriod(content);
        return new TimePeriod(period[0],period[1]);
    }
    private DanubiaCalendar[] parseTimePeriod(String period) {
        StringBuffer periodEntries = new StringBuffer(period);
        // throw away curly brackets
        periodEntries.deleteCharAt(0);
        periodEntries.deleteCharAt(periodEntries.length()-1);

        StringTokenizer st = 
            new StringTokenizer(periodEntries.toString(), ",");
        String startEntry = st.nextToken();
        String endEntry = st.nextToken();
        int startyear = Integer.parseInt(startEntry.substring(0,4));
        int endyear = Integer.parseInt(endEntry.substring(0,4));
        
        int startmonth = Integer.parseInt(startEntry.substring(4,6));
        int endmonth = Integer.parseInt(endEntry.substring(4,6));
        
        int startday = Integer.parseInt(startEntry.substring(6,8));
        int endday = Integer.parseInt(endEntry.substring(6,8));
        
        if (startyear == 0) {
            startyear = TimePeriod.EVERY_YEAR;
            endyear = TimePeriod.EVERY_YEAR;
        }
        if (startmonth == 0) {
            startmonth = TimePeriod.EVERY_MONTH;
            endmonth = TimePeriod.EVERY_MONTH;
        }

        DanubiaCalendar start = 
            new DanubiaCalendar(startyear, startmonth, startday);
        DanubiaCalendar end = new DanubiaCalendar(endyear, endmonth, endday);
        return new DanubiaCalendar[]{start, end};
    }

    /** ToDo: ... */
    public boolean contentToBoolean() {
        return Boolean.parseBoolean(content);
    }
    
    /** Todo: ... 
     * @param classLoader */
    public Class<?> contentToClass(ClassLoader classLoader) {
        Class<?> c = null;
        try {
            if(classLoader == null){
                c = Class.forName(content);
            }
            else{
                c = Class.forName(content, true, classLoader);    
            }
        } 
        catch (ClassNotFoundException e) { log.exception(e); }
        return c;
    }

    /** ToDo: .... */
    public float contentToFloat() {
        return Float.parseFloat(content);
    }
}
/**
 * $Log: ColumnEntry.java,v $
 * Revision 1.10  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.9  2007/10/29 09:38:17  janisch
 * Aligned package imports to Danubia 2.0
 *
 * Revision 1.8  2006/01/31 14:44:49  janisch
 * Refactored generic type usage
 *
 * Revision 1.7  2005/09/28 09:45:07  janisch
 * Extension to support periods "every year" and "every month".
 *
 * Revision 1.6  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.5  2005/08/26 11:17:13  janisch
 * Release 1.0.0
 *
 * Revision 1.4  2005/03/08 10:33:49  janisch
 * Removed unecessary else branch.
 *
 * Revision 1.3  2005/01/21 15:27:55  janisch
 * BugFix: time period wasn't parsed correctly.
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */