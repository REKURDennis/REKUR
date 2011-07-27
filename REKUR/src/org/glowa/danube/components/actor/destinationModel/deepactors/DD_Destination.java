package org.glowa.danube.components.actor.destinationModel.deepactors;

import java.util.Set;

import org.glowa.danube.components.actor.destinationModel.proxelspecifications.DestinationProxel;
import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.deepactors.actors.actor.AbstractActor;

public class DD_Destination extends AbstractActor{
	public boolean[] holidayTypes;
	public int country;
	
	public ClimateData dailyClimate = new ClimateData();
	public ClimateData lastMonthClimate = new ClimateData();
	private ClimateData currentMonth = new ClimateData();
	public float rainyDayPreBorder = 0f;
	private int daysInMonth = 1;
	
	
	protected void options(){
		aggregateClimateData();
	}
	
	
	private void aggregateClimateData(){
		Set<DestinationProxel> dp = getProxel();
		
		if(getSimulationTime().getDay() == 1){
			currentMonth.airTemperatureMean /= daysInMonth;
			currentMonth.precipitationSum /= daysInMonth;
			currentMonth.sunshineDurationSum /= daysInMonth;
			currentMonth.windSpeedMean /= daysInMonth;
			currentMonth.relativeHuminityMean /= daysInMonth;
			
			lastMonthClimate = currentMonth;
			currentMonth = new ClimateData();
			daysInMonth = 1;
			
		}
		
		
		
		for(DestinationProxel currentProxel:dp){
			dailyClimate.airTemperatureMean += currentProxel.airTemperatureDailyMean;
			if(dailyClimate.airTemperatureMax < currentProxel.airTemperatureDailyMax){
				dailyClimate.airTemperatureMax = currentProxel.airTemperatureDailyMin;
			}
			if(dailyClimate.airTemperatureMin > currentProxel.airTemperatureDailyMin){
				dailyClimate.airTemperatureMin = currentProxel.airTemperatureDailyMin;
			}
			dailyClimate.precipitationSum += currentProxel.precipitationDailySum;
			if(dailyClimate.precipitationMax < currentProxel.precipitationDailySum){
				dailyClimate.precipitationMax = currentProxel.precipitationDailySum;
			}
			dailyClimate.sunshineDurationSum += currentProxel.sunshineDurationDailySum;
			dailyClimate.windSpeedMean += currentProxel.windSpeedDailyMean;
			if(dailyClimate.windSpeedMax < currentProxel.windSpeedDailyMax){
				dailyClimate.windSpeedMax = currentProxel.windSpeedDailyMax;
			}
			dailyClimate.relativeHuminityMean += currentProxel.relativeHuminityDailyMean;
		}
		
		dailyClimate.airTemperatureMean /= dp.size();
		dailyClimate.precipitationSum /= dp.size();
		dailyClimate.sunshineDurationSum /= dp.size();
		dailyClimate.windSpeedMean /= dp.size();
		dailyClimate.relativeHuminityMean /= dp.size();
		
		
		currentMonth.airTemperatureMean += dailyClimate.airTemperatureMean;
		if(currentMonth.airTemperatureMax < dailyClimate.airTemperatureMax){
			currentMonth.airTemperatureMax = dailyClimate.airTemperatureMax;
		}
		if(currentMonth.airTemperatureMin > dailyClimate.airTemperatureMin){
			currentMonth.airTemperatureMin = dailyClimate.airTemperatureMin;
		}
		currentMonth.precipitationSum += dailyClimate.precipitationSum;
		if(currentMonth.precipitationMax < dailyClimate.precipitationMax){
			currentMonth.precipitationMax = dailyClimate.precipitationMax;
		}
		if(dailyClimate.precipitationMax > rainyDayPreBorder){
			currentMonth.precepitationDayCounter++;
		}
		
		
		currentMonth.sunshineDurationSum += dailyClimate.sunshineDurationSum;
		currentMonth.windSpeedMean += dailyClimate.windSpeedMean;
		if(currentMonth.windSpeedMax < dailyClimate.windSpeedMax){
			currentMonth.windSpeedMax = dailyClimate.windSpeedMax;
		}
		currentMonth.relativeHuminityMean += dailyClimate.relativeHuminityMean;
		daysInMonth++;
	}
	
}
