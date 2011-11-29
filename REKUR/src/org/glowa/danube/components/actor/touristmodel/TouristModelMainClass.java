package org.glowa.danube.components.actor.touristmodel;


import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

import org.glowa.danube.components.actor.interfaces.ModelControllerToRekurTouristModel;
import org.glowa.danube.components.actor.interfaces.RekurTouristModelToModelController;
import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.model.AbstractActorModel;
import org.glowa.danube.tables.FloatDataTable;
import org.glowa.danube.tables.IntegerDataTable;
import org.glowa.danube.tables.MassPerAreaTable;
import org.glowa.danube.tables.TemperatureTable;
import org.glowa.danube.utilities.execution.GetDataEngine;
import org.glowa.danube.utilities.execution.ProvideTask;
import org.glowa.danube.utilities.internal.DanubiaLogger;

/**
 * The class <tt>TouristModel</tt> is the mainclass of the subcomponent Tourist Model of component deepactor .
 * 
 * Configurationfile can be found unter metadata/components/touristmodel.cfg
 * 
 * @author Dennis Joswig
 */



public class TouristModelMainClass extends AbstractActorModel<TouristProxel> implements RekurTouristModelToModelController
{
	/**
	 * Saves the scenario used in this run.
	 */
	public int touristscenario; 
	
	static final long serialVersionUID = 1;
	/**
	 * holds the URL for the Databaseconnection generated by the attributes given in the config-file.
	 * Configurationfile can be found unter metadata/components/touristmodel.cfg
	 */
	private String database = "jdbc:mysql://localhost/rekur?user=root&password=bla";
	/**
	 * holds the database name given in the configfile.
	 * Configurationfile can be found unter metadata/components/touristmodel.cfg
	 */
	private String dataBaseName;
	/**
	 * holds the user nme for the the database connection. Can be set up in the config-file.
	 * Configurationfile can be found unter metadata/components/touristmodel.cfg
	 */
	private String userName;
	/**
	 * holds the database password given in the config-file.
	 * Configurationfile can be found unter metadata/components/touristmodel.cfg
	 */
	private String password;
	/**
	 * holds the name of the ralation containing the sourcearea attributes. Given in the config-file.
	 * Configurationfile can be found unter metadata/components/touristmodel.cfg
	 */
	private String sourceareaTable = "sourceareaoverview";
	/**
	 * holds the name of the ralation containing the sourcearea ids. Given in the config-file.
	 *  Configurationfile can be found unter metadata/components/touristmodel.cfg
	 */
	private String landkreisIDtoSourceAreaIDTable = "landkreise";
	/**
	 * holds the name of the ralation containing the touristTypes. Given in the config-file.
	 *  Configurationfile can be found unter metadata/components/touristmodel.cfg
	 */
	private String touristTypesTable = "touristTypes";
	/**
	 * HashMap with estination-Ids and thir DATA-Objects.
	 */
	public HashMap<Integer,DATA_Destination> destinations = new HashMap<Integer, DATA_Destination>();
	/**
	 * This HashMap contains all touristTypes.
	 */
  	public HashMap<Integer, DA_AbstractTouristType> touristTypes = new HashMap<Integer, DA_AbstractTouristType>();
  	/**
  	 * Reference to the Importcontroller. 
  	 */
  	public ModelControllerToRekurTouristModel controller; 
  	/**
   	* Checks the initialization. 
   	*/
  	private boolean destinationInit = true;  
  	/**
   	* Saves the number of price categories.
   	*/
  	public int priceCategories = 7;
  	/**
   	* Saves the simulation start year.
   	*/
  	public int startYear;
  	/**
   	* Saves the simulation start month.
   	*/
  	public int startMonth;
  	/**
   	* Saves the simulation start day.
   	*/
  	public int startDay;
  	/**
   	* Saves the last week that has been written into the output database.
   	*/
  	private int printedWeek = 0;
  	/**
   	* Saves the current simulationdate as GregorianCalendar-Object to get the weekOfYear.
   	*/
  	public GregorianCalendar currentDate;
  	/**
   	* Number of years of the pre simulation time.
   	*/
  	public int preSimulationTime;
  	/**
   	* Indicates if the presimulation is finished.
   	*/
  	public boolean preSimulation = true;
  	/**
   	* logging.
   	*/
  	private boolean logging = true;
  	/**
   	* reference to the danubialogger.
   	*/
  	private static DanubiaLogger logger = DanubiaLogger.getDanubiaLogger(TouristModelMainClass.class);
  	/**
   	* Saves the number of tourists to export to the DestiantionModel-Object.  HashMap<DestinationID, HashMap<year, HashMap<week, HashMap<category, HashMap<sourceareaID, quantity>>>>>
   	*/
  	private HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>>>> touristPerDest = new HashMap<Integer, HashMap<Integer,HashMap<Integer,HashMap<Integer,HashMap<Integer,Integer>>>>>();
 
