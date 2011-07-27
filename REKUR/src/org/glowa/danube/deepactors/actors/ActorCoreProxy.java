package org.glowa.danube.deepactors.actors;

import org.glowa.danube.deepactors.actors.actor.ActorCore;
import org.glowa.danube.deepactors.actors.exec.MemberInvocation;
import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ActorCoreProxy.java,v 1.5 2007/10/29 08:05:30 janisch Exp $ 
 */
public class ActorCoreProxy implements MemberInvocation{

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ActorCoreProxy.class);
    
    private ActorCore actorCore;
    
    ActorCoreProxy(ActorCore ac){ actorCore = ac; }

    public void invoke(String method){
        if(method.equals("init")){
            actorCore.init();
        }
        else if(method.equals("query")){ 
            actorCore.query(); 
        }
        else if(method.equals("decide")){
            actorCore.decide();
        }
        else if(method.equals("export")) {
            actorCore.export();
        }
        else if(method.equals("store")){
            actorCore.store();
        }
        else log.exception("Unknown invocation request: "+method);
    }
}

/**
 * $Log: ActorCoreProxy.java,v $
 * Revision 1.5  2007/10/29 08:05:30  janisch
 * - Aligned package imports to Danubia 2.0
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.4  2006/08/16 13:55:32  janisch
 * Added configuration parameters of models' cfg file as
 * configuration resource.
 *
 * Revision 1.3  2005/12/22 14:31:41  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:43  janisch
 * Release 1.0.0
 *
 */