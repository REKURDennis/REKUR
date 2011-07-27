package org.glowa.danube.deepactors;

import java.io.File;

import org.glowa.danube.deepactors.actors.actor.Collaborator;
import org.glowa.danube.deepactors.actors.actor.ConstraintProperties;
import org.glowa.danube.deepactors.actors.constraint.Constraint;
import org.glowa.danube.deepactors.model.TimeQuery;
import org.glowa.danube.deepactors.resmgt.ResourceAllocator;
import org.glowa.danube.deepactors.sensors.SensorQueries;
import org.glowa.danube.deepactors.sensors.sensor.Sensor;
import org.glowa.danube.deepactors.util.ProxelMapEntry;
import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActorsProxy.java,v 1.6 2007/10/31 10:16:51 janisch Exp $ 
 */
public class ActorsProxy implements 
org.glowa.danube.deepactors.actors.ResourceAllocator,
org.glowa.danube.deepactors.actors.Sensors,
org.glowa.danube.deepactors.actors.TimeQuery{

    private ResourceAllocator resource;
    private TimeQuery timeQuery;
    private SensorQueries sensorQueries;
    
    ActorsProxy(ResourceAllocator ra, TimeQuery tq, SensorQueries sq){
        resource = ra;
        timeQuery = tq;
        sensorQueries = sq;
    }

    public DanubiaCalendar getSimulationTime() {
        return timeQuery.getSimulationTime();
    }

    public File getFile(String key) {
        return resource.getFile(key);
    }

    public boolean getConfigBoolean(String key) {
        return resource.getConfigBoolean(key);
    }
    
    public Sensor<Collaborator> getActorSensor() {
        return sensorQueries.getActorSensor();
    }

    public Sensor<Constraint<ConstraintProperties>> getConstraintSensor() {
        return sensorQueries.getConstraintSensor();
    }

    public Sensor<ProxelMapEntry> getProxelSensor() {
        return sensorQueries.getProxelSensor();
    }

    public Class<?> getClass(String key) {
        return resource.getClass();
    }

    public ClassLoader getClassLoader() {
        return resource.getClassLoader();
    }
}

/**
 * $Log: ActorsProxy.java,v $
 * Revision 1.6  2007/10/31 10:16:51  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.5  2007/10/29 08:00:31  janisch
 * - Aligned package imports
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.4  2006/08/16 13:55:33  janisch
 * Added configuration parameters of models' cfg file as
 * configuration resource.
 *
 * Revision 1.3  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:14  janisch
 * Release 1.0.0
 *
 */