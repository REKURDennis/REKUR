package org.glowa.danube.deepactors.model;

import java.util.Set;

import org.glowa.danube.deepactors.util.InterfaceImpl;
import org.glowa.danube.simulation.model.proxel.AbstractProxel;
import org.glowa.danube.simulation.model.proxel.ProxelTable;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActorModelCore.java,v 1.4 2007/10/29 09:15:18 janisch Exp $ 
 */
public interface ActorModelCore {

    ProxelTable<? extends AbstractProxel> getProxelTable();
    Set<InterfaceImpl> getModelImport();
}

/**
 * $Log: ActorModelCore.java,v $
 * Revision 1.4  2007/10/29 09:15:18  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.3  2005/09/23 11:00:59  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:50  janisch
 * Release 1.0.0
 *
 */