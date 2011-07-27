package org.glowa.danube.deepactors.sensors.actor;

import java.util.HashMap;
import java.util.Set;

import org.glowa.danube.deepactors.actors.actor.Collaborator;
import org.glowa.danube.deepactors.sensors.ActorSensorContext;
import org.glowa.danube.deepactors.sensors.sensor.SensorSource;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ActorSensorSource.java,v 1.2 2006/01/27 13:18:30 janisch Exp $ 
 */
public class ActorSensorSource implements SensorSource {

    private HashMap<Integer, Collaborator> collabMap
        = new HashMap<Integer, Collaborator>();
    private ActorSensorContext context;
    
    public ActorSensorSource(ActorSensorContext asc) { 
        context = asc;
    }
    
    public void update() {
        collabMap = null;
        Set<Collaborator> collabs = context.getCollaborators();
        int numCollabs = collabs.size();
        collabMap = new HashMap<Integer, Collaborator>(numCollabs);
        for(Collaborator c:collabs){
            collabMap.put(c.getId(),c);
        }
    }

    /** Used only by the sensor implementation within this package. */
    HashMap<Integer, Collaborator> getExportImpls() { 
        return collabMap; 
    }
}
/**
 * $Log: ActorSensorSource.java,v $
 * Revision 1.2  2006/01/27 13:18:30  janisch
 * Improved collaborator retrievement (previously n² runtime)
 *
 * Revision 1.1  2005/08/26 11:17:43  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/11 17:34:36  janisch
 */
