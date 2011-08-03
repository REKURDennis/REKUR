package org.glowa.danube.components.actor.interfaces;

import java.util.HashMap;

import org.glowa.danube.components.DanubiaInterface;
import org.glowa.danube.components.actor.utilities.ClimateData;

public interface ModelControllerToRekurTouristModel extends DanubiaInterface
{
    public HashMap<Integer, boolean[]> getHolidayTypes();
    public HashMap<Integer, Integer> getCountryIDs();
    public HashMap<Integer, ClimateData> getDailyClimateData();
	public HashMap<Integer, ClimateData> getLastMonthClimateData();
}