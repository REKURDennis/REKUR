package org.glowa.danube.components.actor.touristmodel.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.glowa.danube.components.actor.touristmodel.TouristModel;

/**
 * Adds an additional GUI to turn on and of different write out options.
 * @author Dennis Joswig
 *
 */
public class GUI extends JFrame{
	
	
	public JTextField TextField;
	private TouristModel tourism;
	
	public GUI(TouristModel tourism){
		this.tourism = tourism;
		this.setSize(new Dimension(200,600));
		this.setLayout(new FlowLayout());
		this.add(TextField = new JTextField());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	public GUI(){
		this.setSize(new Dimension(200,600));
		this.setLayout(new FlowLayout());
		this.add(TextField = new JTextField());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
}
