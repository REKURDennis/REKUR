package org.glowa.danube.components.actor.touristmodel;

import java.util.HashMap;
import java.util.Vector;

import org.glowa.danube.components.actor.utilities.ClimateData;
/**
 * This class instantiates objects to save Destination-Information within the TouristModel.
 * @author Dennis Joswig
 *
 */
public class DATA_Destination {
	/**
	 * Saves an array with the availability of holiday types.
	 */
	public boolean[] holidayTypes;
	/**
	 * Saves an array with the availability of holiday activities.
	 */
	public boolean[] holidayActivities;
	/**
	 * Holds the Avg price per Category for each Year, Month and Category
	 * int[year][week][category]
	 */
	public int[][][] costsPerCategory;
	/**
	 * saves the min and max costs costs[0]=min costs[1]=max
	 */
	public int[] costs = new int[2];
	/**
	 * saves the Country-ID of the Destination.
	 */
	public int country;
	/**
	 * saves the unique Destination-ID
	 */
	public int id;
	
	/**
	 * Holds the beCapacities per Year, Week within this Year(0-53) and price-category.
	 * int[weekOfYear][category]
	 */
	public int[][] bedCapacities;
	
	/**
	 * holds the number of free beds per year, week, category HashMap<Year, HashMap<week, HashMap<category, quantity>>>
	 */
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> freeBeds = new HashMap<Integer, HashMap<Integer,HashMap<Integer,Integer>>>();
	/**
	 * HashMap<Year<HashMap<Week, HashMap<Category, HashMap<SourceID, HashMap<Type,HashMap<age, HashMap<sex, Number>>>>>();
	 */
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer[]>>>>>>> touristsPerTimeSourceAndCat = new HashMap<Integer, HashMap<Integer,HashMap<Integer,HashMap<Integer,HashMap<Integer,HashMap<Integer,HashMap<Integer,Integer[]>>>>>>>();
	//public int[][][][] touristsPerSource; 
	
	/**
	 * saves the current booking tourist-journeys HashMap<year, HashMap<week, HashMap<category, Vector<journey>>>>.
	 */
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, Vector<Journey>>>> bookingJourneys = new HashMap<Integer, HashMap<Integer,HashMap<Integer,Vector<Journey>>>>();
	
	/**
	 * Saves the daily climate of the last 28 days.
	 */
	public Vector<ClimateData> climateOfLast28DaysHistory = new Vector<ClimateData>();
	
	/**
	 * Saves the monthly climatedata of the last five years.
	 */
	public HashMap<Integer, HashMap<Integer, ClimateData>> fiveYearClimateDataHistory = new HashMap<Integer, HashMap<Integer,ClimateData>>();
	
	/**
	 * This method checks if the Destination contains the holydaytype with the number i.
	 * @param type Holidaytype number.
	 * @return <code>true</code> if contains this type <code>false</code> if not.
	 */
	public boolean containsHolidayType(int type){
		
		if(holidayTypes[type])return true;
		else return false;
		
	}
	/**
	 * Adds the climatedata to the 28 day history.
	 * @param cd climatedata to add.
	 */
	public void updateDailyClimate(ClimateData cd){
		climateOfLast28DaysHistory.add(cd);
		if(climateOfLast28DaysHistory.size() > 28){
			climateOfLast28DaysHistory.remove(0);
		}
	}
	
	/**
	 * This method updates the monthly climatedata-hashmap.
	 * @param year current year.
	 * @param month current month.
	 * @param cd monthly climatedata to add.
	 */
	public void updateMonthlyClimate(int year, int month, ClimateData cd){
		if(fiveYearClimateDataHistory.containsKey(year)){
			fiveYearClimateDataHistory.get(year).put(month, cd);
		}
		else{
			HashMap<Integer, ClimateData> newYearHistory = new HashMap<Integer, ClimateData>();
			newYearHistory.put(month, cd);
			fiveYearClimateDataHistory.put(year,newYearHistory);
		}
		if(fiveYearClimateDataHistory.containsKey(year-5)){
			if(fiveYearClimateDataHistory.get(year-5).containsKey(month)){
				fiveYearClimateDataHistory.get(year-5).remove(month);
			}
			if(fiveYearClimateDataHistory.get(year-5).size()==0){
				fiveYearClimateDataHistory.remove(year-5);
			}
		}
	}
}
