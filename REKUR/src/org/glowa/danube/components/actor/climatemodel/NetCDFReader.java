package org.glowa.danube.components.actor.climatemodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ucar.ma2.Array;
import ucar.ma2.Range;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * The NetCDFReader-Class offers methods and attributes to read in Climatedata saved as NetCDF-Format.
 * @author Dennis Joswig
 *
 */

public class NetCDFReader {
	/**
	 * Saves the floattable with the air mean temperature of the current simulationday.
	 */
	public float[][] airTemperatureDailyMean;
	/**
	 * Saves the floattable with the air max temperature of the current simulationday.
	 */
	public float[][] airTemperatureDailyMax;
	/**
	 * Saves the floattable with the air min temperature of the current simulationday.
	 */
	public float[][] airTemperatureDailyMin;
	/**
	 * Saves the floattable with the precipitationsum of the current simulationday.
	 */
	public float[][] precipitationDailySum;
	/**
	 * Saves the floattable with the sunshinedurationsum of the current simulationday.
	 */
	public float[][] sunshineDurationDailySum;
	/**
	 * Saves the floattable with the air mean windspeed of the current simulationday.
	 */
	public float[][] windSpeedDailyMean;
	/**
	 * Saves the floattable with the max windspeed of the current simulationday.
	 */
	public float[][] windSpeedDailyMax;
	/**
	 * Saves the floattable with the mean relative humidity of the current simulationday.
	 */
	public float[][] relativeHumidityDailyMean;
	/**
	 * Saves the integertable with temperature humidity of the current simulationday.
	 */
	public double[][] temperaturHumidityIndex;
	/**
	 * Saves the climateFolderPath;
	 */
	public String climateFolderPath;
	
	
	/**
	 * Saves the filename of the mean air temperature *.-file.
	 */
	public String airTemperatureDailyMeanFileName = "";
	/**
	 * Saves the filename of the max air temperature *.-file.
	 */
	public String airTemperatureDailyMaxFileName = "";
	/**
	 * Saves the filename of the min air temperature *.-file.
	 */
	public String airTemperatureDailyMinFileName = "";
	/**
	 * Saves the filename of the precipitation sum *.-file.
	 */
	public String precipitationDailySumFileName = "";
	/**
	 * Saves the filename of the sunshineduration sum *.-file.
	 */
	public String sunshineDurationDailySumFileName = "";
	/**
	 * Saves the filename of the mean windspeed *.-file.
	 */
	public String windSpeedDailyMeanFileName = "";
	/**
	 * Saves the filename of the max windspeed *.-file.
	 */
	public String windSpeedDailyMaxFileName = "";
	/**
	 * Saves the filename of the relative humidity 3h *.-file.
	 */
	public String relativeHumidity3hFileName = "";
	/**
	 * Saves the filename of the mean air temperature per 3h *.-file.
	 */
	public String airTemperature3hFileName = "";
	
	
	/**
	 * Saves the value name for the daily mean air temperature.
	 */
	public String airTemperatureDailyMeanValueName;
	/**
	 * Saves the value name for the daily max air temperature.
	 */
	public String airTemperatureDailyMaxValueName;
	/**
	 * Saves the value name for the daily min air temperature.
	 */
	public String airTemperatureDailyMinValueName;
	/**
	 * Saves the value name for the daily precipitationsum.
	 */
	public String precipitationDailySumValueName;
	/**
	 * Saves the value name for the daily sunshineduration.
	 */
	public String sunshineDurationDailySumValueName;
	/**
	 * Saves the value name for the daily mean windspeed.
	 */
	public String windSpeedDailyMeanValueName;
	/**
	 * Saves the value name for the daily max windspeed.
	 */
	public String windSpeedDailyMaxValueName;
	/**
	 * Saves the value name for the 3h relative humidity.
	 */
	public String relativeHumidity3hValueName;
	/**
	 * Saves the value name for the 3h mean air temperature.
	 */
	public String airTemperature3hValueName;
	
	/**
	 * saves the longitude-values of the climatedatafiles.
	 */
	public double[] lon;
	/**
	 * saves the latitude-values of the climatedatafiles.
	 */
	public double[] lat;
	/**
	 * saves the current reading file.
	 */
	public NetcdfFile ncfile = null;
	
