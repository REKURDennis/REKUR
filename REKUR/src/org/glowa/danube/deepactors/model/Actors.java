package org.glowa.danube.deepactors.model;

import java.util.Set;

import org.glowa.danube.deepactors.actors.actor.Actor;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: Actors.java,v 1.2 2005/09/08 08:12:57 janisch Exp $ 
 */
public interface Actors {

    void create();
    void init();
    void query();
    void decide();
    void export();
    void store();
    Set<Actor> getActors();
}

/**
 * $Log: Actors.java,v $
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:44  janisch
 * Release 1.0.0
 *
 */