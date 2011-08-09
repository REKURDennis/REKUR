package org.glowa.danube.components.actor.touristmodel;

import java.util.Vector;

import org.glowa.danube.deepactors.actors.actor.AbstractActor;


public class DA_Tourist extends AbstractActor{
	public int[][] holidayDestination= new int[1][3];
	public TouristModel tm;
	public DA_SourceArea origin;
	public int holidaytype = 1;
	public int activityType= 1;
	public Vector<Integer> countries= new Vector<Integer>();
	public int maxTemp = 35;
	public int minTemp = 25;
	public int avgTemp = 29;
	public int preferedWeeks[] = {20,21};
	public int possibleWeeks[] = {19,20,21,22};
	public int holiydayLength = 2;
	public int category = 1;
	public int bookingMonth = 1;
	public int bookingDay = 10;
	public int age;
	public DA_Tourist currentTouristType;
	public History history = new History();
	
	public DA_Tourist(TouristModel tm,DA_SourceArea origin){
		this.tm = tm;
		this.origin = origin;
		this.countries.add(6);
		currentTouristType = new DA_Tourist_Type1(this);
	}
	
	public DA_Tourist(){
	}
	
	public void makeDecision(){
		holidayDestination[0][0] = 1;
		holidayDestination[0][1] = 2;
		holidayDestination[0][2] = 3;
		setDestinationChanged();
	}
	public void makeDecision(int month, int day){
		currentTouristType.makeDecision(month, day);
	}
	protected void setDestinationChanged(){
		tm.setDestinationChanged(this);
	}
}
