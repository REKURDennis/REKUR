package org.glowa.danube.deepactors.sensors.proxel;

import org.glowa.danube.deepactors.sensors.ProxelSensorContext;
import org.glowa.danube.deepactors.sensors.sensor.SensorSource;
import org.glowa.danube.simulation.model.proxel.ProxelTable;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ProxelSource.java,v 1.5 2007/10/29 09:33:18 janisch Exp $ 
 */
public final class ProxelSource implements SensorSource {

    private ProxelSensorContext context;
    private ProxelTable<?> proxelTable;
    
    public ProxelSource(ProxelSensorContext psc) { 
        context = psc; 
    }

    /* (non-Javadoc)
     * @see org.glowa.danube.deepactors.sensors.sensor.SensorSource#update()
     */
    public void update() {
        proxelTable = context.getProxelTable();
    }

    /**
     * @return
     */
    public ProxelTable<?> getProxelTable() {
        return proxelTable;
    }

    

}
/**
 * $Log: ProxelSource.java,v $
 * Revision 1.5  2007/10/29 09:33:18  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.4  2005/12/22 14:38:21  janisch
 * Do not notify the proxel objects any more.
 *
 * Revision 1.3  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:17  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */