package org.glowa.danube.deepactors.sensors.sensor;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: SensorControl.java,v 1.1 2005/08/26 11:16:02 janisch Exp $ 
 */
public interface SensorControl{

    /** Cause the sensor to update e.g. read new data from the source. */
    void readData();
}
/**
 * $Log: SensorControl.java,v $
 * Revision 1.1  2005/08/26 11:16:02  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 */