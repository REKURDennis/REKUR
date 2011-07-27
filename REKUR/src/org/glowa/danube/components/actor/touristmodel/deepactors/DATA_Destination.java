package org.glowa.danube.components.actor.touristmodel.deepactors;

import java.util.HashMap;
import java.util.Vector;

import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.components.actor.utilities.IntegerArray2D;

public class DATA_Destination {
	public boolean[] holidayTypes;
	public int[] holidayActivities;
	/**
	 * Holds the Avg price per Year, Month and Category
	 * HashMap<Year, HashMap<Month, HashMap<Category, Price>>>
	 */
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> costsPerCategory = new HashMap<Integer, HashMap<Integer,HashMap<Integer,Integer>>>();
	//public int[][] freeBeds;
	/**
	 * saves the Country-ID of the Destination.
	 */
	public int country;
	/**
	 * saves the unique Destination-ID
	 */
	public int id;
	
	/**
	 * Holds the beCapacities per Year(20XX), Week within this Year(0-52/53) and price-category.
	 * HashMap<Year, HashMap<Week, HashMap<Category, NumberOfBeds>>
	 */
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> bedCapacities = new HashMap<Integer, HashMap<Integer,HashMap<Integer, Integer>>>();
	
	/**
	 * Holds the numberOfTourists per Year(20XX), Week within this Year(0-52/53) and price-category.
	 * HashMap<Year, HashMap<Week, HashMap<Category, NumberOfBeds>>
	 */
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> numberOfTourists = new HashMap<Integer, HashMap<Integer,HashMap<Integer, Integer>>>();
	
	//Todo Liste mit Touristen
	public HashMap<DA_SourceArea,IntegerArray2D> CurrentNumberOfTourists = new HashMap<DA_SourceArea, IntegerArray2D>();
	public HashMap<DA_SourceArea,IntegerArray2D> numberOfTouristsLastDate = new HashMap<DA_SourceArea, IntegerArray2D>();
	
	public Vector<ClimateData> climateOfLast28DaysHistory = new Vector<ClimateData>();
	public HashMap<Integer, HashMap<Integer, ClimateData>> fiveYearClimateDataHistory = new HashMap<Integer, HashMap<Integer,ClimateData>>();
	
	
	public boolean containsHolidayType(int i){
		
		if(holidayTypes[i])return true;
		else return false;
		
	}
	
	public boolean checkNumberOfTourists(){
		
		return false;
	}
	
	public void updateDailyClimate(ClimateData cd){
		climateOfLast28DaysHistory.add(cd);
		if(climateOfLast28DaysHistory.size() > 28){
			climateOfLast28DaysHistory.remove(0);
		}
	}
	
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
