package org.glowa.danube.components.actor.touristmodel;

import java.util.GregorianCalendar;
import java.util.Map.Entry;
import java.util.Vector;


public class DA_DummyTourist implements TouristTypeInterface{
	
	public DA_Tourist tourist;
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
	
	
	public DA_DummyTourist(DA_Tourist tourist) {
		this.tourist = tourist;
		this.countries.add(6);
	}
	
	public DA_DummyTourist() {
	}
	
	public void makeDecision(int month, int day){
		if(month==bookingMonth && day == bookingDay){
			Vector<DATA_Destination> possibleDests = new Vector<DATA_Destination>();
			for(Entry<Integer, DATA_Destination> dest:tourist.tm.destinations.entrySet()){
				if(dest.getValue().containsHolidayType(holidaytype)){
					if(countries.contains(dest.getValue().country)){
						possibleDests.add(dest.getValue());
					}
					
				}
			}
			if(possibleDests.size()>0){
				DATA_Destination finalDestination = possibleDests.elementAt((int)(Math.random()*possibleDests.size()));
				//System.out.println(possibleDests.size());
				tourist.holidayDestination[0][0] = finalDestination.id;
				tourist.holidayDestination[0][1] = preferedWeeks[0]-tourist.tm.currentDate.get(GregorianCalendar.WEEK_OF_YEAR);
				tourist.holidayDestination[0][2] = category;
				tourist.setDestinationChanged();
			}
		}
	}
	
	protected void query(){
		makeDecision(this.tourist.getSimulationTime().getMonth(), this.tourist.getSimulationTime().getDay());
	}
}
