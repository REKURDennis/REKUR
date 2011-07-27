package org.glowa.danube.deepactors.model;

import org.glowa.danube.deepactors.util.DeepActorLogger;


/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActorModelFactory.java,v 1.4 2007/10/31 10:16:51 janisch Exp $ 
 */
public class ActorModelFactory{

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ActorModelFactory.class);

    private ActorModelCore actorModelCore;
    private TimeQuery timeQuery;
    
    public ActorModelFactory(ResourceAdmin rm, Sensors s, Actors a, 
            ActorModelCoreImpl amc) {
        log.info("Instantiate and bind ActorModel principle ...");
        amc.bindRequired(s, rm, a);
        actorModelCore = amc;
        timeQuery = amc;
    }

    public ActorModelCore getActorModelCore() {return actorModelCore; }
    public TimeQuery getTimeQuery() {return timeQuery; }
}

/**
 * $Log: ActorModelFactory.java,v $
 * Revision 1.4  2007/10/31 10:16:51  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.3  2007/10/29 09:31:09  janisch
 * ActorModelCoreImpl is now instantiated and passed by Danubia
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:44  janisch
 * Release 1.0.0
 *
 */