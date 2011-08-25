package org.glowa.danube.components.actor.touristmodel;

import java.util.*;
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
	 * Saves the official landkreis-id.
	 */
	public int landkreisId;
	/**
	 * Saves the number of citizens within this sourcearea.
	 */
	public int[] numberOfCitizensPerAge = new int[2];
	
	/**
	 * Saves the number of citizens difference to last year within this sourcearea.
	 */
	public int[] numberOfCitizensPerAgeDiff = new int[2];
	/**
	 * saves the name of this area.
	 */
	public String name;
	/**
	 * Saves the size in m^2.
	 */
	public float size;
	/**
	 * Saves the number of citizens per age and sex, starting with 0 years and ends with 90 and older.
	 */
	public int[][] populationPerAgeAndSex = new int [91][2];
	
	
	/**
	 * Saves the number of citizens difference to the last year per age and sex, starting with 0 years and ends with 90 and older.
	 */
	public int[][] populationPerAgeAndSexDifference = new int [91][2];
	
	/**
	 * Saves the buyingpower of this area.
	 */
	public float buyingPower;
	/**
	 * Saves a LikedList with references to all touristactors of this area per age and sex.
	 */
	public LinkedList<LinkedList<LinkedList<DA_Tourist>>> touristsPerAge = new LinkedList<LinkedList<LinkedList<DA_Tourist>>>(); 
	
	/**
	 * References the climateDataAggregator-Object to aggregate and the monthly climate.
	 */
	private ClimateDataAggregator ca = new ClimateDataAggregator(); 
	
	/**
	 * Saves the monthly climatedata of the last five years.
	 */
	public HashMap<Integer, HashMap<Integer, ClimateData>> fiveYearClimateDataHistory = new HashMap<Integer, HashMap<Integer,ClimateData>>();
	
	/**
	 * References the TouristModel main Object.
	 */
	public TouristModel tm;
	
	@Override
	protected void options() {
		super.options();
		
		for(LinkedList<LinkedList<DA_Tourist>> aget:touristsPerAge){
			for(LinkedList<DA_Tourist> agesAndSex:aget){
				for(DA_Tourist t:agesAndSex){
					t.options(this.getSimulationTime().getYear(), this.getSimulationTime().getMonth(), this.getSimulationTime().getDay());
				}
			}
		}
		
		updateTouristsAge();
		
//		for(DA_Tourist t:tourists){
//			t.makeDecision(this.getSimulationTime().getYear(), this.getSimulationTime().getMonth(), this.getSimulationTime().getDay());
//		}
		
		ca.aggregateClimateData(getProxel(), getSimulationTime().getDay());
		if(simulationTime().getDay() == 1){
			updateMonthlyClimate();
		}
	}
	
	/**
	 * This methods updates the age of the tourists.
	 */
	private void updateTouristsAge(){
		try{
			if(simulationTime().getDay() == 1 && simulationTime().getMonth() == 1 && simulationTime().getYear()>tm.startYear){
				int rownumber = 0;
				
				touristsPerAge.addFirst(new LinkedList<LinkedList<DA_Tourist>>());
				touristsPerAge.get(0).addLast(new LinkedList<DA_Tourist>());
				touristsPerAge.get(0).addLast(new LinkedList<DA_Tourist>());
				
				
				for(DA_Tourist t: touristsPerAge.getLast().get(0)){
					touristsPerAge.get(touristsPerAge.size()-2).get(0).addLast(t);
				}
				for(DA_Tourist t: touristsPerAge.getLast().get(1)){
					touristsPerAge.get(touristsPerAge.size()-2).get(1).addLast(t);
				}
				touristsPerAge.removeLast();
				
				for(int[] rows : populationPerAgeAndSexDifference){
					int colnumber = 0;
					
					for(int cols:rows){
						if(cols<0){
							for(int delete = 0; delete<((cols*-1)/10);delete++){
								if(0 == (touristsPerAge.get(rownumber).get(colnumber).size())){
									System.out.println(colnumber+" "+rownumber);
								}
								touristsPerAge.get(rownumber).get(colnumber).remove((int)(Math.random()*(double)(touristsPerAge.get(rownumber).get(colnumber).size())));
							}
						}
						if(cols>0){
							for(int add = 0; add<cols/10;add++){
								if(rownumber == 0){
									touristsPerAge.get(rownumber).get(colnumber).addLast(new DA_Tourist(tm, this, tm.touristTypes.get(1), rownumber, colnumber, 0));
								}
								else{
									DA_Tourist clone = (touristsPerAge.get(rownumber).get(colnumber).get((int)(Math.random()*(double)(touristsPerAge.get(rownumber).get(colnumber).size())))).clone();
									touristsPerAge.get(rownumber).get(colnumber).addLast(clone);
								}
							}
						}
						colnumber++;
					}
					rownumber++;
				}
				for(LinkedList<LinkedList<DA_Tourist>> aget:touristsPerAge){
					for(LinkedList<DA_Tourist> agesAndSex:aget){
						for(DA_Tourist t:agesAndSex){
							t.age++;
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
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
	
	/**
	 * This methods inits the tourists.
	 * @param tm TuristModel to refer to the tourists.
	 */
	public void initTourists(TouristModel tm){
		this.tm = tm;
		int age = 0;
		for(int[] popPerAge: populationPerAgeAndSex){
			
			touristsPerAge.addLast(new LinkedList<LinkedList<DA_Tourist>>());
			int sex = 0;
			for(int i:popPerAge){
				//tourists = new DA_Tourist[i/10];
				touristsPerAge.get(age).addLast(new LinkedList<DA_Tourist>());
				for(int number = 0; number<i/10; number++){
					touristsPerAge.get(age).get(sex).addLast(new DA_Tourist(tm,this, tm.touristTypes.get(1), age, number, 0));
				}
				sex++;
			}
			age++;
		}
	}
}
