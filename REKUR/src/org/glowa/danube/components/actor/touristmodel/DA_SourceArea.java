package org.glowa.danube.components.actor.touristmodel;

import org.glowa.danube.deepactors.actors.actor.AbstractActor;


/**
 * THe class DA_SourceArea (DA = DeepActor) saves all deepactor attributes and methods.
 * @author Dennis Joswig
 *
 */
@SuppressWarnings("unchecked")
public class DA_SourceArea extends AbstractActor{
	public float numberOfCitizens;
	public String name;
	public float size;
	public float[] demography = new float[10];
	public int[] sex = new int[2];
	
	public int[][] populationPerAgeAndSex = new int [92][2];
	
	public float buyingPower;
	public DA_Tourist[] tourists;
	
	public DA_SourceArea(){
		
	}
	
	@Override
	protected void query() {
		// TODO Auto-generated method stub
		super.query();
		for(DA_Tourist t : tourists){
			//t.makeDecision();
			t.makeDecision(this.getSimulationTime().getMonth(), this.getSimulationTime().getDay());
		}
	}
}
