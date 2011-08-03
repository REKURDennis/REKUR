package org.glowa.danube.components.actor.interfaces;

import org.glowa.danube.components.DanubiaInterface;
import org.glowa.danube.tables.FloatDataTable;
import org.glowa.danube.tables.MassPerAreaTable;
import org.glowa.danube.tables.TemperatureTable;

public interface RekurClimateModelToModelController extends DanubiaInterface
{
	public TemperatureTable getAirTemperatureDailyMean();
	public TemperatureTable getAirTemperatureDailyMax();
	public TemperatureTable getAirTemperatureDailyMin();
	public MassPerAreaTable getPrecipitationDailySum();
	public FloatDataTable getSunshineDurationDailySum();
	public FloatDataTable getWindSpeedDailyMean();
	public FloatDataTable getWindSpeedDailyMax();
	public FloatDataTable getRelativeHuminityDailyMean();
}