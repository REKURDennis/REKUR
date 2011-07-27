package org.glowa.danube.deepactors.sensors.sensor;

import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.sensors.event.EventSource;
import org.glowa.danube.deepactors.sensors.event.SensorEvent;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.MapEntry;

/**
 * ToDo: javadoc
 * 
 * @author janisch
 * @version $Id: Sensor.java,v 1.2 2007/10/29 09:34:11 janisch Exp $ 
 */
public interface Sensor<E extends MapEntry>{

    /** 
     * Get a data map which is projected to the actor's core properties. 
     *
     * The data map is computed on the fly in correspondance to values
     * as given through the actor's core properties. 
     */
    DataMap<E> getDataMap(Actor actor);
    
    /** 
     * Get an event map which is projected to the actor's core properties.
     * 
     * The event map is computed on the fly according to the actor's
     * core properties, e.g. a proxel sensor will only create events
     * for proxel id's which correspond to the actor's location.
     *  
     */
    DataMap<SensorEvent<EventSource>> getEventMap(Actor actor);
}
/**
 * $Log: Sensor.java,v $
 * Revision 1.2  2007/10/29 09:34:11  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.1  2005/08/26 11:16:05  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */