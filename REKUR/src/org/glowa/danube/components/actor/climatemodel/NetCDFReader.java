package org.glowa.danube.components.actor.climatemodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
	public float[][] relativeHuminityDailyMean;
	
	public String airTemperatureDailyMeanValueName;
	public String airTemperatureDailyMaxValueName;
	public String airTemperatureDailyMinValueName;
	public String precipitationDailySumValueName;
	public String sunshineDurationDailySumValueName;
	public String windSpeedDailyMeanValueName;
	public String windSpeedDailyMaxValueName;
	public String relativeHuminityDailyMeanValueName;
	
	public double[] lon;
	public double[] lat;
	public NetcdfFile ncfile = null;
	
	
	public NetCDFReader(){
		
	}
	
	
	public void initCoordinateSystem(String filename){
		try {
		    ncfile = NetcdfFile.open(filename);
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
		    System.out.println("trying to open " + filename+ ioe.getMessage());
		} 
		finally { 
		    if (null != ncfile) try {
		      ncfile.close();
		    } catch (IOException ioe) {
		    	System.out.println("trying to close " + filename+ ioe.getMessage());
		    }
		}	
	}
	
	public void readAirTemperatureDailyMean(int day, String filename){
		airTemperatureDailyMean = readClimateData(day, filename, airTemperatureDailyMeanValueName);
	}
	
	public void readAirTemperatureDailyMax(int day, String filename){
		airTemperatureDailyMax = readClimateData(day, filename, airTemperatureDailyMaxValueName);
	}
	
	public void readAirTemperatureDailyMin(int day, String filename){
		airTemperatureDailyMin = readClimateData(day, filename, airTemperatureDailyMinValueName);
	}
	
	public void readPrecipitationDailySum(int day, String filename){
		precipitationDailySum = readClimateData(day, filename, precipitationDailySumValueName);
	}
	
	public void readSunshineDurationDailySum(int day, String filename){
		sunshineDurationDailySum = readClimateData(day, filename, sunshineDurationDailySumValueName);
	}
	
	public void readWindSpeedDailyMean(int day, String filename){
		windSpeedDailyMean = readClimateData(day, filename, windSpeedDailyMeanValueName);
	}
	
	public void readWindSpeedDailyMax(int day, String filename){
		windSpeedDailyMax = readClimateData(day, filename, windSpeedDailyMaxValueName);
	}
	
	
	public void readRelativeHuminityDailyMean(int day, String filename){
		relativeHuminityDailyMean = readClimateData(day, filename, relativeHuminityDailyMeanValueName);
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
