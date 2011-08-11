package org.glowa.danube.components.actor.interfaces;

import org.glowa.danube.components.DanubiaInterface;

public interface ModelControllerToRekurDestinationModel extends DanubiaInterface, ModelControllerProvideClimate{
	/*
	 * TouristModel to DestinationModel
	 */
	public int[][][] getNumberOfTourists();
}
