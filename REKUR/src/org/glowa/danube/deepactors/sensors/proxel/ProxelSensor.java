package org.glowa.danube.deepactors.sensors.proxel;

import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.sensors.ProxelSensorContext;
import org.glowa.danube.deepactors.sensors.sensor.AbstractSensor;
import org.glowa.danube.deepactors.sensors.sensor.ComputeDataMap;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.MutableDataMap;
import org.glowa.danube.deepactors.util.ProxelMapEntry;
import org.glowa.danube.simulation.model.proxel.ProxelTable;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ProxelSensor.java,v 1.5 2007/10/29 09:33:27 janisch Exp $ 
 */
public final class ProxelSensor 
extends AbstractSensor<ProxelMapEntry, ProxelSource> 
    implements ComputeDataMap<ProxelMapEntry, ProxelSource>{

    private ProxelSource src;
    
    public ProxelSensor(ProxelSensorContext psc) {
        src = new ProxelSource(psc);
        super.bind(src, this, psc);
        // context might not be ready yet 
        // => don't access e.g. getProxelTable in here
    }

    /**
     * Returns references to proxel objects which belong to the actors location.
     * 
     */
    public DataMap<ProxelMapEntry> compute(ProxelSource src, Actor actor){
        ProxelTable proxelTable = src.getProxelTable();
        MutableDataMap<ProxelMapEntry> resultMap = 
            new MutableDataMap<ProxelMapEntry>();
        int[] pids = actor.getLocation().getPIDArray();
        for(int pid : pids) {
            resultMap.add(new ProxelMapEntry(proxelTable.getProxel(pid)));
        }
        return resultMap;
    }
}
/**
 * $Log: ProxelSensor.java,v $
 * Revision 1.5  2007/10/29 09:33:27  janisch
 * Aligned package imports to Danubia 2.0
 *
 * Revision 1.4  2005/12/22 14:38:21  janisch
 * Do not notify the proxel objects any more.
 *
 * Revision 1.3  2005/09/23 11:01:01  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:14  janisch
 * Release 1.0.0
 *
 * Revision 1.3  2005/02/10 13:41:24  janisch
 * Renamed CoreSensor to AbstractSensor and made it abstract.
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */