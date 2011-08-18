package org.glowa.danube.components.actor.utilities;

import org.glowa.danube.simulation.model.proxel.AbstractProxel;
/**
 * AbstractProxel for the two actormodels.
 * @author Dennis Joswig
 *
 */
public abstract class AbstractActorModelProxel extends AbstractProxel {
	/**
	 * Saves the daily climatedata.
	 */
	public ClimateData cd = new ClimateData();

	public static final long serialVersionUID = 1;
}
