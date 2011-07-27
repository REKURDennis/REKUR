package org.glowa.danube.deepactors.actors.action;


/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActionCore.java,v 1.4 2007/10/29 08:07:03 janisch Exp $ 
 */
public interface ActionCore {
    int getId();
    boolean isExecutable();
    void init();
    // pre: isExecutable()
    void execute();
}

/**
 * $Log: ActionCore.java,v $
 * Revision 1.4  2007/10/29 08:07:03  janisch
 * - Aligned package imports to Danubia 2.0
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.3  2005/12/22 14:31:41  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:53  janisch
 * Release 1.0.0
 *
 */