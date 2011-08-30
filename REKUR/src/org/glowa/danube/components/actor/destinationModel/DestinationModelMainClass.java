 package org.glowa.danube.components.actor.destinationModel;


import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;

import org.glowa.danube.components.actor.interfaces.ModelControllerToRekurDestinationModel;
import org.glowa.danube.components.actor.interfaces.RekurDestinationModelToModelController;
import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.model.AbstractActorModel;
import org.glowa.danube.tables.FloatDataTable;
import org.glowa.danube.tables.IntegerDataTable;
import org.glowa.danube.tables.MassPerAreaTable;
import org.glowa.danube.tables.TemperatureTable;
import org.glowa.danube.utilities.execution.GetDataEngine;
import org.glowa.danube.utilities.execution.ProvideTask;

/**
 * The class <tt>DestinationModel</tt> is the mainclass of the subcomponent DestinationModel of component actor .
 * @author Dennis Joswig
 */

public class DestinationModelMainClass extends AbstractActorModel<DestinationProxel> implements RekurDestinationModelToModelController
{
	/**
	 * Saves the name for the destinationsrelation.
	 */
	private String destinationsTable;
	/**
	 * Saves the accesstring for databaseconnection.
	 */
	private String database = "jdbc:mysql://localhost/rekur?user=root&password=bla";
	/**
	 * Saves the database name.
	 */
	private String dataBaseName;
	/**
	 * Saves the username for the database connection.
	 */
	private String userName;
	/**
	 * Saves the password for the database connection.
	 */
	private String password;
	/**
	 * HashMap with the destinations-ids as key and their holiday types.
	 */
	private HashMap<Integer, boolean[]> holidayTypes = new HashMap<Integer, boolean[]>();
	/**
	 * HashMap with the destinations-ids as key and their country-ids.
	 */
	private HashMap<Integer, Integer> countryIDs = new HashMap<Integer, Integer>();
	/**
	 * HashMap with the destinations-ids as key and their daily climate data.
	 */
	private HashMap<Integer, ClimateData> dailyClimateData = new HashMap<Integer, ClimateData>();
	/**
	 * HashMap with the destinations-ids as key and their last month climate data.
	 */
	private HashMap<Integer, ClimateData> lastMonthClimateData = new HashMap<Integer, ClimateData>();
	/**
	 * References the ModelController for importdata.
	 */
	private ModelControllerToRekurDestinationModel controller;
	/**
	 * Specifies the number of holidaytypes.
	 */
	private final int holidayTypeNumber = 10;
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#init()
	 */
	protected void init() {
		System.out.println("destinationInit");
		
		dataBaseName = this.componentConfig().getComponentProperties().getProperty("dataBaseName");
	    userName = this.componentConfig().getComponentProperties().getProperty("userName");
	    password = this.componentConfig().getComponentProperties().getProperty("password");
	    destinationsTable = this.componentConfig().getComponentProperties().getProperty("destinationrelation");
	    database = "jdbc:mysql://localhost/"+dataBaseName+"?user="+userName+"&password="+password;
		
		
		initDestinationsFromDataBase();
		for(Actor entry :actorMap().getEntries()){
			DD_Destination dest = (DD_Destination)entry;
			holidayTypes.put(entry.getId(), dest.holidayTypes);
			countryIDs.put(entry.getId(), dest.country);
		}
		writemap();
	}
	
	/**
	 * Inits all data saved in the database.
	 */
	
