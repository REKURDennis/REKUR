package org.glowa.danube.components.actor.climatemodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ucar.ma2.Array;
import ucar.ma2.Range;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class NetCDFReader {
	public float[][] airTemperatureDailyMean;
	public float[][] airTemperatureDailyMax;
	public float[][] airTemperatureDailyMin;
	public float[][] precipitationDailySum;
	public float[][] sunshineDurationDailySum;
	public float[][] windSpeedDailyMean;
	public float[][] windSpeedDailyMax;
	public float[][] relativeHumidityDailyMean;
	public int[][] temperaturHumidityIndex;
	
	public String climateFolderPath;
	
	public String airTemperatureDailyMeanFileName = "";
	public String airTemperatureDailyMaxFileName = "";
	public String airTemperatureDailyMinFileName = "";
	public String precipitationDailySumFileName = "";
	public String sunshineDurationDailySumFileName = "";
	public String windSpeedDailyMeanFileName = "";
	public String windSpeedDailyMaxFileName = "";
	public String relativeHumidity3hFileName = "";
	public String airTemperature3hFileName = "";
	
	public String airTemperatureDailyMeanValueName;
	public String airTemperatureDailyMaxValueName;
	public String airTemperatureDailyMinValueName;
	public String precipitationDailySumValueName;
	public String sunshineDurationDailySumValueName;
	public String windSpeedDailyMeanValueName;
	public String windSpeedDailyMaxValueName;
	public String relativeHumidity3hValueName;
	public String airTemperature3hValueName;
	
	public double[] lon;
	public double[] lat;
	public NetcdfFile ncfile = null;
	
	
	public NetCDFReader(){
		
	}
	
	
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
	
	public void readAirTemperatureDailyMean(int day){
		String filename = climateFolderPath+airTemperatureDailyMeanFileName;
		airTemperatureDailyMean = readClimateData(day, filename, airTemperatureDailyMeanValueName);
	}
	
	public void readAirTemperatureDailyMax(int day){
		String filename = climateFolderPath+airTemperatureDailyMaxFileName;
		airTemperatureDailyMax = readClimateData(day, filename, airTemperatureDailyMaxValueName);
	}
	
	public void readAirTemperatureDailyMin(int day){
		String filename = climateFolderPath+airTemperatureDailyMinFileName;
		airTemperatureDailyMin = readClimateData(day, filename, airTemperatureDailyMinValueName);
	}
	
	public void readPrecipitationDailySum(int day){
		String filename = climateFolderPath+precipitationDailySumFileName;
		precipitationDailySum = readClimateData(day, filename, precipitationDailySumValueName);
	}
	
	public void readSunshineDurationDailySum(int day){
		String filename = climateFolderPath+sunshineDurationDailySumFileName;
		sunshineDurationDailySum = readClimateData(day, filename, sunshineDurationDailySumValueName);
	}
	
	public void readWindSpeedDailyMean(int day){
		String filename = climateFolderPath+windSpeedDailyMeanFileName;
		windSpeedDailyMean = readClimateData(day, filename, windSpeedDailyMeanValueName);
	}
	
	public void readWindSpeedDailyMax(int day){
		String filename = climateFolderPath+windSpeedDailyMaxFileName;
		windSpeedDailyMax = readClimateData(day, filename, windSpeedDailyMaxValueName);
	}
	
	
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
	
	public void calcTempHumidityIndex(int day){
		String relHumFilePath = climateFolderPath+relativeHumidity3hFileName;
		String airTemp3hFilePath = climateFolderPath+airTemperature3hFileName;
		for(int i = 0; i<8;i++){
			float[][] relHum3h = readClimateData((day*8+i), relHumFilePath, relativeHumidity3hValueName);
			float[][] airTemp3h =  readClimateData((day*8+i), airTemp3hFilePath, airTemperature3hValueName);
			for(int x = 0; x<relHum3h.length; x++){
				for(int y = 0; y<relHum3h[0].length; y++){
					if(temperaturHumidityIndex[x][y] == 0){
						temperaturHumidityIndex[x][y] = (int)((Math.exp((-849.424)+13.5372*(double)(airTemp3h[0][0])+2.386084*relHum3h[x][y]))/
														(1+Math.exp((-849.424)+13.5372*(double)(airTemp3h[x][y])+2.386084*relHum3h[0][0]))*100.0);
					}
				}
			}	
		}
	}
	
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
