package org.glowa.danube.deepactors.sensors.sensor;

/**
 * Gather what sensor sources have in common. Required by the abstract sensor 
 * base class <tt>AbstractSensor</tt>.
 * 
 * @author janisch
 * @version $Id: SensorSource.java,v 1.1 2005/08/26 11:16:04 janisch Exp $ 
 */
public interface SensorSource {
    void update();
}
/**
 * $Log: SensorSource.java,v $
 * Revision 1.1  2005/08/26 11:16:04  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */