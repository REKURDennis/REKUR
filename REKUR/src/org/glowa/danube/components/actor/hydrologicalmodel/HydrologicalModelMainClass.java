package org.glowa.danube.components.actor.hydrologicalmodel;



import org.glowa.danube.components.actor.interfaces.RekurHydrologicalModelToModelController;
import org.glowa.danube.simulation.model.AbstractModel;
import org.glowa.danube.utilities.time.DanubiaCalendar;





/**
 * @author Dennis Joswig
 * @version 30. Juni 2010
 * 
 */
public class HydrologicalModelMainClass extends AbstractModel<HydrologicalProxel> implements RekurHydrologicalModelToModelController

{
	// PIDs for computation
	// Atmosphere to Actor

	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.component.AbstractComponent#init()
	 */
	@Override
	protected void init() {
		//Utilities.IGNORE_RAS_TIMESTAMPS = true;
		System.out.println("init hydro dummies");
    	//this.provide(this.simulationStart());

	}

	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.component.AbstractComponent#finish()
	 */
	@Override
	public void finish() {
		// Free export tables
		
	}

		
	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.model.AbstractModel#getData(org.glowa.danube.utilities.time.DanubiaCalendar)
	 */
	@Override
	public void getData(DanubiaCalendar actTime) {
	
	}

	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.model.AbstractModel#compute(org.glowa.danube.utilities.time.DanubiaCalendar)
	 */
	@Override
	public void compute(DanubiaCalendar actTime) {
	}

	/* (non-Javadoc)
	 * @see org.glowa.danube.simulation.model.AbstractModel#provide(org.glowa.danube.utilities.time.DanubiaCalendar)
	 */
	@Override
	public void provide(DanubiaCalendar t) {
		
	}
}