	private void initDestinationsFromDataBase(){
		try {
            // Der Aufruf von newInstance() ist ein Workaround
	        // für einige misslungene Java-Implementierungen
		
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(database);
			Statement stmt = con.createStatement();
			
			String query = " select * from "+destinationsTable+";";
			
			ResultSet sa = stmt. executeQuery(query);
			int i = 1;
			while(sa.next()&&i<=actorMap().size()){
//				System.out.println(sa.getString(1)+sa.getString(2)+sa.getString(3)+sa.getString(4));
				DD_Destination currentActor =(DD_Destination)(actorMap().getEntry(Integer.parseInt(sa.getString("RekurID"))));
				currentActor.holidayTypes = new boolean[holidayTypeNumber];
				currentActor.country = Integer.parseInt(sa.getString("LandID"));
				for(int z = 0;z<holidayTypeNumber;z++){
					String entry = sa.getString(z+5);
					if(entry.equals("0")){
						currentActor.holidayTypes[z] = false;
					}
					if(entry.equals("1")){
						currentActor.holidayTypes[z] = true;
					}
				}
				i++;
//				System.out.println(currentActor.getId());
			}	
		} catch (Exception ex) {
            // Fehler behandeln
			ex.printStackTrace();
		}
	}
	
	/**
	 * writes out the destinationmap.
	 */
	private void writemap(){
		FileWriter writeOut;
		String outputName = "destinations.asc";
		try{
			writeOut = new FileWriter(outputName, false);
			writeOut.write("");
			writeOut.flush();
			writeOut = new FileWriter(outputName, true);
			
			
			writeOut.write("ncols 	590\n" +
					"nrows 		258\n" +
					"xllcorner     -2868800\n" +
					"yllcorner     200792\n" +
					"cellsize      10000\n" +
					"NODATA_value  -9999\n");
			
			try{
				for(int i=1;i<(590*258);i++){
					
					boolean inside = proxel(i).isInside();
					if(inside){
						int found = -1;
						for(Actor entry :actorMap().getEntries()){
							for(int id : entry.getLocation().getPIDArray()){
								
								if(id == i){
									found = entry.getId();
								}
							}
						}
						if(found!=-1){
							writeOut.write(" "+found);
						}
						else{
							writeOut.write(" "+0);
						}
					}
					else{
						writeOut.write(" "+"-9999");
					}
					if(i%590==0){
						writeOut.write("\n");
					}
				}
			}
			catch(Exception e){
				writeOut.write(e.getMessage());
			}	
			
			writeOut.flush();
			writeOut.close();
		}
		catch(Exception e){System.out.println(e);}
	}
	
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#getData()
	 */
	public void getData() {
		try {
			controller = (ModelControllerToRekurDestinationModel) getImport("org.glowa.danube.components.actor.interfaces.ModelControllerToRekurDestinationModel");
			//System.out.println(data.getNumberOfTourists());
			getDaylyDataEngine.getData();
		} catch (Exception ex) {
			this.logger().warn(ex);
		}
	}
	
	
	/**
	 * getData engine parallelizes the import of the ClimateData provided by the ClimateModel via ModelController. 
	 */
	private GetDataEngine getDaylyDataEngine = new GetDataEngine();
    {
		
    	
//    	MeanTemp
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			TemperatureTable airTemperatureDailyMean = controller.getAirTemperatureDailyMean();
    			for( int i=0; i<pids().length; i++ ){ 
					proxel(i).cd.airTemperatureMean = airTemperatureDailyMean.getValueByIndex(i);
				}
    		}
    	});
//    	MaxTemp
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			TemperatureTable airTemperatureDailyMax= controller.getAirTemperatureDailyMax();
    			for( int i=0; i<pids().length; i++ ){ 
					proxel(i).cd.airTemperatureMax = airTemperatureDailyMax.getValueByIndex(i);
				}
    		}
    	});
//    	MinTemp
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			TemperatureTable airTemperatureDailyMin = controller.getAirTemperatureDailyMin();
    			for( int i=0; i<pids().length; i++ ){ 
					proxel(i).cd.airTemperatureMin = airTemperatureDailyMin.getValueByIndex(i);
				}
    		}
    	});
//    	PrecepSum
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			MassPerAreaTable precipitationDailySum = controller.getPrecipitationDailySum();
    			for( int i=0; i<pids().length; i++ ){ 
    				proxel(i).cd.precipitationSum = precipitationDailySum.getValueByIndex(i);
				}
    		}
    	});
