package org.glowa.danube.deepactors.actors.constraint;

import org.glowa.danube.deepactors.actors.actor.ConstraintProperties;
import org.glowa.danube.deepactors.util.MapEntry;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: Constraint.java,v 1.3 2006/01/31 14:38:27 janisch Exp $ 
 */
public interface Constraint<P extends ConstraintProperties> extends MapEntry{
    boolean check(P p);
}
/**
 * $Log: Constraint.java,v $
 * Revision 1.3  2006/01/31 14:38:27  janisch
 * Introduced Type Parameter to interface
 *
 * Revision 1.2  2005/09/08 13:39:11  janisch
 * Removed getId() in actors.constraint.Constraint; now inherited by MapEntry.
 *
 * Revision 1.1  2005/08/26 11:16:18  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */