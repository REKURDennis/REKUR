package org.glowa.danube.components.actor.destinationModel;

import org.glowa.danube.components.actor.utilities.ClimateDataAggregator;
import org.glowa.danube.deepactors.actors.actor.AbstractActor;

/**
 * This class provides all methods and attributes for the destination-deepactors.
 * @author Dennis Joswig
 *
 */
public class DD_Destination extends AbstractActor{
	/**
	 * Saves an array with the availability of holiday types.
	 */
	public boolean[] holidayTypes;
	/**
	 * Saves the country, the destination is playced in.
	 */
	public int country;
	/**
	 * Object for climatedata aggregation.
	 */
	public ClimateDataAggregator ca= new ClimateDataAggregator();
	@Override
	protected void options(){
		ca.aggregateClimateData(getProxel(), getSimulationTime().getDay());
	}
}
