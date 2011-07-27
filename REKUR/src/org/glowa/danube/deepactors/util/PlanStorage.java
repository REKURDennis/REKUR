package org.glowa.danube.deepactors.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.glowa.danube.deepactors.actors.actor.ActionEnvironment;
import org.glowa.danube.deepactors.actors.plan.Plan;
import org.glowa.danube.deepactors.actors.plan.PlanCore;
import org.glowa.danube.deepactors.actors.plan.PlanFactory;
import org.glowa.danube.deepactors.actors.plan.PlanFactory.PlanRoles;

/**
 * Allows an actor (core and base) to dynamically load and remove plan objects. After creation of
 * a new plan object its initialization procedure is invoked immediately.
 *   
 * @author janisch
 * @version $Id: PlanStorage.java,v 1.3 2007/11/28 10:27:34 janisch Exp $
 */
public class PlanStorage {

    //private final DeepActorLogger log =
    //    DeepActorLogger.newInstance(PlanStorage.class);
    
    private final int actorId;
    private final ActionEnvironment actionEnv;
    private final PlanFactory planFact;

    private Map<Integer, Plan> keyPlanBaseMap;
    private Map<Integer, PlanCore> keyPlanCoreMap;
    private TreeSet<Integer> loadedPlanKeys;
    
    /**
     * Creates an empty plan storage which uses pf to create plans for the given actor and
     * action environment.
     * 
     * @param actorId of the actor which owns this plan storage
     * @param ae action environment passed to the actions of newly created plans
     * @param pf plan factory used to create plans
     */
    public PlanStorage(int actorId, ActionEnvironment ae, PlanFactory pf){
        this.actorId = actorId;
        this.actionEnv = ae;
        this.planFact = pf;
        this.loadedPlanKeys = new TreeSet<Integer>();
        this.keyPlanBaseMap = new HashMap<Integer,Plan>();
        this.keyPlanCoreMap = new HashMap<Integer,PlanCore>();
    }
    
    private int[] planIdArray = {};
    /**
     * Create, initalize and add corresponding plan objects to the current set of plans. If the plan
     * object for i in planIds already exists, this i is ignored.
     * @param planIds
     */
    public void load(Set<Integer> planIds){
        if(!loadedPlanKeys.equals(planIds)){
            Set<Integer> idsToLoad = new HashSet<Integer>(planIds);
            idsToLoad.removeAll(loadedPlanKeys);
            // create *and* initialize
            planIdArray = new int[idsToLoad.size()];
            int idx = 0;
            for(int i:idsToLoad){
                planIdArray[idx] = i; 
                idx++;
            }
            
            Set<PlanRoles> prSet = planFact.createRolesSet(planIdArray, actorId, actionEnv);
            for(PlanRoles pr:prSet){
                PlanCore planCore = pr.getPlanCore();
                int id = planCore.getId();
                planCore.init(); 
                loadedPlanKeys.add(id);
                keyPlanBaseMap.put(id,pr.getPlan());
                keyPlanCoreMap.put(id,planCore);
            }
        }
    }
    
    /**
     * Remove corresponding plan objects from the currently loaded set of plans. If the plan object
     * for i in planIds has been removed already, this i is ignored.
     * @param planIds
     */
    public void remove(Set<Integer> planIds){
        // if client used this.getLoadedIds for planIds we can not rm from loadedPlanKey while 
        // iterating over planIds. Therefore construct a local iterator:
        Set<Integer> idsToRemove = new HashSet<Integer>(planIds);
        for(int i:idsToRemove){
            loadedPlanKeys.remove(i);
            keyPlanBaseMap.remove(i);
            keyPlanCoreMap.remove(i);
        }
    }
    
    /**
     * Returns an unmodifable set of IDs of the currently loaded plans sorted in ascending order.
     * @return
     */
    public SortedSet<Integer> getLoadedIds(){
        return Collections.unmodifiableSortedSet(loadedPlanKeys);
    }
    
    /**
     * Returns an unmodifiable collection of base objects of all currently loaded plan objects.
     * @return
     */
    public Collection<Plan> getPlanBaseObjs(){
        return Collections.unmodifiableCollection(keyPlanBaseMap.values());
    }
    
    /**
     * Returns unmodifiable collections of core objs of all currently loaded plan objects.
     * @return
     */
    public Collection<PlanCore> getPlanCoreObjs(){
        return Collections.unmodifiableCollection(keyPlanCoreMap.values());
    }
}


/**
 * $Log: PlanStorage.java,v $
 * Revision 1.3  2007/11/28 10:27:34  janisch
 * - Update to Danubia 2.1.1
 * - Bugfix AbstractActorModel: query amd not via proxeltable but from am
 * - Align and fix unit tests
 * - Eclipse compiler settings: ignore raw type warnings
 * - Resolved any other compiler warning
 *
 * Revision 1.2  2007/10/29 09:37:13  janisch
 * - Aligned package imports to Danubia 2.0
 * - Removed exception handling for removed exception types ModelComputeException ...
 *
 * Revision 1.1  2006/08/16 13:59:36  janisch
 * These classes implement user and framework part of the
 * dynamic plan loading mechanism.
 *
 *
 */