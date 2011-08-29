package org.glowa.danube.components.actor.touristmodel;

import org.glowa.danube.deepactors.actors.actor.LightWeightDeepActor;

/**
 * 	This class is used to instantiate the tourist-objects.
 * @author Dennis Joswig
 *
 */
public class DA_Tourist implements LightWeightDeepActor{	
	/**
	 * Saves the current booking journey.
	 */
	public Journey nextJourney;
	/**
	 * Saves the touristModel-Object.
	 */
	public TouristModel tm;
	/**
	 * Saves the origin SourceArea of the tourist.
	 */
	public DA_SourceArea origin;
	/**
	 * Saves the age of the tourist.
	 */
	public int age;
	/**
	 * Saves the sex of the tourist.
	 */
	public int sex;
	/**
	 * Saves the lifephase of the tourist.
	 */
	public int lifephase;
	
	/**
	 * References the current touristTpye.
	 */
	public DA_AbstractTouristType currentTouristType;
	
	/**
	 * Saves the holidayHistory.
	 */
	public History history = new History();
	/**
	 * Constructor of the class instantiates all attributes.
	 * @param tm Reference to the TouristModel-Object.
	 * @param origin Reference to the SourceArea.
	 * @param touristType Reference to the touristType.
	 * @param age current age.
	 * @param sex sex of the tourist.
	 * @param lp the tourist lifephase.
	 */
	public DA_Tourist(TouristModel tm,DA_SourceArea origin, DA_AbstractTouristType tType, int age, int sex, int lp){
		this.tm = tm;
		this.origin = origin;
		currentTouristType = tType;
	}
	/**
	 * Method for redecision process.
	 * @param year current year.
	 * @param month current month.
	 * @param day current day.
	 */
	public void makeDecision(int year, int month, int day){
		currentTouristType.makeDecision(year, month, day, this);
	}
	/**
	 * Sets in touristModel-Object the new holiday-data.
	 */
	protected void setDestinationChanged(){
		tm.setDestinationChanged(this);
	}
	/**
	 * Clones the Tourist and gives ist back.
	 */
	public DA_Tourist clone(){
		DA_Tourist clone = new DA_Tourist(tm, origin, currentTouristType, age, sex, lifephase);
		clone.history = history.clone();
		return clone;
	}
	@Override
	public void init(int year, int month, int day) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void query(int year, int month, int day) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void options(int year, int month, int day) {
		currentTouristType.makeDecision(year, month, day, this);
	}
	@Override
	public void export(int year, int month, int day) {
		// TODO Auto-generated method stub
		
	}
}
