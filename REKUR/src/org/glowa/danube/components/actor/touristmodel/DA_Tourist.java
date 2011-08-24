package org.glowa.danube.components.actor.touristmodel;

import org.glowa.danube.components.actor.utilities.Journey;
import org.glowa.danube.deepactors.actors.actor.AbstractActor;


public class DA_Tourist extends AbstractActor{
	//public int[][] holidayDestination= new int[1][3];
	
	public Journey nextJourney;
	
	public TouristModel tm;
	public DA_SourceArea origin;
	public int age;
	public int sex;
	public int lifephase;
	
	public DA_AbstractTouristType currentTouristType;
	public History history = new History();
	
	public DA_Tourist(TouristModel tm,DA_SourceArea origin, DA_AbstractTouristType touristType, int age, int sex, int lifephase){
		this.tm = tm;
		this.origin = origin;
		currentTouristType = touristType;
	}
	public void makeDecision(int year, int month, int day){
		currentTouristType.makeDecision(year, month, day, this);
	}
	protected void setDestinationChanged(){
		tm.setDestinationChanged(this);
	}
	
	public DA_Tourist clone(){
		DA_Tourist clone = new DA_Tourist(tm, origin, currentTouristType, age, sex, lifephase);
		clone.history = history.clone();
		return clone;
	}
}
