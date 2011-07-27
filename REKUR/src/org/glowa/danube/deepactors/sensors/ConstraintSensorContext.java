package org.glowa.danube.deepactors.sensors;

import java.util.Set;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ConstraintSensorContext.java,v 1.3 2007/10/29 09:32:27 janisch Exp $ 
 */
public interface ConstraintSensorContext extends SensorContext{

    Set<Class<?>> getConstraintClasses();
}

/**
 * $Log: ConstraintSensorContext.java,v $
 * Revision 1.3  2007/10/29 09:32:27  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:04  janisch
 * Release 1.0.0
 *
 */