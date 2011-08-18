package org.glowa.danube.components.actor.touristmodel;

import java.util.GregorianCalendar;
import java.util.Map.Entry;
import java.util.Vector;


public class DA_DummyTouristType extends DA_AbstractTouristType{

	public DA_DummyTouristType() {
		this.countries.add(6);
	}
	
	public void makeDecision(int year, int month, int day, DA_Tourist delegate){
		if(!delegate.tm.preSimulation){
			if(month==bookingMonth && day == bookingDay){
				Vector<DATA_Destination> possibleDests = new Vector<DATA_Destination>();
				for(Entry<Integer, DATA_Destination> dest:delegate.tm.destinations.entrySet()){
					if(dest.getValue().containsHolidayType(holidaytypes.get(0))){
						if(countries.contains(dest.getValue().country)){
							possibleDests.add(dest.getValue());
						}
					}
				}
				if(possibleDests.size()>0){
					DATA_Destination finalDestination = possibleDests.elementAt((int)(Math.random()*possibleDests.size()));
					//System.out.println(possibleDests.size());
					delegate.holidayDestination[0][0] = finalDestination.id;
					delegate.holidayDestination[0][1] = preferedWeeks.get(0)-delegate.tm.currentDate.get(GregorianCalendar.WEEK_OF_YEAR);
					delegate.holidayDestination[0][2] = category;
					delegate.setDestinationChanged();
				}
			}
		}
	}
}
