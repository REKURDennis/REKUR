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
	
	public JButton choseFile;
	public JButton choseFolder;
	public JLabel status;
	public JButton readIn;
	public JLabel relLabel;
	public JPanel relPanel;
	public JTextField relField;
	
	public JLabel dbLabel;
	public JPanel dbPanel;
	public JTextField dbField;

	public JLabel userLabel;
	public JPanel userPanel;
	public JTextField userField;
	
	public JLabel pwLabel;
	public JPanel pwPanel;
	public JTextField pwField;
	
	public Controller c;
	
	public View(){
		super("CSV to Database Reader");
		c = new Controller(this);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(200,300);
		
		choseFile = new JButton("Chose CSV-File");
		choseFolder = new JButton("Chose Demography-Folder");
		status = new JLabel("Status");
		readIn = new JButton("Read In");
		
		relPanel = new JPanel();
		relLabel = new JLabel("Relationname");
		relField = new JTextField("Relationname");
		relPanel.add(relLabel);
		relPanel.add(relField);
		
		dbPanel = new JPanel();
		dbLabel = new JLabel("Databasename"); 
		dbField = new JTextField("rekur");
		dbPanel.add(dbLabel);
		dbPanel.add(dbField);
		
		userPanel = new JPanel();
		userLabel = new JLabel("Username");
		userField = new JTextField("rekur");
		userPanel.add(userLabel);
		userPanel.add(userField);
		
		
		pwPanel = new JPanel();
		pwLabel = new JLabel("Password");
		pwField = new JTextField("rekur");
		pwPanel.add(pwLabel);
		pwPanel.add(pwField);
		
		
		this.add(choseFile);
		this.add(choseFolder);
		this.add(status);
		this.add(readIn);
		this.add(relPanel);
		this.add(dbPanel);
		this.add(userPanel);
		this.add(pwPanel);
		
		choseFile.addActionListener(c);
		choseFolder.addActionListener(c);
		readIn.addActionListener(c);
		readIn.setEnabled(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new View();
	}
}
