package org.glowa.danube.components.actor.climatemodel;



import java.io.File;

import org.glowa.danube.components.actor.interfaces.RekurClimateModelToActorController;
import org.glowa.danube.simulation.model.AbstractModel;
import org.glowa.danube.tables.FloatDataTable;
import org.glowa.danube.tables.MassPerAreaTable;
import org.glowa.danube.tables.TemperatureTable;
import org.glowa.danube.utilities.execution.ProvideEngine;
import org.glowa.danube.utilities.execution.ProvideTask;
import org.glowa.danube.utilities.internal.DanubiaLogger;
import org.glowa.danube.utilities.time.DanubiaCalendar;


/**
 * @author Dennis Joswig
 * @version 30. Juni 2010
 * Handles the climatedata for simulation runs
 * 
 */
public class ClimateModel extends AbstractModel<ClimateProxel> implements RekurClimateModelToActorController

{
	private TemperatureTable airTemperatureDailyMean;
	private TemperatureTable airTemperatureDailyMax;
	private TemperatureTable airTemperatureDailyMin;
	private MassPerAreaTable precipitationDailySum;
	private FloatDataTable sunshineDurationDailySum;
	private FloatDataTable windSpeedDailyMean;
	private FloatDataTable windSpeedDailyMax;
	private FloatDataTable relativeHuminityDailyMean;
	
	private String airTemperatureDailyMeanFileName = "";
	private String airTemperatureDailyMaxFileName = "";
	private String airTemperatureDailyMinFileName = "";
	private String precipitationDailySumFileName = "";
	private String sunshineDurationDailySumFileName = "";
	private String windSpeedDailyMeanFileName = "";
	private String windSpeedDailyMaxFileName = "";
	private String relativeHuminityDailyMeanFileName = "";
	
	private String climateFolderPath = modeloutpath() + File.separator + "ClimateData"+ File.separator;
	private NetCDFReader netCDFReader;
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
		relativeHuminityDailyMean = new FloatDataTable(areaMetaData());
		
    	//this.provide(this.simulationStart());
		climateFolderPath = "ClimateData"+ File.separator;
		airTemperatureDailyMeanFileName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMeanFileName");
		airTemperatureDailyMaxFileName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMaxFileName");
		airTemperatureDailyMinFileName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMinFileName");
		precipitationDailySumFileName = this.componentConfig().getComponentProperties().getProperty("precipitationDailySumFileName");
		sunshineDurationDailySumFileName = this.componentConfig().getComponentProperties().getProperty("sunshineDurationDailySumFileName");
		windSpeedDailyMeanFileName = this.componentConfig().getComponentProperties().getProperty("windSpeedDailyMeanFileName");
		windSpeedDailyMaxFileName = this.componentConfig().getComponentProperties().getProperty("windSpeedDailyMaxFileName");
		relativeHuminityDailyMeanFileName = this.componentConfig().getComponentProperties().getProperty("relativeHuminityDailyMeanFileName");
		
		
		netCDFReader = new NetCDFReader();
		