	/**
	 * This method inits the coordinate System from the air-temperature-daily-file.
	 */
	public void initCoordinateSystem(){
		try {
		    ncfile = NetcdfFile.open(climateFolderPath+airTemperatureDailyMeanFileName);
		    List<Variable> vList = ncfile.getVariables();
		    Variable lonVariable = null;
		    Variable latVariable = null;
		    for(int i=0; i < vList.size();i++){
		    	Variable v = vList.get(i);
		    	if(v.getName().equals("lon")){
		    		lonVariable = v;
		    	}
		    	if(v.getName().equals("lat")){
		    		latVariable = v;
		    	}
		    }
		    try{
		    	Array lonData =lonVariable.read(lonVariable.getRanges()).reduce();
		    	lon = (double[])lonData.copyToNDJavaArray();
		    	Array latData =latVariable.read(latVariable.getRanges()).reduce();
		    	lat = (double[])latData.copyToNDJavaArray();
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    	System.out.println("lonlatException");
		    }
		}
		catch (IOException ioe) {
		    System.out.println("trying to open " + climateFolderPath+airTemperatureDailyMeanFileName+ ioe.getMessage());
		} 
		finally { 
		    if (null != ncfile) try {
		      ncfile.close();
		    } catch (IOException ioe) {
		    	System.out.println("trying to close " + climateFolderPath+airTemperatureDailyMeanFileName+ ioe.getMessage());
		    }
		}	
	}
	
	/**
	 * This methods reads in the mean air temperature for the day-number.
	 * @param day Total number of the days from beginning of the file.
	 */
	public void readAirTemperatureDailyMean(int day){
		String filename = climateFolderPath+airTemperatureDailyMeanFileName;
		airTemperatureDailyMean = readClimateData(day, filename, airTemperatureDailyMeanValueName);
	}
	
	/**
	 * This methods reads in the max air temperature for the day-number.
	 * @param day Total number of the days from beginning of the file.
	 */
	public void readAirTemperatureDailyMax(int day){
		String filename = climateFolderPath+airTemperatureDailyMaxFileName;
		airTemperatureDailyMax = readClimateData(day, filename, airTemperatureDailyMaxValueName);
	}
	
	/**
	 * This methods reads in the min air temperature for the day-number.
	 * @param day Total number of the days from beginning of the file.
	 */
	public void readAirTemperatureDailyMin(int day){
		String filename = climateFolderPath+airTemperatureDailyMinFileName;
		airTemperatureDailyMin = readClimateData(day, filename, airTemperatureDailyMinValueName);
	}
	
	/**
	 * This methods reads in the precipitation sum for the day-number.
	 * @param day Total number of the days from beginning of the file.
	 */
	public void readPrecipitationDailySum(int day){
		String filename = climateFolderPath+precipitationDailySumFileName;
		precipitationDailySum = readClimateData(day, filename, precipitationDailySumValueName);
	}
	
	/**
	 * This methods reads in the sunshine duration sum for the day-number.
	 * @param day Total number of the days from beginning of the file.
	 */
	public void readSunshineDurationDailySum(int day){
		String filename = climateFolderPath+sunshineDurationDailySumFileName;
		sunshineDurationDailySum = readClimateData(day, filename, sunshineDurationDailySumValueName);
	}
	
	/**
	 * This methods reads in the mean windspeed for the day-number.
	 * @param day Total number of the days from beginning of the file.
	 */
	public void readWindSpeedDailyMean(int day){
		String filename = climateFolderPath+windSpeedDailyMeanFileName;
		windSpeedDailyMean = readClimateData(day, filename, windSpeedDailyMeanValueName);
	}
	
	/**
	 * This methods reads in the max windspeed for the day-number.
	 * @param day Total number of the days from beginning of the file.
	 */
	public void readWindSpeedDailyMax(int day){
		String filename = climateFolderPath+windSpeedDailyMaxFileName;
		windSpeedDailyMax = readClimateData(day, filename, windSpeedDailyMaxValueName);
	}
	
