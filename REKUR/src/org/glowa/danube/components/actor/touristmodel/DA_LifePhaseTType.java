package org.glowa.danube.components.actor.touristmodel;

import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;
import org.glowa.danube.components.actor.utilities.Holidays;

/**
 * This class is tourist type. The life phases combined with personal tourist information (age and location) are responsible for the holiday decision.
 * @author Dennis Joswig
 *
 */
public class DA_LifePhaseTType extends DA_AbstractTouristType {
	@Override
	public void makeDecision(int year, int month, int day, DA_Tourist delegate){
		
		if(!delegate.tm.preSimulation && delegate.age>13 && month==bookingMonth && day == bookingDay){
			int r = (int)(Math.random() * 1000.0);
			float[] probs = new float[9];
			for(int i = 0;i<probs.length;i++){
				probs[i] = delegate.tm.LP_Probabilities[delegate.lifephase][delegate.origin.cityStatus][delegate.ageCategory][i];
			}
			
			int smallerThan = 0;
			int ht = 0;
			for(int i = 0; i<probs.length;i++){
				int biggerThan = smallerThan;
				smallerThan+=(int)(probs[i]*10.0);
				if(r>biggerThan && r < smallerThan){
					ht = i;
				}
			}
			if (smallerThan==0){
			//if(delegate.lifephase !=0){	
				System.out.println(year+" "+month+" "+day+" "+delegate.origin.landkreisId+" "+delegate.lifephase+" "+delegate.ageCategory);
				
			}
			HashMap<Integer, Vector<Integer>> weeks  = new HashMap<Integer, Vector<Integer>>();
			//Berechnung der Reisezeit:
			//Arbeit+Kinder+Strand = Sommerferien
			//Arbeit+Kinder+SKi= Weihnachten+Osterferien
			//Arebit+Kinder+Rest= gleichverteilt
			//Rest nach Saisonalitäte
			if(ID==3 || ID==4){
				Holidays h = new Holidays();
				int state = delegate.origin.landkreisId/1000;
				int type = 0;
				if(ht ==1){
					type = Holidays.SOMMERFERIEN;
				}
				else{
					if(ht == 3){
						int re = (int)(Math.random()*2.0);
						if(re ==0){
							type = Holidays.WEIHNACHTSFERIEN;
						}
						else{
							type = Holidays.OSTERFERIEN;
						}
					}
					else{
						int[] hWeeks=null;
						while(hWeeks == null){
							type = (int)((Math.random()*6.0)+1.0);
							hWeeks = h.getHolidays(type, state, year);
							
						}
					}
				}
				
				preferedJourneyWeeks = new Vector<Integer>();
				int[] hWeeks=null;
//				System.out.println(type+" "+state+" "+year);
				hWeeks = h.getHolidays(type, state, year);
				int weekBucket = (int)(Math.random()*(double)(hWeeks.length));
				preferedJourneyWeeks.add(hWeeks[weekBucket]);
				
			}
			weeks.put(year, preferedJourneyWeeks);
//			Vector<DATA_Destination> possibleDests = new Vector<DATA_Destination>();
//			//Nach holidaytype auswaehlen
//			for(Entry<Integer, DATA_Destination> dest:delegate.tm.activedests.entrySet()){
//				if(dest.getValue().containsHolidayType(ht)){
//					possibleDests.add(dest.getValue());
//				}
//			}
			
			//Nach Entfernung auswaehlen
			double rEnt = Math.random();
			double[] entProbs = delegate.tm.LPentfernungen[ID];
			int bucket=0;
			double currentValue= entProbs[0];
			for(int i = 1;i<entProbs.length;i++){
				if(rEnt>currentValue){
					currentValue+=entProbs[i];
					bucket = i;
				}
			}
			int mindistance = 0;
			int maxdistance = 500000;
			if(bucket == 1){
				mindistance = 500000;
				maxdistance = 1000000;
			}
			if(bucket == 2){
				mindistance = 1000000;
				maxdistance = 10000000;
			}
			
			
//			Vector<DATA_Destination> removeDests = new Vector<DATA_Destination>();
			Vector<DATA_Destination> possibleDests = new Vector<DATA_Destination>();
			for(Entry<Integer, DATA_Destination> dest:delegate.tm.activedests.entrySet()){
				DATA_Destination currentdest = dest.getValue();
				//Nach holidaytype auswaehlen
				if(dest.getValue().containsHolidayType(ht)){
					
					int dist = delegate.tm.distanceMatrix.get(delegate.origin).get(currentdest.id);
					//Nach Entfernung auswaehlen
					if(dist>mindistance&&dist>maxdistance){
//						removeDests.add(currentdest);
						int minPrice = currentdest.costs[0]+(int)((double)dist*0.02);
						int maxPrice = currentdest.costs[1]+(int)((double)dist*0.02)+delegate.tm.nklaender.get(currentdest.country);
						//Nach budget auswaehlen
						if(minPrice<delegate.budget && maxPrice>delegate.budget){
							//nach klima auswaehlen
							if(true){
								//nach Erinnerung auswaehlen
								if(true){
									possibleDests.add(currentdest);
								}
							}
						}
					}
				}
			}
//			for(DATA_Destination currentDest:removeDests){
//				possibleDests.remove(currentDest);
//			}
			
			
			if(possibleDests.size()>0){
				//Groesse der Destinationen beruecksichtigen
				DATA_Destination finalDestination = possibleDests.elementAt((int)(Math.random()*possibleDests.size()));
				//System.out.println(possibleDests.size());
					
				delegate.nextJourney = new Journey(weeks, finalDestination.id, category, delegate.origin.getId(), delegate);
				delegate.setDestinationChanged();
			}
		}
	}
}
