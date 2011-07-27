package org.glowa.danube.deepactors.actors.exec;

import java.util.Set;

import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: GroupExecutionFactory.java,v 1.5 2006/08/16 13:54:38 janisch Exp $ 
 */
public class GroupExecutionFactory {

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(GroupExecutionFactory.class);

    private GroupExecService execService;
    private final int numTasks = Runtime.getRuntime().availableProcessors();
    
    public GroupExecutionFactory(Set<MemberInvocation> mis) {
        log.info("Instantiate and bind GroupExecution principle [num task=" + numTasks + "] ...");
        execService = new GroupExecServiceImpl(numTasks, mis); 
    }
    public GroupExecService getExecService() {return execService;} 
}

/**
 * $Log: GroupExecutionFactory.java,v $
 * Revision 1.5  2006/08/16 13:54:38  janisch
 * Num threads equals num processors.
 *
 * Revision 1.4  2006/01/25 09:24:53  janisch
 * Always used sequential mode regardless of the
 * property deepactors.actor.concurrent.
 *
 * Revision 1.3  2005/11/10 12:33:36  janisch
 * Reintroduced boolean system property "deepactors.actor.concurrent" to switch
 * between concurrent and sequential mode. The default mode is sequential.
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:32  janisch
 * Release 1.0.0
 *
 */