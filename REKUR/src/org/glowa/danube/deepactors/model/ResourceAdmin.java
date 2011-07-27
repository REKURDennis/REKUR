package org.glowa.danube.deepactors.model;

import java.util.Set;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ResourceAdmin.java,v 1.3 2006/08/16 13:58:12 janisch Exp $ 
 */
public interface ResourceAdmin {

    void addFile(String key, String fileName);
    void addClassSet(String key, Set<String> classNames);
    void addConfigBoolean(String key, boolean value);
}

/**
 * $Log: ResourceAdmin.java,v $
 * Revision 1.3  2006/08/16 13:58:12  janisch
 * Added configuration parameters of models' cfg file as
 * configuration resource.
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:50  janisch
 * Release 1.0.0
 *
 */