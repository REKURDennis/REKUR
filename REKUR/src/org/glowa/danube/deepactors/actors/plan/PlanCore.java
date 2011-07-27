package org.glowa.danube.deepactors.actors.plan;


/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: PlanCore.java,v 1.4 2007/10/29 09:03:28 janisch Exp $ 
 */
public interface PlanCore{

    int getId();
    boolean isActive();
    void init();
    void computeRating();

    // pre: isActive
    // post: result=false and no state modfication if not executable
    // post: result=true if executable
    // post implied: no need for client to check executability
    boolean execute();
}

/**
 * $Log: PlanCore.java,v $
 * Revision 1.4  2007/10/29 09:03:28  janisch
 * - Aligned package imports to Danubia 2.0
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.3  2005/12/22 14:31:40  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:21  janisch
 * Release 1.0.0
 *
 */