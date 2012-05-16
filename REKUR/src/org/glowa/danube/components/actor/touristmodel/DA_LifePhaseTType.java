package org.glowa.danube.components.actor.touristmodel;

import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

/**
 * This class is tourist type. The life phases combined with personal tourist information (age and location) are responsible for the holiday decision.
 * @author Dennis Joswig
 *
 */
public class DA_LifePhaseTType extends DA_AbstractTouristType {
	@Override
	public void makeDecision(int year, int month, int day, DA_Tourist delegate){
		
		if(!delegate.tm.preSimulation){
			int r = (int)(Math.random() * 100.0);
			currentProbability = (int)((double)(currentProbability)*increaseFactor);
			if(month==bookingMonth && day == bookingDay && r<currentProbability){
				currentProbability = travelProbability;
				HashMap<Integer, Vector<Integer>> weeks  = new HashMap<Integer, Vector<Integer>>();
				weeks.put(year, preferedJourneyWeeks);
				Vector<DATA_Destination> possibleDests = new Vector<DATA_Destination>();
				for(Entry<Integer, DATA_Destination> dest:delegate.tm.activedests.entrySet()){
					if(dest.getValue().containsHolidayType(holidaytypes.get(0))){
						if(countries.contains(dest.getValue().country)){
							possibleDests.add(dest.getValue());
						}
					}
				}
				if(possibleDests.size()>0){
					//possibleDests = checkCapacity(possibleDests, delegate, weeks ,category);
					DATA_Destination finalDestination = possibleDests.elementAt((int)(Math.random()*possibleDests.size()));
					//System.out.println(possibleDests.size());
					
					delegate.nextJourney = new Journey(weeks, finalDestination.id, category, delegate.origin.getId(), delegate);
					delegate.setDestinationChanged();
				}
			}
		}
	}
}
