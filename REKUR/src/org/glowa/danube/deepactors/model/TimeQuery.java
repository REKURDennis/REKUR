package org.glowa.danube.deepactors.model;

import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: TimeQuery.java,v 1.3 2007/10/29 09:31:23 janisch Exp $ 
 */
public interface TimeQuery {

    DanubiaCalendar getSimulationTime();
}

/**
 * $Log: TimeQuery.java,v $
 * Revision 1.3  2007/10/29 09:31:23  janisch
 * Aligned package imports to Danubia 2.0
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:52  janisch
 * Release 1.0.0
 *
 */