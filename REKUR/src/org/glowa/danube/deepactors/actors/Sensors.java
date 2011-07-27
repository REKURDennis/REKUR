package org.glowa.danube.deepactors.actors;

import org.glowa.danube.deepactors.actors.actor.Collaborator;
import org.glowa.danube.deepactors.actors.actor.ConstraintProperties;
import org.glowa.danube.deepactors.actors.constraint.Constraint;
import org.glowa.danube.deepactors.sensors.sensor.Sensor;
import org.glowa.danube.deepactors.util.ProxelMapEntry;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: Sensors.java,v 1.4 2007/10/29 08:05:55 janisch Exp $ 
 */
public interface Sensors {

    Sensor<Collaborator> getActorSensor();
    Sensor<ProxelMapEntry> getProxelSensor();
    Sensor<Constraint<ConstraintProperties>> getConstraintSensor();
}

/**
 * $Log: Sensors.java,v $
 * Revision 1.4  2007/10/29 08:05:55  janisch
 * - Aligned package imports to Danubia 2.0
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.3  2005/09/23 11:01:01  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:46  janisch
 * Release 1.0.0
 *
 */