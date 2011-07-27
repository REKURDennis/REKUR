package org.glowa.danube.deepactors.sensors.actor;


import java.util.HashMap;

import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.actors.actor.Collaborator;
import org.glowa.danube.deepactors.sensors.ActorSensorContext;
import org.glowa.danube.deepactors.sensors.sensor.AbstractSensor;
import org.glowa.danube.deepactors.sensors.sensor.ComputeDataMap;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.MutableDataMap;


/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ActorSensor.java,v 1.2 2006/01/27 13:18:30 janisch Exp $ 
 */
public final class ActorSensor 
    extends AbstractSensor<Collaborator, ActorSensorSource> 
    implements ComputeDataMap<Collaborator,ActorSensorSource>{

    public ActorSensor(ActorSensorContext asc) {
        super.bind(new ActorSensorSource(asc), this, asc);
    }
    
    /**
     * Projects the export implementations of the sensor source src to a
     * data map holding only those objects which are collaborateurs of the 
     * given actor. 
     * 
     * ToDo: numerous assumptions about src.getExportImpls() which should be
     * deduceable from the invariants and post conditions in ASS (src).
     * 
     * @ocl-post: let sourceMap:Set(ActorMapEntry) = src.getExportImpls() and 
     *                resultMap:Set(ActorMapEntry) = result.getEntries() in
     *   %% cardinality
     *   |resultMap| = |{e in sourceMap | e.getKey() in actor.collaborator}|
     *   %% injective 
     *   for all e in resultMap: e in sourceMap
     */
    public DataMap<Collaborator> compute(ActorSensorSource src, Actor actor) {
        MutableDataMap<Collaborator> resultMap = 
            new MutableDataMap<Collaborator>();
        
        HashMap<Integer, Collaborator> allCollabs = src.getExportImpls();

        int[] cids = actor.getCollabIds();
        for(int collabid:cids){
            resultMap.add(allCollabs.get(collabid));
        }
        return resultMap;
    }
}
/**
 * $Log: ActorSensor.java,v $
 * Revision 1.2  2006/01/27 13:18:30  janisch
 * Improved collaborator retrievement (previously n² runtime)
 *
 * Revision 1.1  2005/08/26 11:17:44  janisch
 * Release 1.0.0
 *
 * Revision 1.3  2005/02/10 13:41:24  janisch
 * Renamed CoreSensor to AbstractSensor and made it abstract.
 *
 * Revision 1.2  2005/01/11 17:30:03  janisch
 * Added cvs log.
 *
 */