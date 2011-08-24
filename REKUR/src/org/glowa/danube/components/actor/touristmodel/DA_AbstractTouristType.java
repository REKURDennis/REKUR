package org.glowa.danube.components.actor.touristmodel;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

public class DA_AbstractTouristType {
	public Vector<Integer> holidaytypes= new Vector<Integer>();
	public Vector<Integer> activityTypes;
	public Vector<Integer> countries= new Vector<Integer>();
	public int maxTemp;
	public int minTemp;
	public int avgTemp;
	public Vector<Integer> preferedJourneyWeeks = new Vector<Integer>();
	public int holidayLength;
	public int category;
	public int bookingMonth;
	public int bookingDay;
	
	
	public void makeDecision(int year, int month, int day, DA_Tourist delegate){
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
	public Vector<DATA_Destination> checkCapacity(Vector<DATA_Destination> possibleDests, DA_Tourist tourist, HashMap<Integer, Vector<Integer>> weeksPerYear,int category){
		Vector<DATA_Destination> destsWithCapa = new Vector<DATA_Destination>();
		Vector<DATA_Destination> blacklist = new Vector<DATA_Destination>();
		for(Entry<Integer, Vector<Integer>> years: weeksPerYear.entrySet()){
			for(Integer week:years.getValue()){
				for(DATA_Destination d : possibleDests){
					if((d.bedCapacities==null) ||d.freeBeds.get(years.getKey()).get(week).get(category)>0){
						if(!(destsWithCapa.contains(d)) && !(blacklist.contains(d))){
							destsWithCapa.add(d);
						}
					}
					else{
						if(destsWithCapa.contains(d)){
							destsWithCapa.remove(d);
						}
						blacklist.add(d);
					}
				}
			}
		}
			
		return destsWithCapa;
	}
	
}
