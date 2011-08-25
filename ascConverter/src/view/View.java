package view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;


public class View extends JFrame{
	private static final long serialVersionUID = 1L;
	public JButton loadAscii;
	public JButton saveCSV;
	public JButton convertASCIItoCSV;
	
	public JTextField actorClass;
	public JTextField collaborators;
	public JTextField initialPlans;
	
	public JPanel actorPanel;
	public JPanel collaPanel;
	public JPanel initPanel;
	
	public JLabel actorLabel;
	public JLabel collaLabel;
	public JLabel initLabel;
	
	public JLabel status;
	
	public Controller c;
	
	public View(){
		super("ASC to CSV Converter");
		c = new Controller(this);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(700,300);
		this.loadAscii = new JButton("ASCII laden");
		this.saveCSV = new JButton("Ausgabedatei waehlen");
		this.convertASCIItoCSV = new JButton("Konvertieren und speichern");
		
		actorPanel = new JPanel();
		collaPanel = new JPanel();
		initPanel = new JPanel();
		
		actorLabel = new JLabel("Actor-class");
		collaLabel = new JLabel("CollaboratorIDs");
		initLabel = new JLabel("PlanIDs");
		
		
		this.actorClass = new JTextField(50);
		actorClass.setText("org.glowa.danube.components.actor.destinationModel.deepactors.DD_Destination");
		this.collaborators = new JTextField(45);
		collaborators.setText("");
		this.initialPlans = new JTextField(50);
		initialPlans.setText("1001");
		
		this.status = new JLabel("Status: Eingabedatei waehlen");
		
		
		this.add(loadAscii);
		this.add(saveCSV);
		saveCSV.setEnabled(false);
		this.add(convertASCIItoCSV);
		convertASCIItoCSV.setEnabled(false);
		
		actorPanel.add(actorLabel);
		actorPanel.add(actorClass);
		collaPanel.add(collaLabel);
		collaPanel.add(collaborators);
		initPanel.add(initLabel);
		initPanel.add(initialPlans);
		
		this.add(actorPanel);
		this.add(collaPanel);
		this.add(initPanel);
		
		this.add(status);
		saveCSV.addActionListener(c);
		loadAscii.addActionListener(c);
		convertASCIItoCSV.addActionListener(c);
		
		//this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new View();
	}
	
//	class waechter extends ComponentAdapter{
//		public void componentResized(ComponentEvent e){
//		}
//	}
}