  	/**
   	* Sets if a touristHas booked;
   	* 
   	*/
  	private boolean booked = false;
  
  	/**
   	* Saves the distances between all SourceAreas and Destinations HashMap<SourceId, HashMap<DestID, HashMap<Vehicle, Distance>>>.
   	*/
  	public HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> distanceMatrix;
  	/**
   	* Statement-Object for the database connection.
   	*/
  	private Statement stmt = null;
  
  	/**
   	* Holds the relation names for the tourists per destinations output relations.
   	*/
  	private String touristsPerDestinationTables;
  
  	/**
   	* Saves if the redecide process needs to be executed.
   	*/
  	private boolean redecide = true;
  	public boolean compute = true;
  
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#init()
	 */
	protected void init() {
		
		System.out.println("TouristInit");
		startYear = simulationTime().getYear();
		startMonth = simulationTime().getMonth();
		startDay = simulationTime().getDay();
		updateCurrentDate();
		preSimulationTime = Integer.parseInt(this.componentConfig().getComponentProperties().getProperty("preSimulationTime"));
		dataBaseName = this.componentConfig().getComponentProperties().getProperty("dataBaseName");
	    userName = this.componentConfig().getComponentProperties().getProperty("userName");
	    password = this.componentConfig().getComponentProperties().getProperty("password");
	    database = "jdbc:mysql://localhost/"+dataBaseName+"?user="+userName+"&password="+password;
	    sourceareaTable = this.componentConfig().getComponentProperties().getProperty("sourceareaTable");
	    //demoTable = this.componentConfig().getComponentProperties().getProperty("demoTable");
	    landkreisIDtoSourceAreaIDTable = this.componentConfig().getComponentProperties().getProperty("landkreisIDtoSourceAreaIDTable");
	    touristTypesTable = this.componentConfig().getComponentProperties().getProperty("touristTypesTable");
	    touristsPerDestinationTables = this.componentConfig().getComponentProperties().getProperty("touristsPerDestinationTables");
	    
		initSourceAreasFromDataBase();
		updateDemography(startYear);
		initTourists();
		writemap();
		
		try {
			controller = (ModelControllerToRekurTouristModel) getImport("org.glowa.danube.components.actor.interfaces.ModelControllerToRekurTouristModel");
		} catch (Exception ex) {
			this.logger().warn(ex);
		}
	}
	
	/**
	 * This Method inits the distance matrix from the database.
	 */
	private void initDistanceMatrix(){
		
	}
	
	/**
	 * Reads the additional Attributes of the sourceAreas from the database
	 */
	private void initSourceAreasFromDataBase(){
		try {
            // Der Aufruf von newInstance() ist ein Workaround
	        // f�r einige misslungene Java-Implementierungen
		
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(database);
			Statement stmt = con.createStatement();
			
			String query = " select * from "+sourceareaTable+
					" natural join "+landkreisIDtoSourceAreaIDTable+";";
			
			ResultSet sa = stmt. executeQuery(query);
			int i = 1;
			while(sa.next()&&i<=actorMap().size()){
//				System.out.println(sa.getString(1)+sa.getString(2)+sa.getString(3)+sa.getString(4)+sa.getString(5));
				DA_SourceArea currentActor =(DA_SourceArea)(actorMap().getEntry(Integer.parseInt(sa.getString("RekurID"))));
				currentActor.name = sa.getString(2);
				currentActor.landkreisId = Integer.parseInt(sa.getString("ID"));
				try{
					currentActor.size = Float.parseFloat(sa.getString(3));
				}
				catch(Exception e){
					currentActor.size = 0.0f;
					e.printStackTrace();
				}
				try{
					if(!sa.getString(4).equals("-"))currentActor.buyingPower = Float.parseFloat(sa.getString(4));
				}	
				catch(Exception e){
					currentActor.buyingPower = 0.0f;
					e.printStackTrace();
				}
				i++;
				//System.out.println(sa.getString("RekurID"));
			}	
		} catch (Exception ex) {
            // Fehler behandeln
			ex.printStackTrace();
		}
	}
	/**
	 * Updates the Demography for this year
	 * @param year current simulation year
	 */
	
