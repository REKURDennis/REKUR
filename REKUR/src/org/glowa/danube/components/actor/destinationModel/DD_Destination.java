package org.glowa.danube.components.actor.destinationModel;

import java.util.HashMap;

import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.components.actor.utilities.ClimateDataAggregator;
import org.glowa.danube.deepactors.actors.actor.AbstractActor;

/**
 * This class provides all methods and attributes for the destination-deepactors.
 * @author Dennis Joswig
 *
 */
public class DD_Destination extends AbstractActor{
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
	//public int id;
	
	/**
	 * Holds the beCapacities.
	 * 
	 */
	public int bedCapacities;
	/**
	 * HashMap<Year<HashMap<Week, HashMap<Category, HashMap<SourceID, Number>>>();
	 */
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer[]>>>>>>> touristsPerTimeSourceAndCat = new HashMap<Integer, HashMap<Integer,HashMap<Integer,HashMap<Integer,HashMap<Integer,HashMap<Integer,HashMap<Integer,Integer[]>>>>>>>();
	
	/**
	 * Saves the monthly climatedata of the last five years.
	 */
	public HashMap<Integer, HashMap<Integer, ClimateData>> fiveYearClimateDataHistory = new HashMap<Integer, HashMap<Integer,ClimateData>>();
	
	
	/**
	 * Saves the frost days of the of the last five years for skiing areas.
	 */
	public HashMap<Integer, HashMap<Integer,Integer>> frostDaysClimateDataHistory = new HashMap<Integer, HashMap<Integer,Integer>>();
	
	/**
	 * Saves the melting days of the of the last five years for skiing areas.
	 */
	public HashMap<Integer, HashMap<Integer,Integer>> meltingDaysClimateDataHistory = new HashMap<Integer, HashMap<Integer,Integer>>();
	
	
	/**
	 * Object for climatedata aggregation.
	 */
	public ClimateDataAggregator ca= new ClimateDataAggregator();
	@Override
	protected void options(){
		
//		for(DestinationProxel dp: (Set<DestinationProxel>) getProxel()){
//			System.out.println(this.id()+" "+dp.isInside()+" "+dp.cd.airTemperatureMean);
//			
//		}
		//Szenariobedingung zur €ndeurng des Holiday Types.
		
		ca.aggregateClimateData(getProxel(), getSimulationTime().getDay());
		if(simulationTime().getDay()==2){
			updateMonthlyClimate(simulationTime().getYear(), simulationTime().getMonth(), ca.lastMonthClimate);
		}
		if(holidayTypes[3]){
			
			int year = simulationTime().getYear();
			int month = simulationTime().getMonth();
			
			if(frostDaysClimateDataHistory.containsKey(year)){
				if(!frostDaysClimateDataHistory.get(year).containsKey(month)){
					frostDaysClimateDataHistory.get(year).put(month, 0);
				}
			}
			else{
				HashMap<Integer, Integer> newYearHistory = new HashMap<Integer, Integer>();
				newYearHistory.put(month, 0);
				frostDaysClimateDataHistory.put(year,newYearHistory);
			}
			if(ca.dailyClimate.airTemperatureMin-273.16<0.0){
				int z = frostDaysClimateDataHistory.get(year).get(month);
				frostDaysClimateDataHistory.get(year).put(month,z+1);
//				System.out.println(frostDaysClimateDataHistory.get(year).get(month));
			}
			if(frostDaysClimateDataHistory.containsKey(year-5)){
				if(frostDaysClimateDataHistory.get(year-5).containsKey(month)){
					frostDaysClimateDataHistory.get(year-5).remove(month);
				}
				if(frostDaysClimateDataHistory.get(year-5).size()==0){
					frostDaysClimateDataHistory.remove(year-5);
				}
			}
			
			
			if(meltingDaysClimateDataHistory.containsKey(year)){
				if(!meltingDaysClimateDataHistory.get(year).containsKey(month)){
					meltingDaysClimateDataHistory.get(year).put(month, 0);
				}
			}
			else{
				HashMap<Integer, Integer> newYearHistory = new HashMap<Integer, Integer>();
				newYearHistory.put(month, 0);
				meltingDaysClimateDataHistory.put(year,newYearHistory);
			}
			if(ca.dailyClimate.airTemperatureMax-273.16>5.0){
				
				int z = meltingDaysClimateDataHistory.get(year).get(month);
				meltingDaysClimateDataHistory.get(year).put(month,(z+1));
//				System.out.println(meltingDaysClimateDataHistory.get(year).get(month));
			}
			if(meltingDaysClimateDataHistory.containsKey(year-5)){
				if(meltingDaysClimateDataHistory.get(year-5).containsKey(month)){
					meltingDaysClimateDataHistory.get(year-5).remove(month);
				}
				if(meltingDaysClimateDataHistory.get(year-5).size()==0){
					meltingDaysClimateDataHistory.remove(year-5);
				}
			}
		
		
		
		
			if((month>10 || month<4)&& simulationTime().getDay()==2){
				month = ((month+10)%12)+1;
				if(month==12){
					year--;
				}
				try{
//					System.out.println(id()+" "+year+" "+(month)+" melt: "+meltingDaysClimateDataHistory.get(year).get(month));
//					System.out.println(id()+" "+year+" "+(month)+" frost: "+frostDaysClimateDataHistory.get(year).get(month));
				}
				catch(Exception e){
//					e.printStackTrace();
				}
			}
			if(false){
				holidayTypes[6]=true;
			}
		}
		
		
		//Ausgabe der Taeglichen Klimadaten
		
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
