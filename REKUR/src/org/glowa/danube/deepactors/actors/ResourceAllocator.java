package org.glowa.danube.deepactors.actors;

import java.io.File;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ResourceAllocator.java,v 1.4 2007/10/31 10:16:51 janisch Exp $ 
 */
public interface ResourceAllocator {
    File getFile(String key);
    boolean getConfigBoolean(String key);
    Class<?> getClass(String key);
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
 * Revision 1.4  2007/10/31 10:16:51  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.3  2006/08/16 13:55:32  janisch
 * Added configuration parameters of models' cfg file as
 * configuration resource.
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:43  janisch
 * Release 1.0.0
 *
 */