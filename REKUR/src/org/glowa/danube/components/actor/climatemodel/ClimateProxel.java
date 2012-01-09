package org.glowa.danube.components.actor.climatemodel;

import org.glowa.danube.components.actor.utilities.AggregatedClimateData;
import org.glowa.danube.components.actor.utilities.ClimateDataAggregator;
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
	public AggregatedClimateData cd = new AggregatedClimateData();
	/**
	 * saves the 3h temperature;
	 */
	public float THItemp;
	/**
	 * saves the humidity for thi calculation.
	 */
	public float THIhum;
	
	/**
	 * the appropriate longitude-bucket of the climatedata-files.
	 */
	public int lonBucket;
	/**
	 * the appropriate latitude-bucket of the climatedata-files.
	 */
	public int latBucket;
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
	/**
	 * ClimateData Aggregator
	 */
	ClimateDataAggregator ca = new ClimateDataAggregator();
	@Override
	public void computeProxel(DanubiaCalendar actTime, Object data) {
		netCDFReader = (NetCDFReader)data;
		if(init){
			initLonLatBuckets();
			init = false;
		}
		getClimateData();
		super.computeProxel(actTime, data);
		ca.dailyClimate = cd;
		ca.aggregateClimateData(null, actTime.getDay());
		if(actTime.getDay()==1 &&!(actTime.getMonth()==1 && actTime.getYear()==2008)){
			calcTCI();
		}
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
			cd.relativeHumidityMin = netCDFReader.relativeHumidityDailyMin[latBucket][lonBucket];
			cd.temperatureHumidityIndex = netCDFReader.temperaturHumidityIndex[latBucket][lonBucket];
			
			THItemp = netCDFReader.THITemp[latBucket][lonBucket];
			THIhum = netCDFReader.THIHum[latBucket][lonBucket];
			
		}
		catch(Exception e){
//			e.printStackTrace();
		}
	}
	private void calcTCI(){
		float cid = 0.0f;
		float cia = 0.0f;
		float r = 0.0f;
		float s = 0.0f;
		float w = 0.0f;
		
		if(ca.lastMonthClimate.airTemperatureMax-273.15<15 && ca.lastMonthClimate.windSpeedMean>8){
			int wc = (int)((12.15+6.13*Math.sqrt(ca.lastMonthClimate.windSpeedMean*3.6)-0.32*ca.lastMonthClimate.windSpeedMean*3.6)*(33-ca.lastMonthClimate.airTemperatureMax-273.15));
			for(float[] iterator:ClimateModelMainClass.windratings815){
				if(iterator[0]<=wc && iterator[1]>wc){
					w = iterator[2];
					break;
				}
			}
		}
		else{
			float[] windcat = new float[5];
			for(float[] iterator:ClimateModelMainClass.windratings){
				if(iterator[0]<=ca.lastMonthClimate.windSpeedMean*3.6 && iterator[1]>ca.lastMonthClimate.windSpeedMean*3.6){
					windcat = iterator;
					break;
				}
			}
			if((ca.lastMonthClimate.airTemperatureMax-273.15>=15 && ca.lastMonthClimate.airTemperatureMax-273.15<24)||(ca.lastMonthClimate.airTemperatureMax-273.15<15 && ca.lastMonthClimate.windSpeedMean*3.6<=8)){
				w = windcat[2];
			}
			else{
				if(ca.lastMonthClimate.airTemperatureMax-273.15>=24 && ca.lastMonthClimate.airTemperatureMax-273.15<33){
					w = windcat[3];
				}
				else{
					if(ca.lastMonthClimate.airTemperatureMax-273.15>=33){
						w = windcat[4];
					}
				}
			}
		}
		
		for(float[] iterator:ClimateModelMainClass.precipratings){
			if(iterator[0]<=ca.lastMonthClimate.precipitationSum && iterator[1]>ca.lastMonthClimate.precipitationSum){
				r = iterator[2];
				break;
			}
		}
		for(float[] iterator:ClimateModelMainClass.sunratings){
			if(iterator[0]<=ca.lastMonthClimate.sunshineDurationSum/3600 && iterator[1]>ca.lastMonthClimate.sunshineDurationSum/3600){
				r = iterator[2];
				break;
			}
		}
		cid = cidRating(ca.lastMonthClimate.relativeHumidityMin, ca.lastMonthClimate.airTemperatureMax);
		//cid = cidRating(ca.lastMonthClimate.relativeHumidityMean, ca.lastMonthClimate.airTemperatureMax);
		cia = cidRating(ca.lastMonthClimate.relativeHumidityMean, ca.lastMonthClimate.airTemperatureMean);
		
		
		
		cd.TCI = (int)(4.0f*cid+cia+2.0f*r+2.0f*s+w);
	}
	private float cidRating(float hum, float temp){
		float cid = 0.0f;
		
		int roundHum = (int)((hum*10.0f)+0.5f);
		float[] tempRow = ClimateModelMainClass.cidtemps[roundHum];
		int i = 1;
		//if(pid() == 76692)System.out.println(tempRow.length);
		while(i<tempRow.length && tempRow[i]<(temp-273.15)){
			i++;
		}
		cid = ClimateModelMainClass.cidratings[i-1][2];
		return cid;
	}
}