	public void updateDemography(int year){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(database);
			Statement stmt = con.createStatement();
			for(Actor a:actorMap().getEntries()){
				DA_SourceArea currentArea = (DA_SourceArea)a;
				String query = " select m"+year+",w"+year+" from d"+currentArea.landkreisId;
				
				ResultSet sa = stmt. executeQuery(query);
				int i = 0;
				
				while(sa.next()){
					//System.out.println(i);
					if(sa.getString("m"+year).equals("") || sa.getString("w"+year).equals("")){
//						System.out.println(currentArea.landkreisId+" "+i);
					}
					else{
						int m = Integer.parseInt(sa.getString("m"+year));
						int w = Integer.parseInt(sa.getString("w"+year));
						if(i == 0){
							currentArea.populationPerAgeAndSexDifference[i][0] = m;
							currentArea.populationPerAgeAndSexDifference[i][1] = w;
						}
						if(i>1 && i < 90){	
							currentArea.populationPerAgeAndSexDifference[i][0] = m - currentArea.populationPerAgeAndSex[i-1][0];
							currentArea.populationPerAgeAndSexDifference[i][1] = w - currentArea.populationPerAgeAndSex[i-1][1];
						}
						if(i == 90){
							currentArea.populationPerAgeAndSexDifference[i][0] = m - (currentArea.populationPerAgeAndSex[i-1][0]+currentArea.populationPerAgeAndSex[i][0]);
							currentArea.populationPerAgeAndSexDifference[i][1] = w - (currentArea.populationPerAgeAndSex[i-1][1]+currentArea.populationPerAgeAndSex[i][1]);	
						}
						if(i==91){	
							currentArea.numberOfCitizensPerAgeDiff[0] = m - currentArea.numberOfCitizensPerAge[0];
							currentArea.numberOfCitizensPerAgeDiff[1] = w - currentArea.numberOfCitizensPerAge[1];
							currentArea.numberOfCitizensPerAge[0]= m;
							currentArea.numberOfCitizensPerAge[1]= w;
						}
						else{
							currentArea.populationPerAgeAndSex[i][0] = m;
							currentArea.populationPerAgeAndSex[i][1] = w;
						}
					}
					i++;
				}
				
			}
		} catch (Exception ex) {
	        // Fehler behandeln
			ex.printStackTrace();
			System.out.println("Error");
		}
	}
	
	/**
	 * This Method initializes all Tourist Agents.
	 */
	private void initTourists(){
		initTouristTypes();
		for(DA_SourceArea sa : actorMap().getEntries(DA_SourceArea.class).getEntries()){
			sa.initTourists(this);
		}
	}
	
	
	/**
	 * This methods reads in all TouristTypes from the database.
	 */
	private void initTouristTypes(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(database);
			Statement stmt = con.createStatement();
			String query = "select * from "+touristTypesTable;
			
			ResultSet sa = stmt. executeQuery(query);
			while(sa.next()){
				DA_AbstractTouristType ttype = (DA_AbstractTouristType)Class.forName(sa.getString("Class")).newInstance();
				try{
					ttype.holidaytypes = addToVector(sa.getString("holidaytypes"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.activityTypes = addToVector(sa.getString("activityTypes"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.countries = addToVector(sa.getString("countries"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.tcp.airTemperatureMax = Integer.parseInt(sa.getString("maxTemp"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.tcp.airTemperatureMin = Integer.parseInt(sa.getString("minTemp"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.tcp.airTemperatureMean = Integer.parseInt(sa.getString("avgTemp"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.preferedJourneyWeeks = addToVector(sa.getString("preferedWeeks"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.holidayLength = Integer.parseInt(sa.getString("holidayLength"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.category = Integer.parseInt(sa.getString("category"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.bookingMonth = Integer.parseInt(sa.getString("bookingMonth"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				try{
					ttype.bookingDay = Integer.parseInt(sa.getString("bookingDay"));
				}
				catch(Exception e){
					logger().debug(e.getMessage());
				}
				touristTypes.put(Integer.parseInt(sa.getString("ID")), ttype);
			}	
		} catch (Exception ex) {
	        // Fehler behandeln
			ex.printStackTrace();
			System.out.println("Error");
		}
	}
	
	/**
	 * Adds int values out of a string seperated by ",".
	 * @param ints String with int-values
	 * @return a Vector<Integer> with all values.
	 */
	private Vector<Integer> addToVector(String ints){
		String[] values = ints.split(",", -1);
		Vector<Integer> intVector = new Vector<Integer>();
		for(String intValue:values){
			intVector.add(Integer.parseInt(intValue));
		}
		return intVector;
	}
	
	/**
	 * Writes out the sourcearea map to check the correct initialization
	 */
	private void writemap(){
		FileWriter writeOut;
		String outputName = "sourcearea.asc";
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
					"NODATA_value  1\n");
			
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
						writeOut.write(" "+"1");
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
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * This Method is called to put a Tourist in a destination.
	 * @param tourist The traveling tourist.
	 */
	public void setDestinationChanged(DA_Tourist tourist){
		booked = true;
		DATA_Destination journeyDest = destinations.get(tourist.nextJourney.destID);
		for(Entry<Integer, Vector<Integer>> weeksOfYear: tourist.nextJourney.weeks.entrySet()){
			for(int i : weeksOfYear.getValue()){
				HashMap<Integer, HashMap<Integer, Vector<Journey>>> journeyPerWeekAndCat;
				if(journeyDest.bookingJourneys.containsKey(weeksOfYear.getKey())){
					journeyPerWeekAndCat = journeyDest.bookingJourneys.get(weeksOfYear.getKey());
				}
				else{
					journeyPerWeekAndCat = new HashMap<Integer, HashMap<Integer,Vector<Journey>>>();
				}
				
				HashMap<Integer, Vector<Journey>> journeyPerCat;
				if(journeyPerWeekAndCat.containsKey(i)){
					journeyPerCat = journeyPerWeekAndCat.get(i);
				}
				else{
					journeyPerCat = new HashMap<Integer, Vector<Journey>>();
				}
				if(journeyPerCat.containsKey(tourist.nextJourney.category)){
					Vector<Journey> journeys = journeyPerCat.get(tourist.nextJourney.category);
					journeys.add(tourist.nextJourney);
					journeyPerCat.put(tourist.nextJourney.category, journeys);
				}
				else{
					Vector<Journey> journeys = new Vector<Journey>();
					journeys.add(tourist.nextJourney);
					journeyPerCat.put(tourist.nextJourney.category, journeys);
				}
				//System.out.println(tourist.nextJourney.SourceAreaID);
				journeyPerWeekAndCat.put(i, journeyPerCat);
				journeyDest.bookingJourneys.put(weeksOfYear.getKey(), journeyPerWeekAndCat);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#getData()
	 */
	public void getData() {
		System.out.println("TouristGetData");
		compute = true;
		if(destinationInit){
			int i = 0;
			for(Entry<Integer, boolean[]> entry:controller.getHolidayTypes().entrySet()){
				DATA_Destination currentDest = new DATA_Destination();
				currentDest = new DATA_Destination();
				if(entry.getValue()==null){
					System.out.println(entry.getKey()+" Entry.getValue() ist null");
				}
				currentDest.holidayTypes = entry.getValue();
				currentDest.id = (int)entry.getKey();
				currentDest.country = controller.getCountryIDs().get(entry.getKey());
				destinations.put(entry.getKey(), currentDest);
				i++;
				//System.out.println(text);
			}
			destinationInit = false;
		}
		for(Entry<Integer, ClimateData> dailyClimate: controller.getDailyClimateData().entrySet()){
			destinations.get(dailyClimate.getKey()).updateDailyClimate(dailyClimate.getValue());
			
			if(simulationTime().getDay()==2){
				destinations.get(dailyClimate.getKey()).updateMonthlyClimate(simulationTime().getYear(), simulationTime().getMonth(), controller.getLastMonthClimateData().get(dailyClimate.getKey()));
			}
		}
		//System.out.println(preSimulation);
		if(preSimulation && startDay == simulationTime().getDay() && startMonth == simulationTime().getMonth() && 
			startYear+preSimulationTime == simulationTime().getYear()){	
					preSimulation = false;
		}
		
		try {
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
    			for(int i:pids()){ 
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
    			for(int i:pids()){ 
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
    			for(int i:pids()){
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
    			for(int i:pids()){
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
    			for(int i:pids()){
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
    			for(int i:pids()){
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
    			for(int i:pids()){
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
    			for(int i:pids()){
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
    			for(int i:pids()){
    				proxel(i).cd.temperatureHumidityIndex = temperatureHumidityIndex.getValueByIndex(i);
				}
    		}
    	});
    	
    	getDaylyDataEngine.add(new ProvideTask()
    	{
    		public void run()
    		{
    			if(simulationTime().getMonth() == 1 && simulationTime().getDay() == 1){
    				 //System.out.println(""+ day+" "+month+" "+year);
    				 updateDemography(simulationTime().getYear());
    			 }
    		}
    	});
    }
	
	
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#preCompute()
	 */
	protected void preCompute() {
		updateCurrentDate();
		booked = false;
	}
	/**
	 * This methods updates the current Gregorian Calendar.
	 */
	private void updateCurrentDate(){
		int month = this.simulationTime().getMonth();
		 int year = this.simulationTime().getYear();
		 int day = this.simulationTime().getDay();
		 
		 currentDate = new GregorianCalendar(year, month-1, day-1);
		 currentDate.setMinimalDaysInFirstWeek(4);
		 currentDate.setFirstDayOfWeek(1);
	}
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#postCompute()
	 */
	protected void postCompute(){
		if(booked){
			while(redecide){
				redecide = false;
				redecideProcess();
			}
			journeyCounter();
		}
		
	}
	/**
	 * This method counts the current booking journeys per year, week, category and sourcearea.
	 */
	private void journeyCounter(){
		if(destinations !=null && booked){
			for(Entry<Integer, DATA_Destination> dests:destinations.entrySet()){
				for(Entry<Integer, HashMap<Integer, HashMap<Integer, Vector<Journey>>>> jPerYWC:dests.getValue().bookingJourneys.entrySet()){
					if(!dests.getValue().touristsPerTimeSourceAndCat.containsKey(jPerYWC.getKey())){
						HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> touristsPerCatSourceWeek = new HashMap<Integer, HashMap<Integer,HashMap<Integer,Integer>>>();
						dests.getValue().touristsPerTimeSourceAndCat.put(jPerYWC.getKey(), touristsPerCatSourceWeek);
					}
					for(Entry<Integer, HashMap<Integer, Vector<Journey>>> jPerWC: jPerYWC.getValue().entrySet()){
						if(!dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).containsKey(jPerWC.getKey())){
							HashMap<Integer, HashMap<Integer, Integer>> touristsPerCatSource = new HashMap<Integer, HashMap<Integer,Integer>>();
							dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).put(jPerWC.getKey(), touristsPerCatSource);
						}
						
						for(Entry<Integer, Vector<Journey>> jPerC:jPerWC.getValue().entrySet()){
							if(!dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).get(jPerWC.getKey()).containsKey(jPerC.getKey())){
								HashMap<Integer, Integer> touristsPerCat = new HashMap<Integer, Integer>();
								dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).get(jPerWC.getKey()).put(jPerC.getKey(), touristsPerCat);
							}
							for(Journey j:jPerC.getValue()){
								if(!dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).get(jPerWC.getKey()).get(jPerC.getKey()).containsKey(j.sourceAreaID)){
									dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).get(jPerWC.getKey()).get(jPerC.getKey()).put(j.sourceAreaID,1);
									//dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).get(jPerWC.getKey()).put(jPerC.getKey(), touristsPerCat);
								}
								else{
									int x = dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).get(jPerWC.getKey()).get(jPerC.getKey()).get(j.sourceAreaID);
									x++;
									dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).get(jPerWC.getKey()).get(jPerC.getKey()).put(j.sourceAreaID,x);
								}
							}
						}
					}
				}
				dests.getValue().bookingJourneys = new HashMap<Integer, HashMap<Integer,HashMap<Integer,Vector<Journey>>>>();
			}
		}
	}
	
	/**
	 * This methods checks the current bookings for crowded destinations and causes redicisions.
	 */
	private void redecideProcess(){
		if(destinations !=null && booked){
			for(Entry<Integer, DATA_Destination> dests:destinations.entrySet()){
				if(dests.getValue().bedCapacities !=null){
					for(Entry<Integer, HashMap<Integer, HashMap<Integer, Vector<Journey>>>> jPerYWC:dests.getValue().bookingJourneys.entrySet()){
						for(Entry<Integer, HashMap<Integer, Vector<Journey>>> jPerWC: jPerYWC.getValue().entrySet()){
							for(Entry<Integer, Vector<Journey>> jPerC:jPerWC.getValue().entrySet()){
								boolean notIn = false;
								if(dests.getValue().touristsPerTimeSourceAndCat.containsKey(jPerYWC.getKey())){
									if(dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).containsKey(jPerWC.getKey())){
										if(dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).get(jPerWC.getKey()).containsKey(jPerC.getKey())){
											int quantity = 0;
											for(Entry<Integer, Integer> i:dests.getValue().touristsPerTimeSourceAndCat.get(jPerYWC.getKey()).get(jPerWC.getKey()).get(jPerC.getKey()).entrySet()){
												quantity += i.getValue();
											}
											int tooMany = (jPerC.getValue().size() + quantity) -dests.getValue().bedCapacities[jPerWC.getKey()][jPerC.getKey()];
											
											if(tooMany>0){
												redecide(jPerC.getValue(), tooMany);
												tooMany = 0;
											}
											
											HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> freeBedsPerYWC = new HashMap<Integer, HashMap<Integer,HashMap<Integer,Integer>>>();
											HashMap<Integer, HashMap<Integer, Integer>> freeBedsPerWC = new HashMap<Integer, HashMap<Integer,Integer>>();
											HashMap<Integer, Integer> freebedsPerC = new HashMap<Integer, Integer>();
											freebedsPerC.put(jPerC.getKey(),tooMany*-1);
											freeBedsPerWC.put(jPerWC.getKey(), freebedsPerC);
											freeBedsPerYWC.put(jPerYWC.getKey(), freeBedsPerWC);
											dests.getValue().freeBeds = freeBedsPerYWC;
										}
										else{
											notIn = true;
										}
									}
									else{
										notIn = true;
									}
								}
								else{
									notIn = true;
								}
								if(notIn){
									int tooMany = (jPerC.getValue().size())-dests.getValue().bedCapacities[jPerWC.getKey()][jPerC.getKey()];
									if(tooMany>0){
										redecide(jPerC.getValue(), tooMany);
										tooMany = 0;
									}
									HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> freeBedsPerYWC = new HashMap<Integer, HashMap<Integer,HashMap<Integer,Integer>>>();
									HashMap<Integer, HashMap<Integer, Integer>> freeBedsPerWC = new HashMap<Integer, HashMap<Integer,Integer>>();
									HashMap<Integer, Integer> freebedsPerC = new HashMap<Integer, Integer>();
									freebedsPerC.put(jPerC.getKey(),tooMany*-1);
									freeBedsPerWC.put(jPerWC.getKey(), freebedsPerC);
									freeBedsPerYWC.put(jPerYWC.getKey(), freeBedsPerWC);
									dests.getValue().freeBeds = freeBedsPerYWC;
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Lets a given number of tourists redecide their journeys.
	 * @param journeys Vector with all current booking jounerys.
	 * @param tooMany Number of redecisions.
	 */
	private void redecide(Vector<Journey> journeys, int tooMany){
		for(int i=0; i<tooMany ; i++){
			redecide = true;
			int random = (int)(Math.random()*(double)journeys.size());
			Journey j = journeys.get(random);
			journeys.remove(random);
			j.tourist.nextJourney = null;
			j.tourist.makeDecision(simulationTime().getYear(), simulationTime().getMonth(), simulationTime().getDay());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.model.AbstractActorModel#commit()
	 */
	public void commit()
	{
		System.out.println("TouristProvide");
		if(destinations !=null && printedWeek!=currentDate.get(GregorianCalendar.WEEK_OF_YEAR)){
			printedWeek=currentDate.get(GregorianCalendar.WEEK_OF_YEAR);
			
			try{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection con = DriverManager.getConnection(database);
				Statement stmt = con.createStatement();
				try{
					stmt.executeUpdate("drop table "+touristsPerDestinationTables+simulationTime().getYear()+currentDate.get(GregorianCalendar.WEEK_OF_YEAR));
				}catch(Exception e){}
				String query="Create table "+touristsPerDestinationTables+simulationTime().getYear()+currentDate.get(GregorianCalendar.WEEK_OF_YEAR)+" (DestID varchar(255), Category varchar(255), SourceID varchar(255), quantity int(200))";
				stmt.executeUpdate(query);
			}
			catch(Exception e){}
			for(Entry<Integer, DATA_Destination> dests:destinations.entrySet()){
				try{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection con = DriverManager.getConnection(database);
					Statement stmt = con.createStatement();
					HashMap<Integer, HashMap<Integer, Integer>> touristsPerCatAndSourceHashMap = dests.getValue().touristsPerTimeSourceAndCat.get(currentDate.get(GregorianCalendar.YEAR)).get(currentDate.get(GregorianCalendar.WEEK_OF_YEAR));
					try{	
						for(Entry<Integer, HashMap<Integer, Integer>> touristsPerCatAndSource:touristsPerCatAndSourceHashMap.entrySet()){
							try{
								for(Entry<Integer, Integer> touristsPerSource : touristsPerCatAndSource.getValue().entrySet()){
									
									//System.out.println("Year: "+currentDate.get(GregorianCalendar.YEAR) +" Week: "+currentDate.get(GregorianCalendar.WEEK_OF_YEAR)+" Destination: "+dests.getKey()+" in category: "+touristsPerCatAndSource.getKey()+" from SourceArea: "+touristsPerSource.getKey()+" Quantity: "+touristsPerSource.getValue());
									String query ="insert into "+touristsPerDestinationTables+simulationTime().getYear()+currentDate.get(GregorianCalendar.WEEK_OF_YEAR)+" values('"+dests.getKey()+"','"+touristsPerCatAndSource.getKey()+"','"+touristsPerSource.getKey()+"',"+touristsPerSource.getValue()+")";
									System.out.println(query);
									stmt.executeUpdate(query);
								}
							}
							catch(Exception e ){
								e.printStackTrace();
							}
						}
					}catch(Exception e ){
						
					}
				}catch(Exception e){
					
				}
			}	
		}
		
		
		if(booked && destinations !=null){
			touristPerDest = new HashMap<Integer, HashMap<Integer,HashMap<Integer,HashMap<Integer,HashMap<Integer,Integer>>>>>();
			for(Entry<Integer, DATA_Destination> dests:destinations.entrySet()){
				touristPerDest.put(dests.getKey(), dests.getValue().touristsPerTimeSourceAndCat);
			}	
		}
	}
	
	/**
	 * Inits the gmt: Societal scenario and Actions will be initialized.
	 */
	private void initGMT()
	{
		// Gesellschaftliche Megatrends
		/*int gmt;
	    //try
	    gmt = Integer.parseInt(this.componentConfig().getComponentProperties().getProperty("gmt"));
	    */
	}
	@Override
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>>>> getNumberOfTourists() {
		// TODO Auto-generated method stub
		//return numberOfTourists;
		return touristPerDest;
	}
	  
}





