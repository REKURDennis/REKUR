package org.glowa.danube.components.actor.climatemodel;

import org.glowa.danube.simulation.model.proxel.AbstractProxel;
import org.glowa.danube.utilities.time.DanubiaCalendar;


/**
 * Specifies the additional attributes of the climate model proxel
 * @author Dennis Joswig
 *
 */
public class ClimateProxel extends AbstractProxel{
	
	public float airTemperatureDailyMean = 0f;
	public float airTemperatureDailyMax = 0f;
	public float airTemperatureDailyMin = 0f;
	public float precipitationDailySum = 0f;
	public float sunshineDurationDailySum = 0f;
	public float windSpeedDailyMean = 0f;
	public float windSpeedDailyMax = 0f;
	public float relativeHuminityDailyMean = 0f;
	
	private int lonBucket;
	private int latBucket;
	private NetCDFReader netCDFReader;
	private boolean init = true;
	
	private static final long serialVersionUID = 11745238;
	@Override
	public void computeProxel(DanubiaCalendar actTime, Object data) {
		netCDFReader = (NetCDFReader)data;
//		if(isInside())System.out.println("isInside");
//		else System.out.println("isOutside");
		if(init){
			initLonLatBuckets();
//			System.out.println("initLonLat");
			init = false;
		}
		getClimateData();
		super.computeProxel(actTime, data);
	}
	
	
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
//		System.out.println(isInside());
//		System.out.println("ProxelID: "+pid()+" Longitude: "+lon+" Latitude: "+ lat);
//		System.out.println("ProxelID: "+pid()+" LongitudeBucketValue: "+netCDFReader.lon[lonBucket]+" LatitudeBucketValue: "+ netCDFReader.lat[latBucket]);
//		System.out.println("ProxelID: "+pid()+" LongitudeBucketNumber: "+lonBucket+" LatitudeBucketNumber: "+ latBucket);
	}
	
	
	private void getClimateData(){
		try{
			airTemperatureDailyMean = netCDFReader.airTemperatureDailyMean[latBucket][lonBucket];
			airTemperatureDailyMax = netCDFReader.airTemperatureDailyMax[latBucket][lonBucket];
			airTemperatureDailyMin = netCDFReader.airTemperatureDailyMin[latBucket][lonBucket];
			precipitationDailySum = netCDFReader.precipitationDailySum[latBucket][lonBucket];
			sunshineDurationDailySum = netCDFReader.sunshineDurationDailySum[latBucket][lonBucket];
			windSpeedDailyMean = netCDFReader.windSpeedDailyMean[latBucket][lonBucket];
			windSpeedDailyMax = netCDFReader.windSpeedDailyMax[latBucket][lonBucket];
			relativeHuminityDailyMean = netCDFReader.relativeHuminityDailyMean[latBucket][lonBucket];
		}
		catch(Exception e){
			
		}

//		System.out.println("ProxelID: "+pid()+" LongitudeBucketValue: "+netCDFReader.lon[lonBucket]+" LatitudeBucketValue: "+ netCDFReader.lat[latBucket]);
//		System.out.println("ProxelID: "+pid()+" MeanTemp "+ meanTemp);
//		System.out.println("ProxelID: "+pid()+" LongitudeBucketValue: "+netCDFReader.lon[lonBucket]+" LatitudeBucketValue: "+ netCDFReader.lat[latBucket]);
//		System.out.println("ProxelID: "+pid()+" MeanWindSpeed "+ meanWindSpeed);
	}
}