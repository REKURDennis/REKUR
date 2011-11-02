package org.glowa.danube.components.actor.climatemodel;



import java.io.File;

import org.glowa.danube.components.actor.interfaces.RekurClimateModelToModelController;
import org.glowa.danube.simulation.model.AbstractModel;
import org.glowa.danube.tables.FloatDataTable;
import org.glowa.danube.tables.IntegerDataTable;
import org.glowa.danube.tables.MassPerAreaTable;
import org.glowa.danube.tables.TemperatureTable;
import org.glowa.danube.utilities.execution.ProvideEngine;
import org.glowa.danube.utilities.execution.ProvideTask;
import org.glowa.danube.utilities.time.DanubiaCalendar;


/**
 * Handles the climatedata for simulation runs.
 * @author Dennis Joswig
 * @version 30. Juni 2010
 * 
 * 
 */
public class ClimateModelMainClass extends AbstractModel<ClimateProxel> implements RekurClimateModelToModelController

{
	/**
	 * Saves the TemperatureTable with the air mean temperature of the current simulationday.
	 */
	private TemperatureTable airTemperatureDailyMean;
	/**
	 * Saves the TemperatureTable with the air max temperature of the current simulationday.
	 */
	private TemperatureTable airTemperatureDailyMax;
	/**
	 * Saves the TemperatureTable with the air min temperature of the current simulationday.
	 */
	private TemperatureTable airTemperatureDailyMin;
	/**
	 * Saves the MassPerAreaTable with the precipitationsum of the current simulationday.
	 */
	private MassPerAreaTable precipitationDailySum;
	/**
	 * Saves the FloatDataTable with the sunshineduration of the current simulationday.
	 */
	private FloatDataTable sunshineDurationDailySum;
	/**
	 * Saves the FloatDataTable with the mean windspeed of the current simulationday.
	 */
	private FloatDataTable windSpeedDailyMean;
	/**
	 * Saves the FloatDataTable with the max windspeed of the current simulationday.
	 */
	private FloatDataTable windSpeedDailyMax;
	/**
	 * Saves the FloatDataTable with the mean relative humidity of the current simulationday.
	 */
	private FloatDataTable relativeHumidityDailyMean;
	/**
	 * Saves the IntegerDataTable with the max temperature humidity of the current simulationday.
	 */
	private IntegerDataTable temperatureHumidityDailyIndex;
	
	/**
	 * svaes the refence on the NetCDFReader-Object.
	 */
	private NetCDFReader netCDFReader;
	
	/**
	 * Total number of simulationdays starting with 0.
	 */
	private int currentday = 0;
	
	
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.component.AbstractComponent#init()
	 */
	@Override
	protected void init() {
		//Utilities.IGNORE_RAS_TIMESTAMPS = true;
		System.out.println("init climate");
		
		airTemperatureDailyMean = new TemperatureTable(areaMetaData());
		airTemperatureDailyMax = new TemperatureTable(areaMetaData());
		airTemperatureDailyMin = new TemperatureTable(areaMetaData());
		precipitationDailySum = new MassPerAreaTable(areaMetaData());
		sunshineDurationDailySum = new FloatDataTable(areaMetaData());
		windSpeedDailyMean = new FloatDataTable(areaMetaData());
		windSpeedDailyMax = new FloatDataTable(areaMetaData());
		relativeHumidityDailyMean = new FloatDataTable(areaMetaData());
		temperatureHumidityDailyIndex = new IntegerDataTable(areaMetaData());
		
		netCDFReader = new NetCDFReader();
		
    	//this.provide(this.simulationStart());
		netCDFReader.climateFolderPath = "ClimateData"+ File.separator;
		netCDFReader.airTemperatureDailyMeanFileName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMeanFileName");
		netCDFReader.airTemperatureDailyMaxFileName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMaxFileName");
		netCDFReader.airTemperatureDailyMinFileName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMinFileName");
		netCDFReader.precipitationDailySumFileName = this.componentConfig().getComponentProperties().getProperty("precipitationDailySumFileName");
		netCDFReader.sunshineDurationDailySumFileName = this.componentConfig().getComponentProperties().getProperty("sunshineDurationDailySumFileName");
		netCDFReader.windSpeedDailyMeanFileName = this.componentConfig().getComponentProperties().getProperty("windSpeedDailyMeanFileName");
		netCDFReader.windSpeedDailyMaxFileName = this.componentConfig().getComponentProperties().getProperty("windSpeedDailyMaxFileName");
		netCDFReader.relativeHumidity3hFileName = this.componentConfig().getComponentProperties().getProperty("relativeHumidity3hFileName");
		netCDFReader.airTemperature3hFileName = this.componentConfig().getComponentProperties().getProperty("airTemperature3hFileName");
		
		
		
		netCDFReader.airTemperatureDailyMeanValueName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMeanValueName");
		netCDFReader.airTemperatureDailyMaxValueName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMaxValueName");
		netCDFReader.airTemperatureDailyMinValueName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMinValueName");
		netCDFReader.precipitationDailySumValueName = this.componentConfig().getComponentProperties().getProperty("precipitationDailySumValueName");
		netCDFReader.sunshineDurationDailySumValueName = this.componentConfig().getComponentProperties().getProperty("sunshineDurationDailySumValueName");
		netCDFReader.windSpeedDailyMeanValueName = this.componentConfig().getComponentProperties().getProperty("windSpeedDailyMeanValueName");
		netCDFReader.windSpeedDailyMaxValueName = this.componentConfig().getComponentProperties().getProperty("windSpeedDailyMaxValueName");
		netCDFReader.relativeHumidity3hValueName = this.componentConfig().getComponentProperties().getProperty("relativeHumidity3hValueName");
		netCDFReader.airTemperature3hValueName = this.componentConfig().getComponentProperties().getProperty("airTemperature3hValueName");
		
//		netCDFReader.initFile(climateFolderPath+meanTempFileName);
		netCDFReader.initCoordinateSystem();
		
	}

	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.component.AbstractComponent#finish()
	 */
	@Override
	public void finish() {
		// Free export tables
	}	
	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.model.AbstractModel#getData(org.glowa.danube.utilities.time.DanubiaCalendar)
	 */
	@Override
	public void getData(DanubiaCalendar actTime) {
		/*
		 * Neue Klimadaten einlesen
		*/
		System.out.println("ClimateGetData");
		netCDFReader.readAirTemperatureDailyMean(currentday);
		netCDFReader.readAirTemperatureDailyMax(currentday);
		netCDFReader.readAirTemperatureDailyMin(currentday);
		netCDFReader.readPrecipitationDailySum(currentday);
		netCDFReader.readSunshineDurationDailySum(currentday);
		netCDFReader.readWindSpeedDailyMean(currentday);
		netCDFReader.readWindSpeedDailyMax(currentday);
		netCDFReader.readRelativeHumidityDailyMean(currentday);
		netCDFReader.calcTempHumidityIndex(currentday);
		
	}

	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.model.AbstractModel#compute(org.glowa.danube.utilities.time.DanubiaCalendar)
	 */
	@Override
	public void compute(DanubiaCalendar actTime) {
		System.out.println("ClimateCompute");
		//Data-Objekt zur Uebergabe an Proxel mit netCDFReader-Objekt verkn�pfen, da dort die KlimaArrays vorliegen.
		Object data = netCDFReader;
		//Proxel anweisen Klimadaten zu laden.
		computeProxelsParallel(actTime, data);
	}

	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.model.AbstractModel#provide(org.glowa.danube.utilities.time.DanubiaCalendar)
	 */
	@Override
	public void provide(DanubiaCalendar t) {
		System.out.println("ClimateProvide");
		currentday++;
		provideEngineDaily.provide();
	}
	
	
	/**
     * Daily provide engine
     */
    private ProvideEngine provideEngineDaily = new ProvideEngine();
    {
//    	MeanTemp
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			
    			for( int i=0; i<pids().length; i++ ){ 
					airTemperatureDailyMean.setValue(i, proxel(i).cd.airTemperatureMean);
				}
    		}
    	});
