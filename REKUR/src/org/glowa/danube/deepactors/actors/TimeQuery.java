package org.glowa.danube.deepactors.actors;

import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: TimeQuery.java,v 1.3 2007/10/29 08:04:27 janisch Exp $ 
 */
public interface TimeQuery {

    DanubiaCalendar getSimulationTime();
}

/**
 * $Log: TimeQuery.java,v $
 * Revision 1.3  2007/10/29 08:04:27  janisch
 * Aligned package imports to Danubia 2.0
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:43  janisch
 * Release 1.0.0
 *
 */