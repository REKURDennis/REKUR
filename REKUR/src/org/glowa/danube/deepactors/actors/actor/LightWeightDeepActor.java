package org.glowa.danube.deepactors.actors.actor;

/**
 * This is an interface for an lightweight deepactor, without plan and actor-maps. Designed for actor-types with huge number of instances.
 * @author Dennis Joswig
 *
 */
public interface LightWeightDeepActor {
	/**
     * You may assume: all basic properties initialized including the plan map
     * and the simulation time is set to the simulation start date
      * @param year current year.
	 * @param month current month.
	 * @param day current day.
	*/
	public void init(int year, int month, int day);
	
	/**
     * You may assume: sensor data and events are up to date. Simulation time
     * had been set to the time provided by Danubia for processGetData
     * @param year current year.
	 * @param month current month.
	 * @param day current day.
	*/
	public void query(int year, int month, int day);
	
	/**
     * You may assume: initially all plans are deactivated. If a plan was
     * activated in the last timestep it is still active in the current timestep
     * The simulation time had not been modified.
      * @param year current year.
	 * @param month current month.
	 * @param day current day.
	*/
	public void options(int year, int month, int day);
	
	 
    /**
     * You may assume: computeRating has been called for all active plans.
     * The simulation time had not been modified.
     * @param year current year.
	 * @param month current month.
	 * @param day current day.
	*/
	public void export(int year, int month, int day);
}
