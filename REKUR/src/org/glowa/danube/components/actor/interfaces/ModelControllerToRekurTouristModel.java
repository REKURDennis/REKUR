package org.glowa.danube.components.actor.interfaces;

import java.util.HashMap;

import org.glowa.danube.components.DanubiaInterface;
import org.glowa.danube.components.actor.utilities.ClimateData;
/**
 * ImportInterface for the TouristModel.
 * @author Dennis Joswig
 * @see ModelControllerProvideClimate
 */
public interface ModelControllerToRekurTouristModel extends DanubiaInterface, ModelControllerProvideClimate
{
	/**
	 * Method to get the holidaytypes of each destination.
	 * @return HashMap with the destinations-ids as key and their holiday types.
	 */
    public HashMap<Integer, boolean[]> getHolidayTypes();
    /**
     *  Method to get the country-ids of each destination.
     * @return HashMap with the destinations-ids as key and their country-ids.
     */
    public HashMap<Integer, Integer> getCountryIDs();
    /**
     *  Method to get the daily climate data of each destination.
     * @return HashMap with the destinations-ids as key and their daily climatedata.
     */
    public HashMap<Integer, ClimateData> getDailyClimateData();
    /**
     *  Method to get the last month climate data of each destination.
     * @return HashMap with the destinations-ids as key and their last month climate data.
     */
	public HashMap<Integer, ClimateData> getLastMonthClimateData();
}