package org.glowa.danube.components.actor.touristmodel;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;
/**
 * This abstract class provides attributes and methods for each concrete tourist type.
 * @author Dennis Joswig
 *
 */
public class DA_AbstractTouristType {
	/**
	 * Saves the tourist type id.
	 */
	public int ID;
	/**
	 * Saves a vector with all possible holiday types.
	 */
	public Vector<Integer> holidaytypes= new Vector<Integer>();
	/**
	 * Saves a vector with all possible activity types.
	 */
	public Vector<Integer> activityTypes;
	/**
	 * Saves a vector with all possible countries.
	 */
	public Vector<Integer> countries= new Vector<Integer>();
	
	/**
	 * Saves the climate preferences of this tourist type.
	 */
	public TouristClimatePreferences tcp = new TouristClimatePreferences();
	/**
	 * Saves a vector with all possible weeks for traveling.
	 */
	public Vector<Integer> preferedJourneyWeeks = new Vector<Integer>();
	/**
	 * Saves the length of the journey.
	 */
	public int holidayLength;
	/**
	 * Saves the categroy for the journey.
	 */
	public int category;
	/**
	 * Saves the month to book.
	 */
	public int bookingMonth;
	/**
	 * Saves the day to book.
	 */
	public int bookingDay;
	
	/**
	 * Decides to travel.
	 * @param year current year.
	 * @param month current month.
	 * @param day current day.
	 * @param delegate the Tourist-Object for a call-back.
	 */
	public void makeDecision(int year, int month, int day, DA_Tourist delegate){
	}
	
	
	/**
	 * Checks the capacity of the possibleDests and gives back an vector with all destinations with enough capacities.
	 * @param dests Destinations to check.
	 * @param tourist tourist to callback.
	 * @param weeksPerYear journey-weeks.
	 * @param category journey category.
	 * @return
	 */
	private Vector<DATA_Destination> checkCapacity(Vector<DATA_Destination> dests, DA_Tourist tourist, HashMap<Integer, Vector<Integer>> weeksPerYear,int category){
		Vector<DATA_Destination> destsWithCapa = new Vector<DATA_Destination>();
		Vector<DATA_Destination> blacklist = new Vector<DATA_Destination>();
		for(Entry<Integer, Vector<Integer>> years: weeksPerYear.entrySet()){
			for(Integer week:years.getValue()){
				for(DATA_Destination d : dests){
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
