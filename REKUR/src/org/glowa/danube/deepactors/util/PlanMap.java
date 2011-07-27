package org.glowa.danube.deepactors.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.glowa.danube.deepactors.actors.plan.Plan;

/**
 * Utility class to manage dynamic loading of plan objects.
 * 
 * @author janisch
 * @version $Id: PlanMap.java,v 1.1 2006/08/16 13:59:36 janisch Exp $
 */
public final class PlanMap{

    private final SortedSet<Integer> available;
    private SortedSet<Integer> retained;
    private PlanStorage planStore;
    
    /**
     * Initially all available plans are set to be retained. 
     * @param availablePlanIds
     * @param ps
     */
    public PlanMap(Set<Integer> availablePlanIds, PlanStorage ps){
        available = new TreeSet<Integer>(availablePlanIds);
        retained = new TreeSet<Integer>(availablePlanIds);
        planStore = ps;
    }
    
    /**
     * The set of IDs of available plans as specified in the plan initialisation file.
     */
    public SortedSet<Integer> available(){
        return available;
    }
    
    /**
     * The set of all plan IDs which are currently loaded. 
     */
    public SortedSet<Integer> loaded(){
        return planStore.getLoadedIds();
    }
    
    /**
     * The set of IDs which were specified to retain.
     */
    public SortedSet<Integer> retained(){
        return retained;
    }

    /**
     * Loads the corresponding plans destructively, i.e. plans loaded before are removed unless
     * their ID was specified to retain {@link PlanMap#retain(Set)}. 
     * ToDo: remove only in case of low memory
     *  
     * Post (is not incremental): getPlanMap(Plan.class) returns plan objects according to planIds 
     * and those previously loaded *and* specified to retain. 
     *  
     * @param ids of plan objects to be loaded.
     * @return this (to enable expressions like pm.load(x).getPlanMap(y))
     */
    public PlanMap load(Set<Integer> planIds){
        HashSet<Integer> removeIds = new HashSet<Integer>(loaded());
        removeIds.removeAll(retained);
        planStore.remove(removeIds);
        planStore.load(planIds);
        return this;
    }
    
    /**
     * Singleton version of {@link PlanMap#load(Set)}.
     * @param planId
     * @return this (to enable expressions like pm.load(x).getPlanMap(y))
     */
    public PlanMap load(int planId){
        this.load(Collections.singleton(planId));
        return this;
    }
    
    /**
     * Specifies the set of plans which should be retained by load {@link PlanMap#load(Set)}. Note
     * that this operation is not incremental, i.e. ids specified to be retained before are replaced
     * by the given set of plan ids. 
     * @param planIds
     */
    public void retain(Set<Integer> planIds){
        retained.clear();
        retained.addAll(planIds);
    }
    
    /**
     * Singleton version of {@link PlanMap#retain(Set)}.
     * @param planId
     */
    public void retain(int planId){
        retained.clear();
        retained.add(planId);
    }
    
    private MutableDataMap<Plan> planMap = new MutableDataMap<Plan>();
    /**
     * Constructs a data map for the currently loaded plan objects of type planType.
     * @param <P> the plan type whose objects should be returned
     * @param planType the class object corresponding to P, e.g. CloseBusiness.class
     * @return a data map comprising all existing plan objects of type P
     */
    public <P extends Plan> DataMap<P> getPlanMapProjection(Class<P> planType){
        Collection<Plan> plans = getPlans();
        if(planMap.getEntries().equals(plans)){
            return planMap.getEntries(planType);
        }
        planMap.removeAll();
        for(Plan p:plans){
            planMap.add(p);
        }
        return planMap.getEntries(planType);
    }

    /**
     * Constructs a data map for all currently loaded plan objects.
     * @return a data map comprising all existing plan objects
     */
    public DataMap<Plan> getPlanDataMap(){
        Collection<Plan> plans = getPlans();
        if(planMap.getEntries().equals(plans)){
            return planMap;
        }
        planMap.removeAll();
        for(Plan p:plans){
            planMap.add(p);
        }
        return planMap;
    }

    /**
     * Returns an unmodifiable set of the currently loaded plan objects. 
     * @return
     */
    public Collection<Plan> getPlans(){
        return planStore.getPlanBaseObjs();
    }
}


/**
 * $Log: PlanMap.java,v $
 * Revision 1.1  2006/08/16 13:59:36  janisch
 * These classes implement user and framework part of the
 * dynamic plan loading mechanism.
 *
 *
 */