package org.glowa.danube.components.actor.touristmodel;

import java.util.Vector;

public class DA_AbstractTouristType {
	public Vector<Integer> holidaytypes= new Vector<Integer>();
	public Vector<Integer> activityTypes;
	public Vector<Integer> countries= new Vector<Integer>();
	public int maxTemp;
	public int minTemp;
	public int avgTemp;
	public Vector<Integer> preferedWeeks = new Vector<Integer>();
	public int holidayLength;
	public int category;
	public int bookingMonth;
	public int bookingDay;
	
	
	public void makeDecision(int year, int month, int day, DA_Tourist delegate){
	}
	
	public void query(int year, int month, int day, DA_Tourist delegate){
		makeDecision(year, month, day, delegate);
	}

	public void init(int year, int month, int day, DA_Tourist delegate) {
		// TODO Auto-generated method stub
		
	}

	public void options(int year, int month, int day, DA_Tourist delegate) {
		// TODO Auto-generated method stub
		
	}

	public void export(int year, int month, int day, DA_Tourist delegate) {
		// TODO Auto-generated method stub
		
	}
	
}
