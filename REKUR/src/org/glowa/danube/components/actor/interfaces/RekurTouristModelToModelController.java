package org.glowa.danube.components.actor.interfaces;

import java.util.HashMap;

import org.glowa.danube.components.DanubiaInterface;
/**
 * Export-Interface of the TouristModel
 * @author Dennis Joswig
 *
 */
public interface RekurTouristModelToModelController extends DanubiaInterface
{
  public HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>>>> getNumberOfTourists();
}
