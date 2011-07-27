package org.glowa.danube.deepactors.actors.exec;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Set;
import java.util.concurrent.Callable;

import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ExecTask.java,v 1.5 2007/10/31 10:16:50 janisch Exp $ 
 */
public class ExecTask implements Callable<Object> {

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ExecTask.class);
    private final boolean memCheck;
    private static final int memCheckNumTrigger = 10000;
    private static final String memCheckMethodTrigger = "decide";
    
    private MemoryMXBean membean;
    private int id;
    private MemberInvocation[] memberArray = {};
    private String methodName;

    
    ExecTask(int taskId, Set<MemberInvocation> mis, boolean doMemCheck){
        log.info("Creating Task "+taskId+" with "+mis.size()+" members ...");        
        id = taskId;
        memberArray = mis.toArray(memberArray);
        memCheck = doMemCheck; 
        membean = ManagementFactory.getMemoryMXBean();
    }
    
    int getTaskId() {return id;}
    void setMethodName(String mn) {methodName = mn;}
    
    public Object call() throws Exception {
        log.info("Task "+id+" is executing "+methodName+" ...");
        int membernum = 0;
        
        for(int i=0, j=0; i<memberArray.length; i++){
            if(memCheck && (membernum != 0) && (membernum%memCheckNumTrigger == 0) 
                    && methodName.equals(memCheckMethodTrigger) 
                    && memCheckFailed()){
                log.info("Trigger garbage collection ...");
                System.gc();
                log.info("GC finished, heap usage is now " + membean.getHeapMemoryUsage().getUsed()/1024);
            }
            memberArray[i].invoke(methodName);
            membernum++;
        }

        log.info("Task " +id+ " done for " + membernum + " member.");
        return null;
    }
    
    
    private boolean memCheckFailed() {
        long curHeap = membean.getHeapMemoryUsage().getUsed();
        long curHeapMax = membean.getHeapMemoryUsage().getMax();
        long threshold = (curHeapMax*70)/100;
        boolean result = (curHeap > threshold);
        if(result){
            log.info("Exceeded memory threshold [" + curHeap/1024 + ", max " + curHeapMax/1024 + 
                    ", threshold " + threshold/1024 + "]");
        }
        return result;
    }
}

/**
 * $Log: ExecTask.java,v $
 * Revision 1.5  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.4  2006/08/16 13:52:25  janisch
 * - introduced mem check in memberSet call: trigger gc if
 *   usage > 70% of max
 * - use MemberInvocation[] instead of Set<MemberInvocation>
 * - add flag to constructor to assign only one thread gc trigger
 *   responsibility
 *
 * Revision 1.3  2005/11/10 12:29:00  janisch
 * - Replace raw type usage
 * - count executed member and log resp. info message
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:33  janisch
 * Release 1.0.0
 *
 */