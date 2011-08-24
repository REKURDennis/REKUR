package org.glowa.danube.components.actor.climatemodel;

import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.simulation.model.proxel.AbstractProxel;
import org.glowa.danube.utilities.time.DanubiaCalendar;


/**
 * Specifies the additional attributes of the climate model proxel
 * @author Dennis Joswig
 *
 */
public class ClimateProxel extends AbstractProxel{
	/**
	 * Saves the daily climatedata for this proxel.
	 */
	public ClimateData cd = new ClimateData();
	
	/**
	 * the appropriate longitude-bucket of the climatedata-files.
	 */
	private int lonBucket;
	/**
	 * the appropriate latitude-bucket of the climatedata-files.
	 */
	private int latBucket;
	/**
	 * Saves the NetCDFReader-Object reference.
	 */
	private NetCDFReader netCDFReader;
	/**
	 * Saves if the proxel is already init.
	 */
	private boolean init = true;
	/**
	 * Unique number for serialization. 
	 */
	private static final long serialVersionUID = 11745238;
	@Override
	public void computeProxel(DanubiaCalendar actTime, Object data) {
		netCDFReader = (NetCDFReader)data;
		if(init){
			initLonLatBuckets();
			init = false;
		}
		getClimateData();
		super.computeProxel(actTime, data);
	}
	
	/**
	 * init the longitude and latitude buckets for reading the climatedata.
	 */
	private void initLonLatBuckets(){
		int lon = (int)(easting()*100);
		int lat = (int)(northing()*100);
		int dist = 1000;
		for(int i = 0; i<netCDFReader.lon.length;i++){
			int currentBucketValue = (int)(netCDFReader.lon[i]*100);
			int currentDist = Math.abs(currentBucketValue-lon);
			if(currentDist<dist){
				dist = currentDist;
				lonBucket = i;
			}
		}
		dist = 1000;
		for(int i = 0; i<netCDFReader.lat.length;i++){
			int currentBucketValue = (int)(netCDFReader.lat[i]*100);
			int currentDist = Math.abs(currentBucketValue-lat);
			if(currentDist<dist){
				dist = currentDist;
				latBucket = i;
			}
		}
	}
	
	/**
	 * gets the climatedata from the netCDFReader.
	 */
	private void getClimateData(){
		try{
			cd.airTemperatureMean = netCDFReader.airTemperatureDailyMean[latBucket][lonBucket];
			cd.airTemperatureMax = netCDFReader.airTemperatureDailyMax[latBucket][lonBucket];
			cd.airTemperatureMin = netCDFReader.airTemperatureDailyMin[latBucket][lonBucket];
			cd.precipitationSum = netCDFReader.precipitationDailySum[latBucket][lonBucket];
			cd.sunshineDurationSum = netCDFReader.sunshineDurationDailySum[latBucket][lonBucket];
			cd.windSpeedMean = netCDFReader.windSpeedDailyMean[latBucket][lonBucket];
			cd.windSpeedMax = netCDFReader.windSpeedDailyMax[latBucket][lonBucket];
			cd.relativeHumidityMean = netCDFReader.relativeHumidityDailyMean[latBucket][lonBucket];
			cd.temperatureHumidityIndex = netCDFReader.temperaturHumidityIndex[latBucket][lonBucket];
		}
		catch(Exception e){
			
		}
	}
}