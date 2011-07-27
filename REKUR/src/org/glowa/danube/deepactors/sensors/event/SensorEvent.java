package org.glowa.danube.deepactors.sensors.event;

import org.glowa.danube.deepactors.util.MapEntry;


/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: SensorEvent.java,v 1.2 2006/01/31 14:39:36 janisch Exp $ 
 */
public interface SensorEvent<S extends EventSource> extends MapEntry{
    boolean condition(S src);
}

/**
 * $Log: SensorEvent.java,v $
 * Revision 1.2  2006/01/31 14:39:36  janisch
 * Introduced Type Parameter to interface
 *
 * Revision 1.1  2005/08/26 11:17:47  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */