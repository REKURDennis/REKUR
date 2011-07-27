package org.glowa.danube.components.actor.interfaces;

import java.util.HashMap;

import org.glowa.danube.components.DanubiaInterface;
import org.glowa.danube.components.actor.utilities.ClimateData;

public interface RekurDestinationModelToActorController extends DanubiaInterface
{
	//Climate related variables
	
	//Destination specific variables
	public HashMap<Integer, boolean[]> getHolidayTypes();
	public HashMap<Integer, Integer> getCountryIDs();     
	public HashMap<Integer, ClimateData> getDailyClimateData();
	public HashMap<Integer, ClimateData> getLastMonthClimateData();
}
