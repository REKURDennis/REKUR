package org.glowa.danube.deepactors.sensors.sensor;

import java.util.HashSet;
import java.util.Set;

import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.sensors.SensorContext;
import org.glowa.danube.deepactors.sensors.event.EventSource;
import org.glowa.danube.deepactors.sensors.event.SensorEvent;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.DeepActorLogger;
import org.glowa.danube.deepactors.util.MapEntry;
import org.glowa.danube.deepactors.util.MutableDataMap;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: AbstractSensor.java,v 1.10 2007/10/29 09:33:59 janisch Exp $ 
 */
public abstract class AbstractSensor<E extends MapEntry, S extends SensorSource> 
implements Sensor<E>, SensorControl{

    private final DeepActorLogger log = DeepActorLogger.newInstance(this);
    
    private ComputeDataMap<E,S> srcToDataMap;
    private S source;
    private SensorContext context;
    private Set<Class<?>> eventClasses;

    /**
     * The required interfaces of this base class must be bound by 
     * the concrete subclass of this abstract class. This should be done within 
     * the constructor of the subclass, e.g. if the subclass implements the
     * required interface <tt>cdm</tt> itself, then insert a call 
     * <code>super.bind(yourSource, this, yourContext);</code>.
     * 
     * @param cdm is an object implementing the specific source to data map 
     * transformation.
     */
    protected final void bind(S sensorSource, ComputeDataMap<E,S> cdm, 
            SensorContext sc) {
        source = sensorSource;
        srcToDataMap = cdm;
        context = sc;
    }

    // --------------------------------------------------------------
    // SensorQuery implementation
    // --------------------------------------------------------------
    final public synchronized DataMap<E> getDataMap(Actor actor) {
        DataMap<E> result = srcToDataMap.compute(source, actor);
        return result;
    }
    
    /**
     * ToDo:javadoc
     * 
     * @see org.glowa.danube.deepactors.sensors.sensor.Sensor#getEventMap(org.glowa.danube.deepactors.actors.actor.Actor)
     * 
     * @pre.condition {@code ctx-events} - not context.getEventClasses()->contains(null)
     * 
     * @post.condition {@code Todo:name} - ToDo:body.
     * 
     * @param actor
     * @return
     */
    final public synchronized DataMap<SensorEvent<EventSource>> getEventMap(Actor actor) {
        // query datamap each time: the data map varies for different actors!
        DataMap<E> curDataMap = getDataMap(actor);
        
        // create new eventmap, check events and add them
        MutableDataMap<SensorEvent<EventSource>> occEvents = 
            new MutableDataMap<SensorEvent<EventSource>>();
        Set<SensorEvent<EventSource>> events = createAllEvents();
        for(SensorEvent<EventSource> event : events) {
            if(event.condition(curDataMap)) occEvents.add(event);
        }
        return occEvents;
    }
    @SuppressWarnings("unchecked")
    private Set<SensorEvent<EventSource>> createAllEvents() { 
        HashSet<SensorEvent<EventSource>> events = 
            new HashSet<SensorEvent<EventSource>>();
        for(Class<?> eventClass:eventClasses) {
            String warnMessage = "Event " + eventClass.getSimpleName() + 
            " was not instantiable and will not be taken into account."; 
            try {
                events.add((SensorEvent<EventSource>)eventClass.newInstance());
            } 
            catch(InstantiationException e) {log.exception(warnMessage, e);} 
            catch(IllegalAccessException e) {log.exception(warnMessage, e);}
            catch(ClassCastException e) {log.exception(warnMessage, e);}
        }
        return events;
    }
    // -----------------------------------------------------------------
    // SensorControl implementation
    // -----------------------------------------------------------------
    /** 
     * Updates source and gets set of event classes from the sensors context. 
     * Override the plug point <tt>plugPointReadData</tt> if your 
     * sensor needs to do more, e.g. the proxel sensor may like to transform
     * the imported data tables to proxel values for the whole proxel table at 
     * once.
     */
    @SuppressWarnings("unchecked")    
    final public void readData() { 
        source.update();
        eventClasses = context.getEventClasses();
        plugPointReadData();
    }
    /** Plug point for readData.  */
    protected void plugPointReadData() {}
}
/**
 * $Log: AbstractSensor.java,v $
 * Revision 1.10  2007/10/29 09:33:59  janisch
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.9  2006/08/16 13:58:51  janisch
 * No local datamap in getDataMap
 *
 * Revision 1.8  2006/02/24 13:31:06  janisch
 * Switched back to raw type usage in readData.
 *
 * Revision 1.7  2006/01/31 14:59:49  janisch
 * Changed cast in readData().
 *
 * Revision 1.6  2006/01/31 14:41:19  janisch
 * Refactored generic type usage, introduced SuppressWarnings("unchecked")
 *
 * Revision 1.5  2006/01/12 08:26:28  janisch
 * Removed annoying debug message for sensor event checks.
 *
 * Revision 1.4  2005/11/22 17:28:18  janisch
 * Added debug messages.
 *
 * Revision 1.3  2005/11/10 12:40:27  janisch
 * Data and event queries synchronized.
 *
 * Revision 1.2  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:02  janisch
 * Release 1.0.0
 *
 * Revision 1.1  2005/02/10 13:41:24  janisch
 * Renamed CoreSensor to AbstractSensor and made it abstract.
 *
 * Revision 1.3  2005/02/07 14:05:15  janisch
 * Break cases in readData switch.
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */