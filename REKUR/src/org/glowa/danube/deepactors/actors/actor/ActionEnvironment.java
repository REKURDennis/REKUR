package org.glowa.danube.deepactors.actors.actor;

import java.util.Set;

import org.glowa.danube.simulation.model.proxel.AbstractProxel;
import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActionEnvironment.java,v 1.3 2007/10/29 08:08:41 janisch Exp $ 
 */
public interface ActionEnvironment {

    // returns actor proxels after  
    Set<AbstractProxel> getProxel();
    
    // always returns the simulation time
    DanubiaCalendar getSimulationTime();
}

/**
 * $Log: ActionEnvironment.java,v $
 * Revision 1.3  2007/10/29 08:08:41  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Use new Danubia 2.0 base class AbstractProxel
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:52  janisch
 * Release 1.0.0
 *
 */