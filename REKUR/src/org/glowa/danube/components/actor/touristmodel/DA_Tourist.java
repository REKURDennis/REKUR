package org.glowa.danube.components.actor.touristmodel;

import org.glowa.danube.deepactors.actors.actor.AbstractActor;


public class DA_Tourist extends AbstractActor{
	public int[][] holidayDestination= new int[1][3];
	public TouristModel tm;
	public DA_SourceArea origin;
	public int age;
	public int sex;
	public int lifephase;
	
	public DA_AbstractTouristType currentTouristType;
	public History history = new History();
	
	public DA_Tourist(TouristModel tm,DA_SourceArea origin, DA_AbstractTouristType touristType){
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
}
