package org.glowa.danube.components.actor.utilities;

import java.util.HashMap;
import java.util.Vector;

import org.glowa.danube.components.actor.touristmodel.DA_Tourist;
/**
 * This class is a storage for journeys
 * @author Dennis Joswig
 *
 */
public class Journey {
	/**
	 * Weeks per Year <Year, Vector<WeekOfYear>>.
	 */
	public HashMap<Integer, Vector<Integer>> weeks;
	/**
	 * Saves the DestiantionID.
	 */
	public int destID;
	/**
	 * Saves the category of the destination.
	 */
	public int category;
	/**
	 * Saves the traveling tourist.
	 */
	public DA_Tourist tourist;
	/**
	 * Saves the Id of the tourists origin.
	 */
	public int SourceAreaID;
	/**
	 * The constructor of the class generates a new storage object.
	 * @param weeks Weeks per Year <Year, Vector<WeekOfYear>>.
	 * @param destID Saves the DestiantionID.
	 * @param category Saves the category of the destination.
	 * @param SourceAreaID Saves the Id of the tourists origin.
	 * @param tourist Saves the traveling tourist.
	 */
	public Journey(HashMap<Integer, Vector<Integer>> weeks, int destID, int category, int SourceAreaID, DA_Tourist tourist){
		this.weeks = weeks;
		this.destID = destID;
		this.category = category;
		this.tourist = tourist;
		this.SourceAreaID = SourceAreaID;
	}
}