//    	SunDurance
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			FloatDataTable sunshineDurationDailySum = controller.getSunshineDurationDailySum();
    			for( int i=0; i<pids().length; i++ ){ 
    				proxel(i).cd.sunshineDurationSum = sunshineDurationDailySum.getValueByIndex(i);
				}
    		}
    	});
//    	MeanWindSpeed
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			FloatDataTable windSpeedDailyMean = controller.getWindSpeedDailyMean();
    			for( int i=0; i<pids().length; i++ ){ 
					proxel(i).cd.windSpeedMean = windSpeedDailyMean.getValueByIndex(i);
				}
    		}
    	});
//    	MaxWindSpeed
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			FloatDataTable windSpeedDailyMax = controller.getWindSpeedDailyMax();
    			for( int i=0; i<pids().length; i++ ){ 
					proxel(i).cd.windSpeedMax = windSpeedDailyMax.getValueByIndex(i);
				}
    		}
    	});
//    	RelativeHumidity
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			FloatDataTable relativeHuminityDailyMean = controller.getRelativeHumidityDailyMean();
    			for( int i=0; i<pids().length; i++ ){ 
    				proxel(i).cd.relativeHumidityMean = relativeHuminityDailyMean.getValueByIndex(i);
				}
    		}
    	});
    	
//    	TemperatureHumidityIndex
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			IntegerDataTable temperatureHumidityIndex = controller.getTempHumidityIndex();
    			for( int i=0; i<pids().length; i++ ){ 
    				proxel(i).cd.temperatureHumidityIndex = temperatureHumidityIndex.getValueByIndex(i);
				}
    		}
    	});
    	
    	
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			if(controller.getNumberOfTourists() !=null){
    				for(Entry<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>>>> dests:controller.getNumberOfTourists().entrySet()){
    					DD_Destination dest =(DD_Destination)(actorMap().getEntry(dests.getKey()));
    					dest.touristsPerTimeSourceAndCat = dests.getValue();
    				}	
    			}
    		}
    	});
    }
	
	
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#preCompute()
	 */
	protected void preCompute() {
		
		
	}
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#postCompute()
	 */
	protected void postCompute(){
	}
	
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#commit()
	 */
	public void commit()
	{
		dailyClimateData = new HashMap<Integer, ClimateData>();
		lastMonthClimateData = new HashMap<Integer, ClimateData>();
		for(Actor a : actorMap().getEntries()){
			DD_Destination d = (DD_Destination)a;
			dailyClimateData.put(d.getId(), d.ca.dailyClimate);
			if(simulationTime().getDay()==1){
				lastMonthClimateData.put(d.getId(), d.ca.lastMonthClimate);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#store()
	 */
	protected void store()
	{
//		DanubiaCalendar writeOutTime = simulationTime();
	}//Store

	/**
	 * Inits the gmt: Societal scenario and Actions will be initialized.
	 */
	protected void initGMT()
	{
		// Gesellschaftliche Megatrends
		//int gmt;
	    //try
	    //gmt = Integer.parseInt(this.componentConfig().getComponentProperties().getProperty("gmt"));
	}

	@Override
	public HashMap<Integer, boolean[]> getHolidayTypes() {
		// TODO Auto-generated method stub
		return holidayTypes;
	}

	@Override
	public HashMap<Integer, Integer> getCountryIDs() {
		// TODO Auto-generated method stub
		return countryIDs;
	}


	@Override
	public HashMap<Integer, ClimateData> getDailyClimateData() {
		// TODO Auto-generated method stub
		return dailyClimateData;
	}


	@Override
	public HashMap<Integer, ClimateData> getLastMonthClimateData() {
		// TODO Auto-generated method stub
		return lastMonthClimateData;
	}

	@Override
	public HashMap<Integer, int[][][]> getBedCapacities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer, int[][][]> getCostsPerCategory() {
		// TODO Auto-generated method stub
		return null;
	}
	  
}





