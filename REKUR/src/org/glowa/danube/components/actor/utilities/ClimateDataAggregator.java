package org.glowa.danube.components.actor.utilities;

import java.util.Set;
/**
 * Aggregates daily(spatial) and monthly climatedata. 
 * @author Dennis Joswig
 *
 */
public class ClimateDataAggregator {
	/**
	 * Saves the daily spatially aggregated climate data.
	 */
	public AggregatedClimateData dailyClimate = new AggregatedClimateData();
	/**
	 * saves the climatedata aggregation of the last month.
	 */
	public AggregatedClimateData lastMonthClimate = new AggregatedClimateData();
	/**
	 * saves the current month aggregation.
	 */
	private AggregatedClimateData currentMonth = new AggregatedClimateData();
	/**
	 * rainy day precipitation limit.
	 */
	public float rainyDayLimit = 0f;
	/**
	 * Current month day counter.
	 */
	private int daysInMonth = 1;
	
	/**
	 * this method aggregates daily(spatial) and monthly climatedata. 
	 * @param dp Proxelset of aggregation area.
	 * @param dayOfMonth dayOf Month.
	 */
	public void aggregateClimateData(Set<AbstractActorModelProxel> dp, int day){
		
		if(day == 1){
			currentMonth.airTemperatureMean /= daysInMonth;
			currentMonth.precipitationSum /= daysInMonth;
			currentMonth.sunshineDurationSum /= daysInMonth;
			currentMonth.windSpeedMean /= daysInMonth;
			currentMonth.relativeHumidityMean /= daysInMonth;
			currentMonth.relativeHumidityMin /= daysInMonth;
			currentMonth.temperatureHumidityIndexMonthlyMean /= daysInMonth;
	
			lastMonthClimate = currentMonth;
			currentMonth = new AggregatedClimateData();
			daysInMonth = 1;	
		}
		if(dp!=null){
			dailyClimate = new AggregatedClimateData();
			for(AbstractActorModelProxel currentProxel:dp){
				dailyClimate.airTemperatureMean += currentProxel.cd.airTemperatureMean;
				
				//System.out.println(currentProxel.cd.airTemperatureMean);
				if(dailyClimate.airTemperatureMax < currentProxel.cd.airTemperatureMax){
					dailyClimate.airTemperatureMax = currentProxel.cd.airTemperatureMax;
				}
				if(dailyClimate.airTemperatureMin > currentProxel.cd.airTemperatureMin){
					dailyClimate.airTemperatureMin = currentProxel.cd.airTemperatureMin;
				}
				dailyClimate.precipitationSum += currentProxel.cd.precipitationSum;
				if(dailyClimate.precipitationMax < currentProxel.cd.precipitationSum){
					dailyClimate.precipitationMax = currentProxel.cd.precipitationSum;
				}
				dailyClimate.sunshineDurationSum += currentProxel.cd.sunshineDurationSum;
				dailyClimate.windSpeedMean += currentProxel.cd.windSpeedMean;
				if(dailyClimate.windSpeedMax < currentProxel.cd.windSpeedMax){
					dailyClimate.windSpeedMax = currentProxel.cd.windSpeedMax;
				}
				dailyClimate.relativeHumidityMean += currentProxel.cd.relativeHumidityMean;
				dailyClimate.relativeHumidityMin += currentProxel.cd.relativeHumidityMin;
				if(dailyClimate.temperatureHumidityIndex < currentProxel.cd.temperatureHumidityIndex){
					dailyClimate.temperatureHumidityIndex = currentProxel.cd.temperatureHumidityIndex;
				}
			}
			
			dailyClimate.airTemperatureMean /= dp.size();
			dailyClimate.precipitationSum /= dp.size();
			dailyClimate.sunshineDurationSum /= dp.size();
			dailyClimate.windSpeedMean /= dp.size();
			dailyClimate.relativeHumidityMean /= dp.size();
			dailyClimate.relativeHumidityMin /= dp.size();
		}
		
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
		if(dailyClimate.precipitationMax > rainyDayLimit){
			currentMonth.precepitationDayCounter++;
		}
		
		
		currentMonth.sunshineDurationSum += dailyClimate.sunshineDurationSum;
		currentMonth.windSpeedMean += dailyClimate.windSpeedMean;
		if(currentMonth.windSpeedMax < dailyClimate.windSpeedMax){
			currentMonth.windSpeedMax = dailyClimate.windSpeedMax;
		}
		currentMonth.relativeHumidityMean += dailyClimate.relativeHumidityMean;
		currentMonth.relativeHumidityMin += dailyClimate.relativeHumidityMin;
		currentMonth.temperatureHumidityIndexMonthlyMean += dailyClimate.temperatureHumidityIndex;
		if(currentMonth.temperatureHumidityIndex<dailyClimate.temperatureHumidityIndex){
			currentMonth.temperatureHumidityIndex = dailyClimate.temperatureHumidityIndex;
		}
		daysInMonth++;
	}
}
