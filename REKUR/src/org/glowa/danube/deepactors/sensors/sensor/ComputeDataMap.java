package org.glowa.danube.deepactors.sensors.sensor;

import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.MapEntry;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ComputeDataMap.java,v 1.1 2005/08/26 11:16:03 janisch Exp $ 
 */
public interface ComputeDataMap<E extends MapEntry, S extends SensorSource>{

    /** Transform the given sensor source  according to the 
     * given actor to a corresponding data map. */
    DataMap<E> compute(S src, Actor actor);
}
/**
 * $Log: ComputeDataMap.java,v $
 * Revision 1.1  2005/08/26 11:16:03  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */