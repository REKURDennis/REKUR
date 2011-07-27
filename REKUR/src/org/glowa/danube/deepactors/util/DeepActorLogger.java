package org.glowa.danube.deepactors.util;

import java.util.Hashtable;

import org.glowa.danube.utilities.internal.DanubiaLogger;

/**
 * This class provides logging facility for the DeepActor-Framework 
 * 
 * <p>
 * This class reduces the DanubiaLogger API to the log levels debug,
 * info and warn, all of them providing only a string parameter. Additionaly
 * an exception log method is introduced which creates log output with error
 * level and throws a runtime exception. This method may be used, if: <br>
 * 1. the application catches a checked exception but cannot handle it and does
 *    not want or can not extend the method signature to declare the catched
 *    exceptions (e.g. IOExceptions during file read, etc.)<br> 
 * 2. the application detects an invalid object or system state<br>
 * In the first case the application should use <tt>exception(Exception e)</tt> 
 * or <tt>exception(String message, Exception e)</tt> if the application can 
 * provide more specific information than given by the information coming 
 * with <tt>e</tt>. In the second case the application should use 
 * <tt>exception(String message)</tt> to provide a description of the detected 
 * erroneous state.
 * </p>
 * 
 * <p>Instantiation: the DeepActorLogger is a singleton, i.e. the class hides 
 * the constructor and provides the static <tt>newInstance</tt> methods which 
 * return a  newly instantiated logger if and only if a logger instance has not 
 * been created for the given type before. The class provides the following
 * factory methods:<br>
 * 1. <tt>newInstance(Object obj)</tt> <br>
 * 2. <tt>newInstance(Class c)</tt> <br>
 * The first case allows to create a logger for objects of the dynamic type of
 * <tt>obj</tt>, i.e. it can be used within super classes which like to create a
 * logger for their subtype objects. The second case creates a logger for 
 * objects of type <tt>c</tt>. Note that the singleton property implies that 
 * all objects of the same type share the same logger.  
 * </p>
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: DeepActorLogger.java,v 1.2 2005/09/08 08:13:00 janisch Exp $ 
 */
public class DeepActorLogger {

    // map of singletons
    private static Hashtable<Class, DeepActorLogger> loggerMap = 
        new Hashtable<Class, DeepActorLogger>();
    // delegate
    private DanubiaLogger danubiaLogger;
    
    private DeepActorLogger(Class c) {
        danubiaLogger = DanubiaLogger.getDanubiaLogger(c);
    }
    
    /**
     * Returns logger instance for objects of the type c.
     * Usage in the context of an application class C:<br>
     * <tt>DeepActorLogger dal = DeepActorLogger.newInstance(C.class);</tt>
     */
    public static DeepActorLogger newInstance(Class c){
        if(loggerMap.get(c) == null) {
            loggerMap.put(c, new DeepActorLogger(c));
        }
        return loggerMap.get(c);    
    }

    /**
     * Returns logger instance for objects of the dynamic type of the given obj.
     * Usage in the context of any application class:<br>
     * <tt>DeepActorLogger dal = DeepActorLogger.newInstance(this);</tt>
     */
    public static DeepActorLogger newInstance(Object obj){
        return newInstance(obj.getClass());
    }

    /** Log a debug message. */
    public void debug(String message) { danubiaLogger.debug(message); }
    
    /** Log information to trace the application's state. */
    public void info(String message) { danubiaLogger.info(message); }
    
    /** 
     * Log warning message for critical states which does not necessarly require
     * the application to be stopped.
     */
    public void warn(String message) { danubiaLogger.warn(message); }

    /**
     * Log exception for exceptional application states which require the 
     * application to be stopped.
     */
    public void exception(Exception e) {
        danubiaLogger.error(e);
        throw new RuntimeException(e.getCause());
    }

    /**
     * Log message for erroneous application states which require the 
     * application to be stopped.
     */
    public void exception(String message) {
        Exception e = new RuntimeException(message);
        e.fillInStackTrace();
        exception(message, e);
    }
    
    /**
     * Log exception with more specific information for exceptional application 
     * states which require the application to be stopped.
     */
    public void exception(String message, Exception e) {
        danubiaLogger.error(message, e);
        throw new RuntimeException(message, e.getCause());
    }
}

/**
 * $Log: DeepActorLogger.java,v $
 * Revision 1.2  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:12  janisch
 * Release 1.0.0
 *
 */