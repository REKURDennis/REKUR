package org.glowa.danube.deepactors.actors.actor;

import org.glowa.danube.deepactors.util.MapEntry;
import org.glowa.danube.deepactors.util.Zone;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: Actor.java,v 1.3 2005/09/23 11:01:00 janisch Exp $ 
 */
public interface Actor extends MapEntry{

    int getId();
    Zone getLocation();
    int[] getCollabIds();
}

/**
 * $Log: Actor.java,v $
 * Revision 1.3  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:52  janisch
 * Release 1.0.0
 *
 */