package org.glowa.danube.components.actor.destinationModel;

import java.util.Set;

import org.glowa.danube.components.actor.utilities.AggregatedClimateData;
import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.components.actor.utilities.ClimateDataAggregator;
import org.glowa.danube.deepactors.actors.actor.AbstractActor;

public class DD_Destination extends AbstractActor{
	public boolean[] holidayTypes;
	public int country;
	
	public ClimateDataAggregator ca= new ClimateDataAggregator();
	protected void options(){
		ca.aggregateClimateData(getProxel(), getSimulationTime().getDay());
	}
}