		netCDFReader.airTemperatureDailyMeanValueName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMeanValueName");
		netCDFReader.airTemperatureDailyMaxValueName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMaxValueName");
		netCDFReader.airTemperatureDailyMinValueName = this.componentConfig().getComponentProperties().getProperty("airTemperatureDailyMinValueName");
		netCDFReader.precipitationDailySumValueName = this.componentConfig().getComponentProperties().getProperty("precipitationDailySumValueName");
		netCDFReader.sunshineDurationDailySumValueName = this.componentConfig().getComponentProperties().getProperty("sunshineDurationDailySumValueName");
		netCDFReader.windSpeedDailyMeanValueName = this.componentConfig().getComponentProperties().getProperty("windSpeedDailyMeanValueName");
		netCDFReader.windSpeedDailyMaxValueName = this.componentConfig().getComponentProperties().getProperty("windSpeedDailyMaxValueName");
		netCDFReader.relativeHuminityDailyMeanValueName = this.componentConfig().getComponentProperties().getProperty("relativeHuminityDailyMeanValueName");
		
//		netCDFReader.initFile(climateFolderPath+meanTempFileName);
		netCDFReader.initCoordinateSystem(climateFolderPath+airTemperatureDailyMeanFileName);
		
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
		netCDFReader.readAirTemperatureDailyMean(currentday, climateFolderPath+airTemperatureDailyMeanFileName);
		netCDFReader.readAirTemperatureDailyMax(currentday, climateFolderPath+airTemperatureDailyMaxFileName);
		netCDFReader.readAirTemperatureDailyMin(currentday, climateFolderPath+airTemperatureDailyMinFileName);
		netCDFReader.readPrecipitationDailySum(currentday, climateFolderPath+precipitationDailySumFileName);
		netCDFReader.readSunshineDurationDailySum(currentday, climateFolderPath+sunshineDurationDailySumFileName);
		netCDFReader.readWindSpeedDailyMean(currentday, climateFolderPath+windSpeedDailyMeanFileName);
		netCDFReader.readWindSpeedDailyMax(currentday, climateFolderPath+windSpeedDailyMaxFileName);
		netCDFReader.readRelativeHuminityDailyMean(currentday, climateFolderPath+relativeHuminityDailyMeanFileName);
	}

	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.model.AbstractModel#compute(org.glowa.danube.utilities.time.DanubiaCalendar)
	 */
	@Override
	public void compute(DanubiaCalendar actTime) {
		//Data-Objekt zur Uebergabe an Proxel mit netCDFReader-Objekt verknüpfen, da dort die KlimaArrays vorliegen.
		Object data = netCDFReader;
		//Proxel anweisen Klimadaten zu laden.
		computeProxelsParallel(actTime, data);
	}

	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.model.AbstractModel#provide(org.glowa.danube.utilities.time.DanubiaCalendar)
	 */
	@Override
	public void provide(DanubiaCalendar t) {
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
					airTemperatureDailyMean.setValue(i, proxel(i).airTemperatureDailyMean);
				}
    		}
    	});
//    	MaxTemp
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			for( int i=0; i<pids().length; i++ ){ 
					airTemperatureDailyMax.setValue(i, proxel(i).airTemperatureDailyMax);
				}
    		}
    	});
//    	MinTemp
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			for( int i=0; i<pids().length; i++ ){ 
					airTemperatureDailyMin.setValue(i, proxel(i).airTemperatureDailyMin);
				}
    		}
    	});
//    	PrecepSum
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			for( int i=0; i<pids().length; i++ ){ 
    				precipitationDailySum.setValue(i, proxel(i).precipitationDailySum);
				}
    		}
    	});
//    	SunDurance
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			for( int i=0; i<pids().length; i++ ){ 
    				sunshineDurationDailySum.setValue(i, proxel(i).sunshineDurationDailySum);
				}
    		}
    	});
//    	MeanWindSpeed
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			
    			for( int i=0; i<pids().length; i++ ){ 
					windSpeedDailyMean.setValue(i, proxel(i).windSpeedDailyMean);
				}
    		}
    	});
//    	MaxWindSpeed
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			
    			for( int i=0; i<pids().length; i++ ){ 
					windSpeedDailyMax.setValue(i, proxel(i).windSpeedDailyMax);
				}
    		}
    	});
//    	RelatuveHuminity
    	provideEngineDaily.add(new ProvideTask()
    	{
    		public void run()
    		{
    			
    			for( int i=0; i<pids().length; i++ ){ 
    				relativeHuminityDailyMean.setValue(i, proxel(i).relativeHuminityDailyMean);
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
	public FloatDataTable getRelativeHuminityDailyMean() {
		// TODO Auto-generated method stub
		return relativeHuminityDailyMean;
	}
}
