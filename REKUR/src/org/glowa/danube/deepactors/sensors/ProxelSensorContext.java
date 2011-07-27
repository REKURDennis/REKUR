package org.glowa.danube.deepactors.sensors;

import org.glowa.danube.simulation.model.proxel.ProxelTable;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ProxelSensorContext.java,v 1.5 2007/10/29 09:32:27 janisch Exp $ 
 */
public interface ProxelSensorContext extends SensorContext{

    public ProxelTable<?> getProxelTable();
}

/**
 * $Log: ProxelSensorContext.java,v $
 * Revision 1.5  2007/10/29 09:32:27  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.4  2005/12/22 14:38:21  janisch
 * Do not notify the proxel objects any more.
 *
 * Revision 1.3  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:59  janisch
 * Release 1.0.0
 *
 */