package org.glowa.danube.deepactors.util.tools;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.glowa.danube.utilities.internal.DanubiaLogger;

/**
 * A simple class to carry out performance measurements for method invocations. Object,
 * method and argument values which should be passed to the method must be given
 * to the constructors of this class. The number of invocations whose average
 * result will be the result of a performance measurement may be set via 
 * {@link PerformanceMeas#setNumOfInvocations(int)}. The default number of invocations
 * is 30. To actually perform the mesurement use 
 * {@link PerformanceMeas#runMeas()}. Status and some execution info is printed to the
 * console. 
 * 
 * @author janisch
 * @version $Id: PerformanceMeas.java,v 1.1 2006/08/16 14:02:12 janisch Exp $ 
 */
public class PerformanceMeas {
    
    private static final DanubiaLogger log = 
        DanubiaLogger.getDanubiaLogger(PerformanceMeas.class);

    // stores meas logs for each invocation
    private StringBuffer stepLogs = new StringBuffer();
    
    // result in ms
    private long timeInMs;

    // mem result in KB
    private long memInKb;
    
    // method to be measured
    private Method m;
    
    // convenient
    private String simpleMethodName;

    // parameter values for method
    private Object[] args;
    
    // object to be called m on
    private Object obj;
    
    // number of invocations for one measurement
    private int numInvoke = 30;

    private static final String nl = System.getProperty("line.separator");

    // get mem infos
    private final MemoryMXBean mb = ManagementFactory.getMemoryMXBean();


    
    /**
     * Constructor for automatic method calls
     * @param o
     * @param method
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public PerformanceMeas(Object o, String method) throws SecurityException, 
    NoSuchMethodException, IllegalArgumentException, IllegalAccessException, 
    InvocationTargetException {
        this.obj = o;
        this.m = o.getClass().getMethod(method, new Class[] {});
        this.args = new Object[] {};
        simpleMethodName = method;
    }
    public PerformanceMeas(Object o, String method, String[] argTypes, Object[] args) 
    throws SecurityException, 
    NoSuchMethodException, IllegalArgumentException, IllegalAccessException, 
    InvocationTargetException, ClassNotFoundException {
        this.obj = o;
        this.m = o.getClass().getMethod(method, constructClassArray(argTypes));
        this.args=args;
        simpleMethodName = method;        
    }
    
    private Class[] constructClassArray(String[] argTypes) 
    throws ClassNotFoundException {
        Class[] classes = new Class[argTypes.length];
        for(int i=0; i<argTypes.length; i++) {
            classes[i] = Class.forName(argTypes[i]);
        }
        return classes;
    }
    
    
    
    /** Start time measurement */
    public void runMeas() throws IllegalArgumentException, 
    IllegalAccessException, InvocationTargetException {
        System.out.println("Running time measurement with " +numInvoke +
                " invocations " + "of method " + simpleMethodName + "...");
        stepLogs = new StringBuffer();
        long sumDiff=0; long sumMem=0;
        for(int i=0; i<numInvoke; i++) {
            long start = System.currentTimeMillis();
            m.invoke(obj, args);
            long end = System.currentTimeMillis();
            long diff = end-start;
            long heapmem = mb.getHeapMemoryUsage().getUsed()/1024;
            String info = "Time diff " + i + ".invocation: " + diff + "ms " + heapmem/1024 + "mb";
            stepLogs.append(info + nl);
            System.out.println(info);
            log.info(info);
            sumDiff = sumDiff + diff;
            sumMem = sumMem + heapmem;
        }
        timeInMs = sumDiff/numInvoke;
        memInKb = sumMem/numInvoke;
    }
    
    public double getTimeInSec() {return (timeInMs/1000.0);}
    public int getNumOfInvocations() {return numInvoke;}
    public void setNumOfInvocations(int num) {numInvoke = num;}
    
    public String resultToString() {
        StringBuffer sb = new StringBuffer();
        int numProcessors = Runtime.getRuntime().availableProcessors();
        sb.append("Performance results (numInvocation=" + numInvoke + ", numTasks=" 
                + numProcessors + "):" + nl);
        sb.append(stepLogs);
        sb.append("Average execution time: " + timeInMs +"ms" +"(" + getTimeInSec() + "s)" + nl);
        long heapmem = mb.getHeapMemoryUsage().getUsed()/1024;
        sb.append("Average heap usage: " + memInKb + "(kb) " + memInKb/1024 + "(mb)");
        return sb.toString();
    }
}

/**
 * $Log: PerformanceMeas.java,v $
 * Revision 1.1  2006/08/16 14:02:12  janisch
 * - Renamed TimeMeas to PerformanceMeas
 * - Minor improvements/modifications
 *
 * Revision 1.4  2006/01/27 13:19:59  janisch
 * Added new functionality (start/endMeasurement).
 *
 * Revision 1.3  2005/03/08 10:34:16  janisch
 * Removed unnecessary cast to double.
 *
 * Revision 1.2  2005/03/01 10:30:32  janisch
 * - Added javadoc
 * - Decoupled gage() from object construction, i.e. measurements are not
 *   executed immediatley as part of the instatiation process any more.
 * - Made the number of invocations adjustable.
 *
 */