//    	MaxTemp
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			for( int i=0; i<pids().length; i++ ){ 
					airTemperatureDailyMax.setValue(i, proxel(i).cd.airTemperatureMax);
				}
    		}
    	});
//    	MinTemp
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			for( int i=0; i<pids().length; i++ ){ 
					airTemperatureDailyMin.setValue(i, proxel(i).cd.airTemperatureMin);
				}
    		}
    	});
//    	PrecepSum
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			for( int i=0; i<pids().length; i++ ){ 
    				precipitationDailySum.setValue(i, proxel(i).cd.precipitationSum);
				}
    		}
    	});
//    	SunDurance
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			for( int i=0; i<pids().length; i++ ){ 
    				sunshineDurationDailySum.setValue(i, proxel(i).cd.sunshineDurationSum);
				}
    		}
    	});
//    	MeanWindSpeed
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			
    			for( int i=0; i<pids().length; i++ ){ 
					windSpeedDailyMean.setValue(i, proxel(i).cd.windSpeedMean);
				}
    		}
    	});
//    	MaxWindSpeed
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			
    			for( int i=0; i<pids().length; i++ ){ 
					windSpeedDailyMax.setValue(i, proxel(i).cd.windSpeedMax);
				}
    		}
    	});
//    	RelativeHuminity
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			
    			for( int i=0; i<pids().length; i++ ){ 
    				relativeHumidityDailyMean.setValue(i, proxel(i).cd.relativeHumidityMean);
				}
    		}
    	});
    	
    	
//    	TemperatureHumidityIndex
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			
    			for( int i=0; i<pids().length; i++ ){ 
    				temperatureHumidityDailyIndex.setValue(i, proxel(i).cd.temperatureHumidityIndex);
				}
    		}
    	});
    }
	
	

	@Override
	public TemperatureTable getAirTemperatureDailyMean() {
		// TODO Auto-generated method stub
		return airTemperatureDailyMean;
	}

	@Override
	public TemperatureTable getAirTemperatureDailyMax() {
		// TODO Auto-generated method stub
		return airTemperatureDailyMax;
	}

	@Override
	public TemperatureTable getAirTemperatureDailyMin() {
		// TODO Auto-generated method stub
		return airTemperatureDailyMin;
	}

	@Override
	public MassPerAreaTable getPrecipitationDailySum() {
		// TODO Auto-generated method stub
		return precipitationDailySum;
	}

	@Override
	public FloatDataTable getSunshineDurationDailySum() {
		// TODO Auto-generated method stub
		return sunshineDurationDailySum;
	}

	@Override
	public FloatDataTable getWindSpeedDailyMean() {
		// TODO Auto-generated method stub
		return windSpeedDailyMean;
	}

	@Override
	public FloatDataTable getWindSpeedDailyMax() {
		// TODO Auto-generated method stub
		return windSpeedDailyMax;
	}

	@Override
	public FloatDataTable getRelativeHumidityDailyMean() {
		// TODO Auto-generated method stub
		return relativeHumidityDailyMean;
	}

	@Override
	public IntegerDataTable getTempHumidityIndex() {
		// TODO Auto-generated method stub
		return temperatureHumidityDailyIndex;
	}
}
