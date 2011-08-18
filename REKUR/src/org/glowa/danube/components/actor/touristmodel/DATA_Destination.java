package org.glowa.danube.components.actor.touristmodel;

import java.util.HashMap;
import java.util.Vector;

import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.components.actor.utilities.IntegerArray2D;
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
	 * saves the Country-ID of the Destination.
	 */
	public int country;
	/**
	 * saves the unique Destination-ID
	 */
	public int id;
	
	/**
	 * Holds the beCapacities per Year, Week within this Year(0-53) and price-category.
	 * int[year][week][category]
	 */
	public int[][][] bedCapacities;
	
	/**
	 * Holds the numberOfTourists per Year, Week within this Year(0-53) and price-category.
	 * int[year][week][category]
	 */
	public int[][][] numberOfTourists;
	
	//Todo Liste mit Touristen
	
	
	public HashMap<DA_SourceArea, HashMap<Integer,Vector<DA_Tourist>>> nowBookingTourists = new HashMap<DA_SourceArea, HashMap<Integer,Vector<DA_Tourist>>>();
	
	public HashMap<DA_SourceArea,IntegerArray2D> CurrentNumberOfTourists = new HashMap<DA_SourceArea, IntegerArray2D>();
	public HashMap<DA_SourceArea,IntegerArray2D> numberOfTouristsLastDate = new HashMap<DA_SourceArea, IntegerArray2D>();
	
	
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
	 * @param i Holidaytype number.
	 * @return <code>true</code> if contains this type <code>false</code> if not.
	 */
	public boolean containsHolidayType(int i){
		
		if(holidayTypes[i])return true;
		else return false;
		
	}
	
	public boolean checkNumberOfTourists(){
		
		return false;
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
