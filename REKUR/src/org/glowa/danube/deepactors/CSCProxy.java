package org.glowa.danube.deepactors;

import java.util.Collections;
import java.util.Set;

import org.glowa.danube.deepactors.model.ResourceKeys;
import org.glowa.danube.deepactors.resmgt.ResourceAllocator;

/**
 * Maps {@link org.glowa.danube.deepactors.resmgt.ResourceAllocator} to
 * {@link org.glowa.danube.deepactors.sensors.ConstraintSensorContext}
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: CSCProxy.java,v 1.4 2007/10/29 08:00:31 janisch Exp $ 
 */
public class CSCProxy implements 
org.glowa.danube.deepactors.sensors.ConstraintSensorContext {

    private ResourceAllocator resource;
    
    public CSCProxy(ResourceAllocator ra) { resource = ra; }

    public Set<Class<?>> getEventClasses() {
        return nullToEmpty(ResourceKeys.ConstraintSensorEvents);
    }
   
    public Set<Class<?>> getConstraintClasses() {
        return nullToEmpty(ResourceKeys.Constraints); 
    }

    private Set<Class<?>> nullToEmpty(String resourceKey) {
        Set<Class<?>> classSet = resource.getClassSet(resourceKey);
        if(classSet == null) return Collections.emptySet();
        return classSet;
    }
}

/**
 * $Log: CSCProxy.java,v $
 * Revision 1.4  2007/10/29 08:00:31  janisch
 * - Aligned package imports
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.3  2005/11/22 17:22:01  janisch
 * Bugfix: used wrong hard-coded keys to retrieve sensor events and constraints
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:12  janisch
 * Release 1.0.0
 *
 */