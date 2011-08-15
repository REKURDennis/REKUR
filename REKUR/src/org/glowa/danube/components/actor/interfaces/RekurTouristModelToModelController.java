package org.glowa.danube.components.actor.interfaces;

import org.glowa.danube.components.DanubiaInterface;
/**
 * Export-Interface of the TouristModel
 * @author Dennis Joswig
 *
 */
public interface RekurTouristModelToModelController extends DanubiaInterface
{
  public int[][][] getNumberOfTourists();
}
