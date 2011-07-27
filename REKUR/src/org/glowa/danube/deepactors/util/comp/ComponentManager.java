package org.glowa.danube.deepactors.util.comp;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ComponentManager.java,v 1.3 2006/01/31 14:44:24 janisch Exp $ 
 */
public interface ComponentManager {

    <P> P getProvided(Class<? extends P> provInterf);
    <R> void bindRequired(Class<? extends R> reqInterf, R impl);
    boolean isBound();
}

/**
 * $Log: ComponentManager.java,v $
 * Revision 1.3  2006/01/31 14:44:24  janisch
 * Refactored generic type usage
 *
 * Revision 1.2  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:41  janisch
 * Release 1.0.0
 *
 */