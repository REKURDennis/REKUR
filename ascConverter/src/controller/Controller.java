package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import model.ASCtoCSV;

import view.View;

public class Controller extends Thread implements ActionListener{
	public View v;
	private JFileChooser fc;
	private ASCtoCSV converter;
	
	public Controller (View v){
		this.v = v;
		converter = new ASCtoCSV(v);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == v.loadAscii){
			converter.setInputFile(getPath(true));
			v.saveCSV.setEnabled(true);
			v.status.setText("Status: Ausgabedatei waehlen");
		}
		if(e.getSource() == v.saveCSV){
			converter.setOuputFile(getPath(false));
			v.convertASCIItoCSV.setEnabled(true);
		}
		if(e.getSource() == v.convertASCIItoCSV){
			this.start();
		}
		
	}
	private String getPath(boolean open){
		fc = new JFileChooser(".");
		
		int returnVal;
		if(open){
			fc.setFileFilter(fileFilterASC);
			returnVal = fc.showOpenDialog(v);
		}
		else {
			fc.setFileFilter(fileFilterCSV);
			returnVal = fc.showSaveDialog(v);
		}
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
		converter.setCollaborators(v.collaborators.getText());
		converter.setinitialPlans(v.initialPlans.getText());
		converter.setActorClass(v.actorClass.getText());
		converter.init();
		converter.readandwrite();
	}
	
}
