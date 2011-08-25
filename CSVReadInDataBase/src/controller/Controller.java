package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import model.Reader;

import view.View;

public class Controller extends Thread implements ActionListener{
	public View v;
	private JFileChooser fc;
	private Reader reader;
	private boolean readIn = true;
	
	public Controller (View v){
		this.v = v;
		reader = new Reader(v);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == v.choseFile){
			reader.readInFile = getPath();
			v.readIn.setEnabled(true);
			v.status.setText(reader.readInFile);
			v.relField.setEnabled(true);
		}
		if(e.getSource() == v.choseFolder){
			readIn = false;
			reader.demoFolder = getPath();
			v.readIn.setEnabled(true);
			v.status.setText(reader.demoFolder);
			v.relField.setEnabled(false);
		}
		if(e.getSource() == v.readIn){
			this.start();
		}
	}
	private String getPath(){
		fc = new JFileChooser(".");
		
		int returnVal;
		if(readIn)fc.setFileFilter(fileFilterCSV);
		if(!readIn)fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		returnVal = fc.showOpenDialog(v);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			boolean error=true;
			try{
				return(fc.getSelectedFile().getAbsolutePath());
			}
			catch(Exception ex){
				if(error)
				JOptionPane.showMessageDialog(null, "No valid File!", "Error!", JOptionPane.WARNING_MESSAGE);
			}
		}
		return null;
	}
	
	FileFilter fileFilterASC = new FileFilter() 
    { 
	      public boolean accept( File f ) 
	      { 
	        return f.isDirectory() || f.getName().toLowerCase().endsWith( ".asc" ); 
	      } 
	      public String getDescription() 
	      { 
	        return "(.asc)"; 
	      } 
	};
	
	FileFilter fileFilterCSV = new FileFilter() 
    { 
	      public boolean accept( File f ) 
	      { 
	        return f.isDirectory() || f.getName().toLowerCase().endsWith( ".csv" ); 
	      } 
	      public String getDescription() 
	      { 
	        return "(.csv)"; 
	      } 
	};
	
	public void run(){
		reader.dbName = v.dbField.getText();
		reader.userName = v.userField.getText();
		reader.password = v.pwField.getText();
		if(readIn){
			reader.relationName = v.relField.getText();
			reader.readIn();
		}
		else{
			reader.readDemography();
		}
	}
	
}
