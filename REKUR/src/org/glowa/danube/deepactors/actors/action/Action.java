package org.glowa.danube.deepactors.actors.action;

import org.glowa.danube.deepactors.util.MapEntry;
import org.glowa.danube.deepactors.util.TimePeriod;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: Action.java,v 1.5 2007/10/29 08:06:31 janisch Exp $ 
 */
public interface Action extends MapEntry{

    int getId();
    boolean isMandatory();
    TimePeriod getPeriod();
    boolean isExecutable();
}

/**
 * $Log: Action.java,v $
 * Revision 1.5  2007/10/29 08:06:31  janisch
 * - Aligned package imports to Danubia 2.0
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.4  2005/12/22 14:31:41  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.3  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:55  janisch
 * Release 1.0.0
 *
 */