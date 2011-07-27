package org.glowa.danube.components.actor;

import java.util.HashMap;

import org.glowa.danube.components.actor.interfaces.*;
import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.simulation.controller.AbstractController;
import org.glowa.danube.tables.FloatDataTable;
import org.glowa.danube.tables.MassPerAreaTable;
import org.glowa.danube.tables.TemperatureTable;

public class ActorController extends AbstractController implements ActorControllerToRekurDestinationModel, 
	ActorControllerToRekurTouristModel, ActorControllerToRekurHydrologicalModel{
	
	private RekurTouristModelToActorController tourist = null;
	private RekurClimateModelToActorController climate = null;
	private RekurDestinationModelToActorController destination = null;
	private RekurHydrologicalModelToActorController hydrological = null;
	
	/**
	 * Inits the Interfaceconnections
	 */
	
	protected void init() throws RuntimeException {
		// -- internal interfaces --------------------------------------------------
		try {
			tourist = (RekurTouristModelToActorController) getImport("org.glowa.danube.components.actor.interfaces.RekurTouristModelToActorController");
		} catch (Exception ex) {
			this.logger().warn(ex);
		}
		try {
			climate = (RekurClimateModelToActorController) getImport("org.glowa.danube.components.actor.interfaces.RekurClimateModelToActorController");
		} catch (Exception ex) {
			this.logger().warn(ex);
		}
		try {
			destination = (RekurDestinationModelToActorController) getImport("org.glowa.danube.components.actor.interfaces.RekurDestinationModelToActorController");
		} catch (Exception ex) {
			this.logger().warn(ex);
		}
		try {
			hydrological = (RekurHydrologicalModelToActorController) getImport("org.glowa.danube.components.actor.interfaces.RekurHydrologicalModelToActorController");
		} catch (Exception ex) {
			this.logger().warn(ex);
		}
	}
	
	/**
	 * Cleans up references in case of abort.
	 */
	protected void processAbort() {
	}
	
//	Touristmodel to DestinationModel interfacemethods
	@Override
	public int[][][] getNumberOfTourists() {
		// TODO Auto-generated method stub
		return tourist.getNumberOfTourists();
	}

	
//	ClimateModel to DestinationModel interfacemethods
	@Override
	public TemperatureTable getAirTemperatureDailyMean() {
		// TODO Auto-generated method stub
		return climate.getAirTemperatureDailyMean();
	}

	@Override
	public TemperatureTable getAirTemperatureDailyMax() {
		// TODO Auto-generated method stub
		return climate.getAirTemperatureDailyMax();
	}

	@Override
	public TemperatureTable getAirTemperatureDailyMin() {
		// TODO Auto-generated method stub
		return climate.getAirTemperatureDailyMin();
	}

	@Override
	public MassPerAreaTable getPrecipitationDailySum() {
		// TODO Auto-generated method stub
		return climate.getPrecipitationDailySum();
	}

	@Override
	public FloatDataTable getSunshineDurationDailySum() {
		// TODO Auto-generated method stub
		return climate.getSunshineDurationDailySum();
	}

	@Override
	public FloatDataTable getWindSpeedDailyMean() {
		// TODO Auto-generated method stub
		return climate.getWindSpeedDailyMean();
	}

	@Override
	public FloatDataTable getWindSpeedDailyMax() {
		// TODO Auto-generated method stub
		return climate.getWindSpeedDailyMax();
	}

	@Override
	public FloatDataTable getRelativeHuminityDailyMean() {
		// TODO Auto-generated method stub
		return climate.getRelativeHuminityDailyMean();
	}

	
//	Destinationmodel to TouristModel interfaceMethods
	@Override
	public HashMap<Integer, ClimateData> getDailyClimateData() {
		// TODO Auto-generated method stub
		return destination.getDailyClimateData();
	}

	@Override
	public HashMap<Integer, ClimateData> getLastMonthClimateData() {
		// TODO Auto-generated method stub
		return destination.getLastMonthClimateData();
	}
	
	@Override
	public HashMap<Integer, boolean[]> getHolidayTypes() {
		// TODO Auto-generated method stub
		return destination.getHolidayTypes();
	}

	@Override
	public HashMap<Integer, Integer> getCountryIDs() {
		// TODO Auto-generated method stub
		return destination.getCountryIDs();
	}
}
