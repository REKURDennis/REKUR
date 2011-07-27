package org.glowa.danube.deepactors.actors;

import java.util.Set;

import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.actors.actor.Collaborator;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: Actors.java,v 1.2 2005/09/08 08:12:59 janisch Exp $ 
 */
public interface Actors {

    Set<Actor> getActors();
    Set<Collaborator> getCollaborators();
}

/**
 * $Log: Actors.java,v $
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:44  janisch
 * Release 1.0.0
 *
 */