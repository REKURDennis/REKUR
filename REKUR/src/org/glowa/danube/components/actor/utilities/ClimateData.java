package org.glowa.danube.components.actor.utilities;
/**
 * This class is a climatedata storage.
 * @author Dennis Joswig
 *
 */
public class ClimateData {
	/**
	 * Mean air temperature in kelvin.
	 */
	public float airTemperatureMean = 0f;
	/**
	 * Max air temperature in kelvin.
	 */
	public float airTemperatureMax = 0f;
	/**
	 * min air temperature in kelvin.
	 */
	public float airTemperatureMin = 400f;
	/**
	 * precipitation sum in mm^3.
	 */
	public float precipitationSum = 0f;
	/**
	 * sunshine duration sum in min.
	 */
	public float sunshineDurationSum = 0f;
	/**
	 * mean wind speed in m/s.
	 */
	public float windSpeedMean = 0f;
	/**
	 * max wind speed in m/s.
	 */
	public float windSpeedMax = 0f;
	/**
	 * relative humidity in %.
	 */
	public float relativeHumidityMean = 0f;
	/**
	 * watertemp in kelvin.
	 */
	public float watertemp = 0f;
	/**
	 * temperature humidity index in %.
	 */
	public int temperatureHumidityIndex = 0;
}
