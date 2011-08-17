package org.glowa.danube.components.actor.touristmodel;

import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.Map.Entry;

public class DA_AbstractTouristType {
	public int holidaytype;
	public int activityType;
	public Vector<Integer> countries= new Vector<Integer>();
	public int maxTemp;
	public int minTemp;
	public int avgTemp;
	public int preferedWeeks[];
	public int holiydayLength;
	public int category;
	public int bookingMonth;
	public int bookingDay;
	
	
	public void makeDecision(int year, int month, int day, DA_Tourist delegate){
		if(month==bookingMonth && day == bookingDay){
			Vector<DATA_Destination> possibleDests = new Vector<DATA_Destination>();
			for(Entry<Integer, DATA_Destination> dest:delegate.tm.destinations.entrySet()){
				if(dest.getValue().containsHolidayType(holidaytype)){
					if(countries.contains(dest.getValue().country)){
						possibleDests.add(dest.getValue());
					}
					
				}
			}
			if(possibleDests.size()>0){
				DATA_Destination finalDestination = possibleDests.elementAt((int)(Math.random()*possibleDests.size()));
				//System.out.println(possibleDests.size());
				delegate.holidayDestination[0][0] = finalDestination.id;
				delegate.holidayDestination[0][1] = preferedWeeks[0]-delegate.tm.currentDate.get(GregorianCalendar.WEEK_OF_YEAR);
				delegate.holidayDestination[0][2] = category;
				delegate.setDestinationChanged();
			}
		}
	}
	
	public void query(int year, int month, int day, DA_Tourist delegate){
		makeDecision(year, month, day, delegate);
	}

	public void init(int year, int month, int day, DA_Tourist delegate) {
		// TODO Auto-generated method stub
		
	}

	public void options(int year, int month, int day, DA_Tourist delegate) {
		// TODO Auto-generated method stub
		
	}

	public void export(int year, int month, int day, DA_Tourist delegate) {
		// TODO Auto-generated method stub
		
	}
	
}
