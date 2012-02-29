package org.glowa.danube.components.actor.interfaces;

import org.glowa.danube.tables.FloatDataTable;
import org.glowa.danube.tables.IntegerDataTable;
import org.glowa.danube.tables.MassPerAreaTable;
import org.glowa.danube.tables.TemperatureTable;

/**
 * This interface provides Methods for ClimateData export.
 * @author Dennis Joswig
 *
 */
public interface ModelControllerProvideClimate {
	/**
	 * Method to get the daily mean air temperature.
	 * @return A TemperatureTable with the REKUR-resolution.
	 */
	public TemperatureTable getAirTemperatureDailyMean();
	/**
	 * Method to get the daily max air temperature.
	 * @return A TemperatureTable with the REKUR-resolution.
	 */
	public TemperatureTable getAirTemperatureDailyMax();
	/**
	 * Method to get the daily min air temperature.
	 * @return A TemperatureTable with the REKUR-resolution.
	 */
	public TemperatureTable getAirTemperatureDailyMin();
	/**
	 * Method to get the daily precipitation sum.
	 * @return A MassPerAreaTable with the REKUR-resolution.
	 */
	public MassPerAreaTable getPrecipitationDailySum();
	/**
	 * Method to get the daily sunshine duration sum.
	 * @return A FloatDataTable with the REKUR-resolution.
	 */
	public FloatDataTable getSunshineDurationDailySum();
	/**
	 * Method to get the daily mean windspeed.
	 * @return A FloatDataTable with the REKUR-resolution.
	 */
	public FloatDataTable getWindSpeedDailyMean();
	/**
	 * Method to get the daily max windspeed.
	 * @return A FloatDataTable with the REKUR-resolution.
	 */
	public FloatDataTable getWindSpeedDailyMax();
	/**
	 * Method to get the daily mean relative humidity.
	 * @return A FloatDataTable with the REKUR-resolution.
	 */
	public FloatDataTable getRelativeHumidityDailyMean();
	/**
	 * Method to get the daily max temperature humidity index.
	 * @return A IntegerDataTable with the REKUR-resolution.
	 */
	public IntegerDataTable getTempHumidityIndex();
	/**
	 * Method to get the daily temperature climate index.
	 * @return A IntegerDataTable with the REKUR-resolution.
	 */
	public IntegerDataTable getTourismClimateIndex();
}
