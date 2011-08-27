package org.glowa.danube.components.actor.utilities;
/**
 * This class can save aggregated climatedata.
 * @author Dennis Joswig
 * @see ClimateData
 */
public class AggregatedClimateData extends ClimateData{
	/**
	 * Max precipitation of the aggregated data.
	 */
	public float precipitationMax = 0f;
	/**
	 * Number of days with precipitation.
	 */
	public int precepitationDayCounter = 0;
	/**
	 * Monthly mean temperature humidity index.
	 */
	public int temperatureHumidityIndexMonthlyMean = 0;
	/**
	 * Monthly mean water temperature.
	 */
	public int waterTemp = 0;
}
