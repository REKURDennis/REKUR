package org.glowa.danube.components.actor.touristmodel;

public interface DA_TouristTypeInterface {
	public void init();
	public void query();
	public void options();
	public void makeDecision(int month, int day);
	public void export();
}