	/**
	 * This methods reads in the mean relative humidity for the day-number
	 * @param day Total number of the days from beginning of the file.
	 */
	public void readRelativeHumidityDailyMean(int day){
		try{
			String filename = climateFolderPath+relativeHumidity3hFileName;
			relativeHumidityDailyMean = readClimateData((day*8), filename, relativeHumidity3hValueName);
			for(int i = 1; i<8;i++){
				float[][] relativeHuminity3hMean = readClimateData((day*8)+i, filename, relativeHumidity3hValueName);
				for(int x = 0; x<relativeHuminity3hMean.length; x++){
					for(int y = 0; y<relativeHuminity3hMean[0].length; y++){
						relativeHumidityDailyMean[x][y] += relativeHuminity3hMean[x][y];
						if(i == 8){
							relativeHumidityDailyMean[x][y] /=8;
						}
					}
				}
			}
		}
		catch(Exception e){
			System.out.println("no relhum available");
		}
	}
	
	/**
	 * This methods reads in the temperature humidity index for the day-number
	 * @param day Total number of the days from beginning of the file.
	 */
	public void calcTempHumidityIndex(int day){
		try{
			String relHumFilePath = climateFolderPath+relativeHumidity3hFileName;
			String airTemp3hFilePath = climateFolderPath+airTemperature3hFileName;
			float[][] initsize = readClimateData((day), relHumFilePath, relativeHumidity3hValueName);
			temperaturHumidityIndex = new double[initsize.length][initsize[0].length];
			for(int i = 0; i<8;i++){
				float[][] relHum3h = readClimateData((day*8+i), relHumFilePath, relativeHumidity3hValueName);
				float[][] airTemp3h =  readClimateData((day*8+i), airTemp3hFilePath, airTemperature3hValueName);
				for(int x = 0; x<relHum3h.length; x++){
					for(int y = 0; y<relHum3h[0].length; y++){
						if(temperaturHumidityIndex[x][y] == 0){
							temperaturHumidityIndex[x][y] = (Math.exp((-849.424)+13.5372*(double)(airTemp3h[x][y]-273.15)+2.386084*100.0*relHum3h[x][y])+(0.2527834*(100.0*relHum3h[x][y])+(airTemp3h[x][y]-273.15)))/
															(1+Math.exp((-849.424)+13.5372*(double)(airTemp3h[x][y]-273.15)+2.386084*100.0*relHum3h[x][y]+(0.2527834*(100.0*relHum3h[x][y])+(airTemp3h[x][y]-273.15))));
							System.out.print(temperaturHumidityIndex[x][y]+" ");
						}
					}
					System.out.println();
				}	
			}
		}catch(Exception e){
			System.out.println("no relhum or air 3h");
		}
	}
	
	/**
	 * generic *.nc-Filerader.
	 * @param day Total number of the days from beginning of the file.
	 * @param filename Name of the climatdatafile.
	 * @param valueName Name of the climatevalue within the file.
	 * @return Floattable with the data for each longitude-latitude value pair.
	 */
	private float[][] readClimateData(int day, String filename, String valueName){
		try {
		    ncfile = NetcdfFile.open(filename);
			List<Variable> vList = ncfile.getVariables();
			
		    Variable temp = null;
		    for(int i=0; i < vList.size();i++){
		    	Variable v = vList.get(i);
		    	
		    	if(v.getName().equals(valueName)){
		    		//vList.remove(v);
		    		temp = v;
		    	}
		    
		    }
		    
		    List<Range> ranges = temp.getRanges();
		    List<Range> newRanges = new ArrayList<Range>();
		    for(int i=0; i < ranges.size();i++){
		    	Range r = ranges.get(i);
		    	if(r.getName().equals("time")){
		    		try{
		    			newRanges.add(new Range(day,day));
		    		}
		    		catch(Exception e){		
		    		}
		    	}
		    	else{
		    		newRanges.add(r);
		    	}
		    }
		    try{
		    	Array tempdata =temp.read(newRanges).reduce();
		    	return((float[][])tempdata.copyToNDJavaArray());
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		}
		catch (IOException ioe) {
//		    System.out.println("failed to open " + filename+ ioe.getMessage());
		} 
		finally { 
		    if (null != ncfile) try {
		      ncfile.close();
		    } catch (IOException ioe) {
		    	System.out.println("trying to close " + filename+ ioe.getMessage());
		    }
		}
		return null;
	}
	
}
