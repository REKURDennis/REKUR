package org.glowa.danube.components.actor.utilities;

public class RekurUtil {
	/**
	 * 
	 * Changes a dot to comma in a float value
	 * @param f Number
	 * @return String with comma
	 */
	public static String dotToComma(float f){
		String back = new String();
		back = ""+f;
		back = back.replace(".", ",");
		return back; 
	}

	/**
	 * 
	 *  Changes a dot to comma in a double value
	 * @param d Number
	 * @return String with comma
	 */
	public static String dotToComma(double d){
		String back = new String();
		back = ""+d;
		back = back.replace(".", ",");
		return back; 
	}

}
