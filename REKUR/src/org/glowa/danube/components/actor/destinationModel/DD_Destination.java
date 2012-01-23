package org.glowa.danube.components.actor.destinationModel;

import java.util.HashMap;
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
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>>> touristsPerTimeSourceAndCat = new HashMap<Integer, HashMap<Integer,HashMap<Integer,HashMap<Integer, Integer>>>>();
	
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
		ca.aggregateClimateData(getProxel(), getSimulationTime().getDay());
		
	}
}
