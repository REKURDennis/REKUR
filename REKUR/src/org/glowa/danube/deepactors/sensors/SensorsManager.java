package org.glowa.danube.deepactors.sensors;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.glowa.danube.deepactors.actors.actor.Collaborator;
import org.glowa.danube.deepactors.actors.actor.ConstraintProperties;
import org.glowa.danube.deepactors.actors.constraint.Constraint;
import org.glowa.danube.deepactors.sensors.actor.ActorSensor;
import org.glowa.danube.deepactors.sensors.constraint.ConstraintSensor;
import org.glowa.danube.deepactors.sensors.proxel.ProxelSensor;
import org.glowa.danube.deepactors.sensors.sensor.Sensor;
import org.glowa.danube.deepactors.sensors.sensor.SensorControl;
import org.glowa.danube.deepactors.util.DeepActorLogger;
import org.glowa.danube.deepactors.util.ProxelMapEntry;
import org.glowa.danube.deepactors.util.comp.AbstractComponentManager;
import org.glowa.danube.deepactors.util.comp.ComponentManager;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: SensorsManager.java,v 1.6 2007/10/29 09:32:27 janisch Exp $ 
 */
public final class SensorsManager extends AbstractComponentManager 
implements ComponentManager, SensorQueries, SensorsControl{

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(SensorsManager.class);
    
    private ConcurrentLinkedQueue<SensorControl> sensors = 
        new ConcurrentLinkedQueue<SensorControl>();
    private ActorSensor actorSensor;
    private ConstraintSensor constraintSensor;
    private ProxelSensor proxelSensor; 
    
    public SensorsManager(){
        log.info("Instantiate Sensors container ...");
        super.provInterfMap.put(SensorsControl.class, this);
        super.provInterfMap.put(SensorQueries.class, this);
        
        super.reqInterfSet.add(ConstraintSensorContext.class);
        super.reqInterfSet.add(ProxelSensorContext.class);
        super.reqInterfSet.add(ActorSensorContext.class);
    }
    
    /**
     * ToDo:javadoc
     * 
     * @see org.glowa.danube.deepactors.util.comp.ComponentManager#bindRequired(java.lang.Class, R)
     * 
     * @pre.condition {@code Todo:name} - ToDo:body.
     * 
     * @post.condition {@code Todo:name} - ToDo:body.
     * 
     * @param <R>
     * @param reqInterf
     * @param impl
     */
    protected <R> void handleBinding(Class<? extends R> reqInterf, R impl) {
        if(reqInterf == ConstraintSensorContext.class) {
            constraintSensor = 
                new ConstraintSensor((ConstraintSensorContext)impl);
            sensors.add(constraintSensor);
        }
        if(reqInterf == ProxelSensorContext.class) {
            proxelSensor = new ProxelSensor((ProxelSensorContext)impl);
            sensors.add(proxelSensor);
        }
        if(reqInterf == ActorSensorContext.class) {
            actorSensor = new ActorSensor((ActorSensorContext)impl);
            sensors.add(actorSensor);
        }
    }

    // -------------------------------------------------------------------------    
    // Implementing provided interfaces 
    // -------------------------------------------------------------------------
    public final void update() { 
        for(SensorControl s:sensors) s.readData(); 
    }
    public final Sensor<Constraint<ConstraintProperties>> getConstraintSensor() {
        return constraintSensor;
    }
    public final Sensor<ProxelMapEntry> getProxelSensor() {
        return proxelSensor;
    }
    public final Sensor<Collaborator> getActorSensor() { 
        return actorSensor; 
    }       
}

/**
 * $Log: SensorsManager.java,v $
 * Revision 1.6  2007/10/29 09:32:27  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.5  2006/01/31 14:40:59  janisch
 * Refactored generic type usage
 *
 * Revision 1.4  2005/11/10 12:39:35  janisch
 * Use ConcurrentLinkedQueue to administrate sensor objects
 *
 * Revision 1.3  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:00  janisch
 * Release 1.0.0
 *
 */