package org.glowa.danube.components.actor.touristmodel;

import java.util.GregorianCalendar;
import java.util.Map.Entry;
import java.util.Vector;

import org.glowa.danube.components.actor.utilities.ClimateData;

public class DA_Tourist_Type1 extends DA_Tourist{
	
	public DA_Tourist tourist;
	
	public DA_Tourist_Type1(DA_Tourist tourist) {
		this.tourist = tourist;
	}
	
	public DA_Tourist_Type1() {
	}
	
	public void makeDecision(int month, int day){
		if(month==tourist.bookingMonth && day == tourist.bookingDay){
			Vector<DATA_Destination> possibleDests = new Vector<DATA_Destination>();
			for(Entry<Integer, DATA_Destination> dest:tourist.tm.destinations.entrySet()){
				if(dest.getValue().containsHolidayType(tourist.holidaytype)){
					if(tourist.countries.contains(dest.getValue().country)){
						possibleDests.add(dest.getValue());
					}
					
				}
			}
			if(possibleDests.size()>0){
				DATA_Destination finalDestination = possibleDests.elementAt((int)(Math.random()*possibleDests.size()));
				//System.out.println(possibleDests.size());
				tourist.holidayDestination[0][0] = finalDestination.id;
				tourist.holidayDestination[0][1] = tourist.preferedWeeks[0]-tourist.tm.currentDate.get(GregorianCalendar.WEEK_OF_YEAR);
				tourist.holidayDestination[0][2] = tourist.category;
				tourist.setDestinationChanged();
			}
		}
	}
	
	protected void query(){
		makeDecision(this.tourist.getSimulationTime().getMonth(), this.tourist.getSimulationTime().getDay());
	}
}
