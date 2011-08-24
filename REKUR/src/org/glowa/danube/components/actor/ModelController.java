package org.glowa.danube.components.actor;

import java.util.HashMap;

import org.glowa.danube.components.actor.interfaces.*;
import org.glowa.danube.components.actor.utilities.ClimateData;
import org.glowa.danube.simulation.controller.AbstractController;
import org.glowa.danube.tables.FloatDataTable;
import org.glowa.danube.tables.IntegerDataTable;
import org.glowa.danube.tables.MassPerAreaTable;
import org.glowa.danube.tables.TemperatureTable;

/**
 * The ModelController implements all import interfaces and provides all export methods.
 * @author Dennis Joswig
 *
 */
public class ModelController extends AbstractController implements ModelControllerToRekurDestinationModel, 
	ModelControllerToRekurTouristModel, ModelControllerToRekurHydrologicalModel{
	/**
	 * Object to import TouristModel-Values.
	 */
	private RekurTouristModelToModelController tourist = null;
	/**
	 * Object to import ClimateModel-Values.
	 */
	private RekurClimateModelToModelController climate = null;
	/**
	 * Object to import DestinationModel-Values.
	 */
	private RekurDestinationModelToModelController destination = null;
	/**
	 * Object to import HydrologicalModel-Values.
	 */
	private RekurHydrologicalModelToModelController hydrological = null;
	
	/**
	 * Inits the Interfaceconnections
	 */
	
	protected void init() throws RuntimeException {
		// -- internal interfaces --------------------------------------------------
		try {
			tourist = (RekurTouristModelToModelController) getImport("org.glowa.danube.components.actor.interfaces.RekurTouristModelToModelController");
		} catch (Exception ex) {
			this.logger().warn(ex);
		}
		try {
			climate = (RekurClimateModelToModelController) getImport("org.glowa.danube.components.actor.interfaces.RekurClimateModelToModelController");
		} catch (Exception ex) {
			this.logger().warn(ex);
		}
		try {
			destination = (RekurDestinationModelToModelController) getImport("org.glowa.danube.components.actor.interfaces.RekurDestinationModelToModelController");
		} catch (Exception ex) {
			this.logger().warn(ex);
		}
		try {
			hydrological = (RekurHydrologicalModelToModelController) getImport("org.glowa.danube.components.actor.interfaces.RekurHydrologicalModelToModelController");
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
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>>>> getNumberOfTourists() {
		// TODO Auto-generated method stub
		return tourist.getNumberOfTourists();
	}

	
//	ClimateModel to Other Models (Tourist and Destination) interfacemethods
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
	public FloatDataTable getRelativeHumidityDailyMean() {
		// TODO Auto-generated method stub
		return climate.getRelativeHumidityDailyMean();
	}
	
	@Override
	public IntegerDataTable getTempHumidityIndex() {
		// TODO Auto-generated method stub
		return climate.getTempHumidityIndex();
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
