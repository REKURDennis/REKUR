package org.glowa.danube.deepactors.actors.exec;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: GroupExecServiceImpl.java,v 1.6 2006/08/16 13:53:34 janisch Exp $ 
 */
public class GroupExecServiceImpl implements GroupExecService{
    
    private final DeepActorLogger log =
        DeepActorLogger.newInstance(GroupExecServiceImpl.class);

    

    private ExecutorService execService = Executors.newCachedThreadPool();
    
    // Exec tasks to be executed concurrently
    private Collection<Callable<Object>> tasks = 
        new HashSet<Callable<Object>>();
    
    GroupExecServiceImpl(int numTasks, Set<MemberInvocation> group){
        log.info("Creating tasks member execution ...");
        createTasks(numTasks, group);
    }
    
    private void createTasks(int numTasks, Set<MemberInvocation> group) {
        int blockSize = calcBlocksize(numTasks, group.size());
        int taskId = 1;
        int eltCount = 0;
        boolean memCheck = true; // assign exactly one task to check for low memory!
        Set<MemberInvocation> memberSubset = new HashSet<MemberInvocation>();
        for(MemberInvocation mi:group) {
            memberSubset.add(mi);
            eltCount++;
            if(eltCount == blockSize) {
                tasks.add(new ExecTask(taskId, memberSubset, memCheck));
                memberSubset = new HashSet<MemberInvocation>();
                eltCount = 0;
                taskId++;
                memCheck = false;
            }
        }
        if(!memberSubset.isEmpty()){
            taskId++;
            tasks.add(new ExecTask(taskId, memberSubset, memCheck));
        }
    }
    private int calcBlocksize(int numTasks, int groupSize) {
        int blockSize = groupSize/numTasks + groupSize%numTasks;
        if(blockSize < 250 && numTasks > 1) {
            return calcBlocksize(numTasks/2,groupSize);
        }
        return blockSize;
    }

    public void invoke(String methodName) {
        log.debug("Configure tasks to invoke " +methodName + " ...");
        for(Callable t : tasks){
            ExecTask execTask = (ExecTask) t;
            execTask.setMethodName(methodName);
        }
        try{ 
            List<Future<Object>> result = execService.invokeAll(tasks);
            for(Future<Object> f:result) {
                // if an exception occured during concurrent exec this
                // call will throw an ExecutionException:
                ExecTask t = (ExecTask) f.get();
            }
        }
        catch (ExecutionException e) { shutdownAndLog(e); } 
        catch (InterruptedException e) { shutdownAndLog(e); }
    }
    private void shutdownAndLog(Exception e) {
        execService.shutdownNow();
        tasks.clear();
        log.exception(e);
    }
    
    protected void finalize() throws Throwable {
        execService.shutdownNow();
        tasks.clear();
        execService = null;
        tasks = null;
    }
}

/**
 * $Log: GroupExecServiceImpl.java,v $
 * Revision 1.6  2006/08/16 13:53:34  janisch
 * - implemented finalize
 * - assign gc trigger responsib. to first task created
 *
 * Revision 1.5  2006/01/25 09:22:33  janisch
 * Last exec task wasn't created correctly resulting
 * in an incomplete set of member subsets.
 *
 * Revision 1.4  2005/11/10 12:30:24  janisch
 * - Replaced raw type usage
 * - Refectored concurrent exec to account for exceptions thrown
 *   during task execution
 *
 * Revision 1.3  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.2  2005/09/05 12:00:44  janisch
 * Removed unnecessary imports
 *
 * Revision 1.1  2005/08/26 11:17:31  janisch
 * Release 1.0.0
 *
 */