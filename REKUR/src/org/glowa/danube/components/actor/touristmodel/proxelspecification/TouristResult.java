package org.glowa.danube.components.actor.touristmodel.proxelspecification;

/*
 * TourismResult.java
 *
 *
 * Letzte Aenderung  24.7.2003 Alex Dingeldey
 */


import org.glowa.danube.components.DanubiaInterface;
import org.glowa.danube.tables.LongDataTable;
import org.glowa.danube.tables.SkiingAreaStatusTypeTable;
import org.glowa.danube.tables.WaterFluxTable;




/**Danubia Result Interface 
 * 
 * @author Dr. Alexander Dingedey alex@dingel.org, Dennis Joswig
 *  
 * 
 */
public interface TouristResult  extends DanubiaInterface{
		

    /**
     * getTourismDrinkingWaterDemand() returns the water demand
     * @return Drinking water demand
     */
    public WaterFluxTable getTourismDrinkingWaterDemand();
    
    
}

//class

