package org.glowa.danube.components.actor.interfaces;

import java.util.HashMap;

import org.glowa.danube.components.DanubiaInterface;
/**
 * ImportInterface for the DestinationModel.
 * @author Dennis Joswig
 * @see ModelControllerProvideClimate
 */
public interface ModelControllerToRekurDestinationModel extends DanubiaInterface, ModelControllerProvideClimate{
	/*
	 * TouristModel to DestinationModel
	 */
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>>>> getNumberOfTourists();
}
