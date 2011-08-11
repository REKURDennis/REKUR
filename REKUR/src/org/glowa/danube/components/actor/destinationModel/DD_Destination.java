package org.glowa.danube.components.actor.destinationModel;

import java.util.Set;

import org.glowa.danube.components.actor.utilities.AggregatedClimateData;
import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.components.actor.utilities.ClimateDataAggregator;
import org.glowa.danube.deepactors.actors.actor.AbstractActor;

public class DD_Destination extends AbstractActor{
	public boolean[] holidayTypes;
	public int country;
	
//	public AggregatedClimateData dailyClimate = new AggregatedClimateData();
//	public AggregatedClimateData lastMonthClimate = new AggregatedClimateData();
//	private AggregatedClimateData currentMonth = new AggregatedClimateData();
//	public float rainyDayPreBorder = 0f;
//	private int daysInMonth = 1;
	
	public ClimateDataAggregator ca= new ClimateDataAggregator();
	protected void options(){
		ca.aggregateClimateData(getProxel(), getSimulationTime().getDay());
	}
	
//	private void aggregateClimateData(){
//		Set<DestinationProxel> dp = getProxel();
//		
//		if(getSimulationTime().getDay() == 1){
//			currentMonth.airTemperatureMean /= daysInMonth;
//			currentMonth.precipitationSum /= daysInMonth;
//			currentMonth.sunshineDurationSum /= daysInMonth;
//			currentMonth.windSpeedMean /= daysInMonth;
//			currentMonth.relativeHumidityMean /= daysInMonth;
//			currentMonth.temperatureHumidityIndexMonthlyMean /= daysInMonth;
//	
//			lastMonthClimate = currentMonth;
//			currentMonth = new AggregatedClimateData();
//			daysInMonth = 1;	
//		}
//		for(DestinationProxel currentProxel:dp){
//			dailyClimate.airTemperatureMean += currentProxel.cd.airTemperatureMean;
//			if(dailyClimate.airTemperatureMax < currentProxel.cd.airTemperatureMax){
//				dailyClimate.airTemperatureMax = currentProxel.cd.airTemperatureMax;
//			}
//			if(dailyClimate.airTemperatureMin > currentProxel.cd.airTemperatureMin){
//				dailyClimate.airTemperatureMin = currentProxel.cd.airTemperatureMin;
//			}
//			dailyClimate.precipitationSum += currentProxel.cd.precipitationSum;
//			if(dailyClimate.precipitationMax < currentProxel.cd.precipitationSum){
//				dailyClimate.precipitationMax = currentProxel.cd.precipitationSum;
//			}
//			dailyClimate.sunshineDurationSum += currentProxel.cd.sunshineDurationSum;
//			dailyClimate.windSpeedMean += currentProxel.cd.windSpeedMean;
//			if(dailyClimate.windSpeedMax < currentProxel.cd.windSpeedMax){
//				dailyClimate.windSpeedMax = currentProxel.cd.windSpeedMax;
//			}
//			dailyClimate.relativeHumidityMean += currentProxel.cd.relativeHumidityMean;
//			if(dailyClimate.temperatureHumidityIndex < currentProxel.cd.temperatureHumidityIndex){
//				dailyClimate.temperatureHumidityIndex = currentProxel.cd.temperatureHumidityIndex;
//			}
//		}
//		
//		dailyClimate.airTemperatureMean /= dp.size();
//		dailyClimate.precipitationSum /= dp.size();
//		dailyClimate.sunshineDurationSum /= dp.size();
//		dailyClimate.windSpeedMean /= dp.size();
//		dailyClimate.relativeHumidityMean /= dp.size();
//		
//		
//		currentMonth.airTemperatureMean += dailyClimate.airTemperatureMean;
//		if(currentMonth.airTemperatureMax < dailyClimate.airTemperatureMax){
//			currentMonth.airTemperatureMax = dailyClimate.airTemperatureMax;
//		}
//		if(currentMonth.airTemperatureMin > dailyClimate.airTemperatureMin){
//			currentMonth.airTemperatureMin = dailyClimate.airTemperatureMin;
//		}
//		currentMonth.precipitationSum += dailyClimate.precipitationSum;
//		if(currentMonth.precipitationMax < dailyClimate.precipitationMax){
//			currentMonth.precipitationMax = dailyClimate.precipitationMax;
//		}
//		if(dailyClimate.precipitationMax > rainyDayPreBorder){
//			currentMonth.precepitationDayCounter++;
//		}
//		
//		
//		currentMonth.sunshineDurationSum += dailyClimate.sunshineDurationSum;
//		currentMonth.windSpeedMean += dailyClimate.windSpeedMean;
//		if(currentMonth.windSpeedMax < dailyClimate.windSpeedMax){
//			currentMonth.windSpeedMax = dailyClimate.windSpeedMax;
//		}
//		currentMonth.relativeHumidityMean += dailyClimate.relativeHumidityMean;
//		currentMonth.temperatureHumidityIndexMonthlyMean += dailyClimate.temperatureHumidityIndex;
//		if(currentMonth.temperatureHumidityIndex<dailyClimate.temperatureHumidityIndex){
//			currentMonth.temperatureHumidityIndex = dailyClimate.temperatureHumidityIndex;
//		}
//		daysInMonth++;
//	}
	
}
