package org.glowa.danube.components.actor.interfaces;

import org.glowa.danube.tables.FloatDataTable;
import org.glowa.danube.tables.IntegerDataTable;
import org.glowa.danube.tables.MassPerAreaTable;
import org.glowa.danube.tables.TemperatureTable;

public interface ModelControllerProvideClimate {
	/*
	 * ClimateModel to other Models
	 */
	public TemperatureTable getAirTemperatureDailyMean();
	public TemperatureTable getAirTemperatureDailyMax();
	public TemperatureTable getAirTemperatureDailyMin();
	public MassPerAreaTable getPrecipitationDailySum();
	public FloatDataTable getSunshineDurationDailySum();
	public FloatDataTable getWindSpeedDailyMean();
	public FloatDataTable getWindSpeedDailyMax();
	public FloatDataTable getRelativeHumidityDailyMean();
	public IntegerDataTable getTempHumidityIndex();
}
