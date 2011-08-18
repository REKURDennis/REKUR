package org.glowa.danube.components.actor.utilities;
/**
 * Storage for 2d integerarrays, necessary for HashMaps.
 * @author Dennis Joswig
 *
 */
public class IntegerArray2D {
	/**
	 * The 2d array.
	 */
	public int[][] array;
	/**
	 * Constructor to init the array
	 * @param x First-Length
	 * @param y Second-Length
	 */
	public IntegerArray2D(int x, int y){
		array = new int[x][y];
	}
}
