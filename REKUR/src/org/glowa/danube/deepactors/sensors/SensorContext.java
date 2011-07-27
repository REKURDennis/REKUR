package org.glowa.danube.deepactors.sensors;

import java.util.Set;

/**
 * Specifies what a sensor needs to get from its environment.
 * 
 * @author janisch
 * @version $Id: SensorContext.java,v 1.3 2007/10/29 09:32:27 janisch Exp $ 
 */
public interface SensorContext {

    /**
     * ToDo:javadoc
     * 
     * @pre.condition {@code Todo:name} - ToDo:body.
     * 
     * @post.condition {@code Todo:name} - ToDo:body.
     * 
     * @return
     */
    Set<Class<?>> getEventClasses();
}

/**
 * $Log: SensorContext.java,v $
 * Revision 1.3  2007/10/29 09:32:27  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:58  janisch
 * Release 1.0.0
 *
 * Revision 1.3  2005/02/18 14:24:37  janisch
 * 
 */