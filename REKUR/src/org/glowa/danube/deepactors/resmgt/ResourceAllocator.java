package org.glowa.danube.deepactors.resmgt;

import java.io.File;
import java.util.Set;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ResourceAllocator.java,v 1.5 2007/10/31 10:16:50 janisch Exp $ 
 */
public interface ResourceAllocator {

    File getFile(String key);
    Set<File> getFileSet(String key);
    
    Class<?> getClass(String key);
    Set<Class<?>> getClassSet(String key);
    
    boolean getConfigBoolean(String key);
    
    ClassLoader getClassLoader();
    /*
     * Note, 2007-10-31 
     * The classloader is required for the initailisation within the Factories
     * of the actors container component. It would be better to have the complete
     * initialisation stuff in the ResMgt component instead of the tedious 
     * Factory-InitTable-Init pattern which relies on getClass of ColumnEntry. The
     * latter should then be merged with *one* getClass query in the ResMgt
     */
}

/**
 * $Log: ResourceAllocator.java,v $
 * Revision 1.5  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.4  2007/10/29 09:31:51  janisch
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.3  2006/08/16 13:55:33  janisch
 * Added configuration parameters of models' cfg file as
 * configuration resource.
 *
 * Revision 1.2  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:21  janisch
 * Release 1.0.0
 *
 */