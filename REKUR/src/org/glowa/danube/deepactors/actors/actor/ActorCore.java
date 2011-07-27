package org.glowa.danube.deepactors.actors.actor;

import org.glowa.danube.deepactors.util.MapEntry;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActorCore.java,v 1.4 2007/10/29 08:09:01 janisch Exp $ 
 */
public interface ActorCore extends MapEntry{
    int getId();
    void init();
    void query();
    void decide();
    void export();
    void store();
}

/**
 * $Log: ActorCore.java,v $
 * Revision 1.4  2007/10/29 08:09:01  janisch
 * - Aligned package imports to Danubia 2.0
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.3  2005/12/22 14:31:40  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:53  janisch
 * Release 1.0.0
 *
 */