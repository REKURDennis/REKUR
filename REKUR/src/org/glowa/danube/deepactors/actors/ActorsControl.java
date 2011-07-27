package org.glowa.danube.deepactors.actors;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActorsControl.java,v 1.2 2005/09/08 08:12:59 janisch Exp $ 
 */
public interface ActorsControl {

    void create();
    void init();
    void query();
    void decide();
    void export();
    void store();
}

/**
 * $Log: ActorsControl.java,v $
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:46  janisch
 * Release 1.0.0
 *
 */