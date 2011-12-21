package org.glowa.danube.components.actor.utilities;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public JLabel touristScenario;
	public JLabel holidayScenario;
	public JLabel destinationScenario;
	
	
	public GUI(){
		super("REKUR");
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(700,300);
		
		
		this.touristScenario = new JLabel("TouristModel-Szenario: ");
		this.holidayScenario = new JLabel("HolidayModel-Szenario: ");
		this.destinationScenario = new JLabel("DestinationModel-Szenario: ");
		
		
		
		this.add(touristScenario);
		this.add(holidayScenario);
		this.add(destinationScenario);
		this.setVisible(true);
	}
}
