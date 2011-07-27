package org.glowa.danube.deepactors;

import java.util.Collections;
import java.util.Set;

import org.glowa.danube.deepactors.actors.Actors;
import org.glowa.danube.deepactors.actors.actor.Collaborator;
import org.glowa.danube.deepactors.model.ResourceKeys;
import org.glowa.danube.deepactors.resmgt.ResourceAllocator;


/** 
 * Maps {@link org.glowa.danube.deepactors.actors.Actors} and
 * {@link org.glowa.danube.deepactors.resmgt.ResourceAllocator} to
 * {@link org.glowa.danube.deepactors.sensors.ActorSensorContext}.
 * 
 * @author janisch
 * @version $Id: ASCProxy.java,v 1.3 2007/10/29 08:00:31 janisch Exp $ 
 */
public final class ASCProxy implements 
org.glowa.danube.deepactors.sensors.ActorSensorContext {

    private Actors actors;
    private ResourceAllocator resource;

    public ASCProxy(ResourceAllocator ra, Actors a) {
        actors = a;
        resource = ra;
    }
    
    public Set<Collaborator> getCollaborators() {
        return actors.getCollaborators();
    }
    public Set<Class<?>> getEventClasses() {
        Set<Class<?>> cs = 
            resource.getClassSet(ResourceKeys.ActorSensorEvents);
        if(cs == null) return Collections.emptySet();
        return cs;
    }
}

/**
 * $Log: ASCProxy.java,v $
 * Revision 1.3  2007/10/29 08:00:31  janisch
 * - Aligned package imports
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.2  2005/11/22 17:22:01  janisch
 * Bugfix: used wrong hard-coded keys to retrieve sensor events and constraints
 *
 * Revision 1.1  2005/08/26 11:16:09  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/02/24 17:02:08  janisch
 */