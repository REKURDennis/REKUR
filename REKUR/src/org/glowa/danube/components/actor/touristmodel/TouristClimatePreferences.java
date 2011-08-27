package org.glowa.danube.components.actor.touristmodel;

import org.glowa.danube.components.actor.utilities.AggregatedClimateData;
/**
 * The class is a storage for the tourist type climate preferences.
 * @author Dennis Joswig	
 *
 */
public class TouristClimatePreferences extends AggregatedClimateData{
	/**
	 * The possible mean air temp deviation for this the touristtype;
	 */
	public float airMeanTempDeviation;
	/**
	 * The possible min air temp deviation for this the touristtype;
	 */
	public float airMinTempDeviation;
	/**
	 * The possible max air temp deviation for this the touristtype;
	 */
	public float airMaxTempDeviation;
	/**
	 * The possible mean precipitation deviation for this the touristtype;
	 */
	public float precipMeanDeviation;
	/**
	 * The possible max precipitation deviation for this the touristtype;
	 */
	public float precipMaxDeviation;
	/**
	 * The possible relative himidity deviation for this the touristtype;
	 */
	public float relHumDeviation;
	/**
	 * The possible THI deviation for this the touristtype;
	 */
	public int thiDevitaion;
	/**
	 * The possible sundurance deviation for this the touristtype;
	 */
	public float sundDurDeviation;
	/**
	 * The possible mean wind speed deviation for this the touristtype;
	 */
	public float windSpeedMeanDeviation;
	/**
	 * The possible max wind speed deviation for this the touristtype;
	 */
	public float windSpeedMaxDeviation;
	/**
	 * The possible mean water temp deviation for this the touristtype;
	 */
	public float waterTempDeviation;
}
