package org.glowa.danube.components.actor.touristmodel;

import java.util.HashMap;
import java.util.Vector;
import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.components.actor.utilities.ClimateDataAggregator;
import org.glowa.danube.deepactors.actors.actor.AbstractActor;


/**
 * The class DA_SourceArea (DA = DeepActor) saves all deepactor attributes and methods.
 * @author Dennis Joswig
 *
 */
@SuppressWarnings("unchecked")
public class DA_SourceArea extends AbstractActor{
	/**
	 * Saves the number of citizens within this sourcearea.
	 */
	public float numberOfCitizens;
	/**
	 * saves the name of this area.
	 */
	public String name;
	/**
	 * Saves the size in m^2.
	 */
	public float size;
	
	public float[] demography = new float[10];
	
	public int[] sex = new int[2];
	
	/**
	 * Saves the number of citizens per age and sex, starting with 0 years and ends with 90 and older.
	 */
	public int[][] populationPerAgeAndSex = new int [92][2];
	
	/**
	 * Saves the buyingpower of this area.
	 */
	public float buyingPower;
	/**
	 * Saves an array with references to all touristactors of this area.
	 */
	public DA_Tourist[] tourists;
	
	/**
	 * References the climateDataAggregator-Object to aggregate and the monthly climate.
	 */
	private ClimateDataAggregator ca = new ClimateDataAggregator(); 
	
	/**
	 * Saves the monthly climatedata of the last five years.
	 */
	public HashMap<Integer, HashMap<Integer, ClimateData>> fiveYearClimateDataHistory = new HashMap<Integer, HashMap<Integer,ClimateData>>();
	
	@Override
	protected void query() {
		// TODO Auto-generated method stub
		super.query();
		for(DA_Tourist t : tourists){
			//t.makeDecision();
			t.makeDecision(this.getSimulationTime().getYear(), this.getSimulationTime().getMonth(), this.getSimulationTime().getDay());
		}
		ca.aggregateClimateData(getProxel(), getSimulationTime().getDay());
		if(simulationTime().getDay() == 1){
			updateMonthlyClimate();
		}
	}
	
	/**
	 * This method updates the monthly climatedata-hashmap.
	 */
	private void updateMonthlyClimate(){
		int year = simulationTime().getYear();
		int month = simulationTime().getMonth();
		ClimateData cd = ca.lastMonthClimate;
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
