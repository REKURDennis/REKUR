package org.glowa.danube.components.actor.touristmodel;

import java.io.FileWriter;
import java.util.*;
import java.util.Map.Entry;

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
	 * Indicates if the district is a city 1 =city 0 = rural.
	 */
	public int cityStatus;
	/**
	 * Saves the official landkreis-id.
	 */
	public int landkreisId;
	/**
	 * Saves the number of citizens within this sourcearea.
	 */
	public int[] numberOfCitizens = new int[2];
	
	/**
	 * Saves the number of citizens difference to last year within this sourcearea.
	 */
	public int[] numberOfCitizensDiff = new int[2];
	/**
	 * saves the name of this area.
	 */
	public String name;
	/**
	 * Saves the size in m^2.
	 */
	public float size;
	/**
	 * Saves the number of different touristTypes
	 */
	public int numberOfTypes = 7;
	/**
	 * Saves the number of citizens per age and sex, starting with 0 years and ends with 90 and older.
	 */
	public int[][][] populationPerAgeAndSex = new int [91][2][numberOfTypes];
	
	
	/**
	 * Saves the number of citizens difference to the last year per age and sex, starting with 0 years and ends with 90 and older.
	 */
	public int[][][] populationPerAgeAndSexDifference = new int [91][2][numberOfTypes];
	
	public int[][][] lastYearpop = new int[91][2][numberOfTypes];
	
	/**
	 * Saves the buyingpower of this area.
	 */
	public int buyingPower[];
	
	
	/**
	 * Saves a LikedList with references to all touristactors of this area per age, sex and family status.
	 */
	public LinkedList<LinkedList<HashMap<Integer,LinkedList<DA_Tourist>>>> touristsPerAge = new LinkedList<LinkedList<HashMap<Integer,LinkedList<DA_Tourist>>>>(); 
	
	/**
	 * References the climateDataAggregator-Object to aggregate and the monthly climate.
	 */
	public ClimateDataAggregator ca = new ClimateDataAggregator(); 
	
	/**
	 * Saves the monthly climatedata of the last five years.
	 */
	public HashMap<Integer, HashMap<Integer, ClimateData>> fiveYearClimateDataHistory = new HashMap<Integer, HashMap<Integer,ClimateData>>();
	
	/**
	 * References the TouristModel main Object.
	 */
	public TouristModelMainClass tm;
	/**
	 * Saves the distance to each destination. <DestID, Distance in Minutes>
	 */
	public HashMap<Integer, Integer> distance = new HashMap<Integer, Integer>();
	
	/**
	 * Saves the loctaion 0 = Sh 1 = North Bavaria 2 = south Bavaria
	 */
	public int location = 0;
	
	@Override
	protected void options() {
		super.options();
		updateDemographyInSA();
		for(LinkedList<HashMap<Integer,LinkedList<DA_Tourist>>> aget:touristsPerAge){
			for(HashMap<Integer,LinkedList<DA_Tourist>> agesSexFT:aget){
				for(Entry<Integer, LinkedList<DA_Tourist>> ft:agesSexFT.entrySet()){
					for(DA_Tourist t:ft.getValue()){
						t.options(this.getSimulationTime().getYear(), this.getSimulationTime().getMonth(), this.getSimulationTime().getDay());
					}
				}
			}
		}
		
		ca.aggregateClimateData(getProxel(), this.getSimulationTime().getDay());
		
		// Ausgabe der taeglichen Klimadaten
		
		
		if(simulationTime().getDay() == 1){
			updateMonthlyClimate();
		}
//		if(tm.compute){
//			System.out.println("TouristCompute");
//			tm.compute = false; 
//		}
	}
	
	/**
	 * This methods updates the age of the tourists.
	 */
	private void updateDemographyInSA(){
		try{
			if(simulationTime().getDay() == 1 && simulationTime().getMonth() == 1 && simulationTime().getYear()>tm.startYear){
				
				
				touristsPerAge.addFirst(new LinkedList<HashMap<Integer,LinkedList<DA_Tourist>>>());
				
				touristsPerAge.get(0).addLast(new HashMap<Integer,LinkedList<DA_Tourist>>());
				touristsPerAge.get(0).addLast(new HashMap<Integer,LinkedList<DA_Tourist>>());
				
				for (int i = 0; i<numberOfTypes;i++){
					touristsPerAge.get(0).get(0).put(i, new LinkedList<DA_Tourist>());
					touristsPerAge.get(0).get(1).put(i, new LinkedList<DA_Tourist>());
				}
				
				
				for (int i = 0; i<numberOfTypes;i++){
					for(DA_Tourist t: touristsPerAge.getLast().get(0).get(i)){
						touristsPerAge.get(touristsPerAge.size()-2).get(0).get(i).addLast(t);
					}
				}
				for (int i = 0; i<numberOfTypes;i++){
					for(DA_Tourist t: touristsPerAge.getLast().get(1).get(i)){
						touristsPerAge.get(touristsPerAge.size()-2).get(1).get(i).addLast(t);
					}
				}
				touristsPerAge.removeLast();
				
				for(int rows=0;rows< populationPerAgeAndSexDifference.length;rows++){
					
					for(int cols = 0; cols < populationPerAgeAndSexDifference[rows].length;cols++){
						//if(landkreisId == 9577)System.out.println(simulationTime().getYear()+" "+landkreisId+" "+colnumber+" "+rownumber+" touristsPerAge alt "+touristsPerAge.get(rownumber).get(colnumber).size());

						for(int type=0;type<populationPerAgeAndSexDifference[rows][cols].length;type++){
							int i= populationPerAgeAndSexDifference[rows][cols][type];
							//System.out.println(landkreisId+" "+rows+" "+i);
							if(i<0){
								
								for(int delete = 0; delete<((i*-1));delete++){
									boolean swapped = false;
									for(int n = type+1;n<numberOfTypes;n++){
										if(populationPerAgeAndSexDifference[rows][cols][n]>0 && !swapped){
											swapped = true;
											DA_Tourist t = touristsPerAge.get(rows).get(cols).get(type).get((int)(Math.random()*(double)(touristsPerAge.get(rows).get(cols).get(type).size())));
											t.currentTouristType = tm.touristTypes.get(n);
											touristsPerAge.get(rows).get(cols).get(n).add(t);
											touristsPerAge.get(rows).get(cols).get(type).remove(t);
											t.lifephase = n;
											populationPerAgeAndSexDifference[rows][cols][n]--;
										}
									}
									if(!swapped){
										try{
											touristsPerAge.get(rows).get(cols).get(type).remove((int)(Math.random()*(double)(touristsPerAge.get(rows).get(cols).get(type).size())));
											
										}catch(Exception e){
											e.printStackTrace();
											System.out.println(simulationTime().getYear()+" "+landkreisId+" "+cols+" "+rows+" "+type);
										}
									}
									
									
								}
							}
							if(i>0){
								
								for(int add = 0; add<i;add++){
									
									if(rows == 0){
										
										
										int budget = getBudget(rows,type);
										
										
										touristsPerAge.get(rows).get(cols).get(type).addLast(new DA_Tourist(tm, this, tm.touristTypes.get(type), rows, cols, type,budget));
									}
									else{
										try{
											
											boolean swapped = false;
											for(int n = type+1;n<numberOfTypes;n++){
												if(populationPerAgeAndSexDifference[rows][cols][n]<0 && !swapped){
													swapped = true;
													DA_Tourist t = touristsPerAge.get(rows).get(cols).get(n).get((int)(Math.random()*(double)(touristsPerAge.get(rows).get(cols).get(n).size())));
													t.currentTouristType = tm.touristTypes.get(type);
													t.lifephase = type;
													touristsPerAge.get(rows).get(cols).get(type).add(t);
													touristsPerAge.get(rows).get(cols).get(n).remove(t);
													populationPerAgeAndSexDifference[rows][cols][n]++;
												}
											}
											if(!swapped){
												try{
													int x = 0;
													while(touristsPerAge.get(rows+x).get(cols).get(type).size()==0){
														x++;
													}
													DA_Tourist clone = (touristsPerAge.get(rows+x).get(cols).get(type).get((int)(Math.random()*(double)(touristsPerAge.get(rows+x).get(cols).get(type).size())))).clone();
													clone.age = rows;
													touristsPerAge.get(rows).get(cols).get(type).addLast(clone);
												}catch(Exception e){
													e.printStackTrace();
													System.out.println(simulationTime().getYear()+" "+landkreisId+" "+cols+" "+rows+" "+type);
												}
												
											}
										}catch(Exception e){
											e.printStackTrace();
											System.out.println(simulationTime().getYear()+" "+landkreisId+" "+cols+" "+rows);
										}
									}
								}
							}
						}
					}
				}
				for(LinkedList<HashMap<Integer,LinkedList<DA_Tourist>>> aget:touristsPerAge){
					for(HashMap<Integer,LinkedList<DA_Tourist>> agesSexFT:aget){
						for(Entry<Integer, LinkedList<DA_Tourist>> ft:agesSexFT.entrySet()){
							for(DA_Tourist t:ft.getValue()){
								t.age++;
								t.calcAgeCat();
								
							}
						}
					}
				}
				//Datenbankausgabe fuer Anzahl der Typen pro Quellgebiet
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		for(int x = 0; x< 91;x++){
			for(int y = 0;y<2;y++){
				for(int z = 0;z<numberOfTypes;z++){
					lastYearpop[x][y][z] = populationPerAgeAndSex[x][y][z];
				}
			}
		}
//		checkTouristInit();
		for(int age =0;age<91;age++){
			for(int sex = 0; sex<2;sex++){
				for(int type=0;type<numberOfTypes;type++){
					for(DA_Tourist t : touristsPerAge.get(age).get(sex).get(type)){
						t.age = age;
						t.calcAgeCat();
						t.lifephase=type;
						t.budget = getBudget(t.age, t.lifephase);
					}
				}
			}
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
	public void initTourists(TouristModelMainClass tm){
		this.tm = tm;
		int age = 0;
		for(int[][] popPerAge: populationPerAgeAndSex){
			touristsPerAge.addLast(new LinkedList<HashMap<Integer,LinkedList<DA_Tourist>>>());
			int sex = 0;
			for(int[] i:popPerAge){
				touristsPerAge.get(age).addLast(new HashMap<Integer,LinkedList<DA_Tourist>>());
				for(int z = 0; z<numberOfTypes;z++){
					touristsPerAge.get(age).get(sex).put(z,new LinkedList<DA_Tourist>());
				}
				int currentType = 0;
				for(int type:i){
					
					int budget = getBudget(age, currentType);
					
					for(int z=0;z<type;z++){
						touristsPerAge.get(age).get(sex).get(currentType).add(new DA_Tourist(tm,this, tm.touristTypes.get(currentType), age, sex, currentType,budget));
					}
					currentType++;
				}
				sex++;
			}
			age++;
		}
//		checkTouristInit();
	}
	
	
	/**
	 * chooses the age category
	 */
	public int calcAgeCat(int age){
		int ageCategory=0;
		if(age>=20){
			ageCategory=1;
		}
		if(age>=30){
			ageCategory=2;
		}
		if(age>=40){
			ageCategory=3;
		}
		if(age>=50){
			ageCategory=4;
		}
		if(age>=60){
			ageCategory=5;
		}
		if(age>=70){
			ageCategory=6;
		}
		return ageCategory;
	}
	
	
	private int getBudget(int age, int lp){
		
			float[] perc = tm.lpBudget.get(lp+1)[calcAgeCat(age)+1];
			float random = (float)(Math.random()*100.0);
			float add = 0.0f;
			int p = 0;
			while(add<random && p+1<perc.length){
				add+=perc[p];
				p++;
			}
//			System.out.println(add+" "+random);
		try{
			int budget = (int)tm.lpBudget.get(lp+1)[0][p];
			return budget;
		}
		catch(Exception e){
			System.out.println(add+" "+random+" "+p+" "+perc.length);
			e.printStackTrace();
		}
		return 0;
	}
	
	private void checkTouristInit(){
		FileWriter writeOut;
		String outputName = "sourceArea"+landkreisId+simulationTime().getYear()+".csv";
		try{
			writeOut = new FileWriter(outputName, false);
			writeOut.write("");
			writeOut.flush();
			writeOut = new FileWriter(outputName, true);
			for(int age =0;age<91;age++){
				writeOut.write(";"+age);
				for(int sex = 0; sex<2;sex++){
					for(int type=0;type<numberOfTypes;type++){
						int number = touristsPerAge.get(age).get(sex).get(type).size();
						for(DA_Tourist t : touristsPerAge.get(age).get(sex).get(type)){
							t.age = age;
							t.calcAgeCat();
							t.lifephase=type;
						}
						writeOut.write(";"+number);
					}
					writeOut.write(";");
				}
				writeOut.write("\n");
			}
			writeOut.flush();
			writeOut.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
