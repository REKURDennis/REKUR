package org.glowa.danube.components.actor.interfaces;

import java.util.HashMap;

import org.glowa.danube.components.DanubiaInterface;
/**
 * ImportInterface for the DestinationModel.
 * @author Dennis Joswig
 * @see ModelControllerProvideClimate
 */
public interface ModelControllerToRekurDestinationModel extends DanubiaInterface, ModelControllerProvideClimate{
	/**
	 * Provides the Tourists per detsiantion, Year, week, category and sourcearea. 
	 * @return HashMap<destinationId, HashMap<year, HashMap<week, HashMap<category, HashMap<sourceareaID, Integer>>>>>
	 */
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>>>>>>> getNumberOfTourists();
}
