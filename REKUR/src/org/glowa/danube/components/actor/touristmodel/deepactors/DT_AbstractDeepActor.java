package org.glowa.danube.components.actor.touristmodel.deepactors;


import org.glowa.danube.deepactors.actors.actor.AbstractActor;
import org.glowa.danube.utilities.internal.DanubiaLogger;
import org.glowa.danube.components.actor.touristmodel.TouristModel;
import org.glowa.danube.components.actor.touristmodel.proxelspecification.TouristProxel;

/**
 * This class  implements the main variables, options, calculation methods and output methods for every DeepActor.
 * 
 * @author Dennis Joswig
 */
public abstract class DT_AbstractDeepActor extends AbstractActor


{
	private static DanubiaLogger logger = DanubiaLogger.getDanubiaLogger(TouristModel.class);
	

	
	public void setGmt(int gmt)
	{
	}
	


	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.actors.actor.AbstractActor#init()
	 */
	protected void init()
	{	
		int[] pids = getLocation().getPIDArray();

	}
	
	/* (non-Javadoc)
	 * @see org.glowa.danube.deepactors.actors.actor.AbstractActor#options()
	 */
	protected void options()
	{	//Monatszaehler wird zum Monatsersten resettet
		if (this.simulationTime().getDay() == 1)
		{
			
			
			if (this.simulationTime().getMonth() == 1)
			{
			}
		}
	}
	

		
	
	public void preCompute(TouristProxel p)
	{
		
	
	}

	
	
	
	
	/**
	 * Initializes the data from proxel
	 * @param p	Specific proxel
	 */
	public void initDataFromProxels(TouristProxel p)
	{
	}
	
	
}