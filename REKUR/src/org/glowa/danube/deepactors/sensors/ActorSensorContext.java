package org.glowa.danube.deepactors.sensors;

import java.util.Set;

import org.glowa.danube.deepactors.actors.actor.Collaborator;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActorSensorContext.java,v 1.2 2005/09/08 08:12:58 janisch Exp $ 
 */
public interface ActorSensorContext extends SensorContext{

    public Set<Collaborator> getCollaborators();
}

/**
 * $Log: ActorSensorContext.java,v $
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:05  janisch
 * Release 1.0.0
 *
 */