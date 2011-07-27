package org.glowa.danube.deepactors.resmgt;

import org.glowa.danube.deepactors.util.DeepActorLogger;


/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ResourceManagementFactory.java,v 1.3 2007/10/31 10:16:50 janisch Exp $ 
 */
public class ResourceManagementFactory {

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ResourceManagementFactory.class);
    
    private ResourceAdmin resAdmin;
    private ResourceAllocator resAlloc;
    
    public ResourceManagementFactory(ClassLoader danubiaClassLoader) {
        log.info("Instantiate and bind ResourceManagement principle ...");
        ResourceImpl ri = new ResourceImpl(danubiaClassLoader);
        resAdmin = ri;
        resAlloc = ri;
    }
    public ResourceAdmin getResourceAdmin() {return resAdmin;}
    public ResourceAllocator getResourceAllocator() {return resAlloc;}
}

/**
 * $Log: ResourceManagementFactory.java,v $
 * Revision 1.3  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:19  janisch
 * Release 1.0.0
 *
 */