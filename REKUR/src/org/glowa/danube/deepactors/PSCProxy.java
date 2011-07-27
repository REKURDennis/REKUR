package org.glowa.danube.deepactors;

import java.util.Collections;
import java.util.Set;

import org.glowa.danube.deepactors.model.ActorModelCore;
import org.glowa.danube.deepactors.model.ResourceKeys;
import org.glowa.danube.deepactors.resmgt.ResourceAllocator;
import org.glowa.danube.deepactors.util.DeepActorLogger;
import org.glowa.danube.deepactors.util.InterfaceImpl;
import org.glowa.danube.simulation.model.proxel.ProxelTable;

/** 
 * Maps {@link org.glowa.danube.deepactors.model.ActorModelCore} and
 * {@link org.glowa.danube.deepactors.resmgt.ResourceAllocator} to
 * {@link org.glowa.danube.deepactors.sensors.ProxelSensorContext}.
 * 
 * @author janisch
 * @version $Id: PSCProxy.java,v 1.5 2007/10/29 08:00:31 janisch Exp $ 
 */
public final class PSCProxy implements 
org.glowa.danube.deepactors.sensors.ProxelSensorContext{

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(PSCProxy.class);
    
    private ResourceAllocator resource;
    private ActorModelCore actorModel;
    
    public PSCProxy(ResourceAllocator ra, ActorModelCore amc) { 
        resource = ra; 
        actorModel = amc;
    }
    public Set<Class<?>> getEventClasses() { 
        Set<Class<?>> cs = resource.getClassSet(ResourceKeys.ProxelSensorEvents);
        if(cs == null){
            log.debug("No event types for proxel sensor ...");
            return Collections.emptySet();
        }
        log.debug("Retrieved " + cs.size() + " event types for proxel sensor.");
        return cs;        
    }
    public Set<InterfaceImpl> getInterfaceImpls() {
        return actorModel.getModelImport();
    }
    public ProxelTable<?> getProxelTable() { return actorModel.getProxelTable(); }
}
/**
 * $Log: PSCProxy.java,v $
 * Revision 1.5  2007/10/29 08:00:31  janisch
 * - Aligned package imports
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.4  2005/11/22 17:57:50  janisch
 * Debug message introduced possibility of null pointer
 * expections. Fixed.
 *
 * Revision 1.3  2005/11/22 17:22:01  janisch
 * Bugfix: used wrong hard-coded keys to retrieve sensor events and constraints
 *
 * Revision 1.2  2005/09/23 11:01:00  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.1  2005/08/26 11:16:11  janisch
 * Release 1.0.0
 *
 */