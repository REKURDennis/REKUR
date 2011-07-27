package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map.Entry;
import java.util.*;

import view.View;

public class ASCtoCSV {
	BufferedReader readIn;
	FileWriter writeOut;
	View v;
	int nCols;
	int nRows;
	int xllCorner;
	int yllCorner;
	int cellsize;
	int noData;
	public String actor = "org.glowa.danube.components.actor.touristmodel.deepactors.DA_SourceArea";
	//String destinationActor = "org.glowa.danube.components.actor.destinationModel.deepactors.DD_Destination";
	public String inputName = "bysh_final.txt";
	public String outputName = "DT_ActorsInit.csv";
	public String collaborators ="";
	public String initialPlans ="1001";
	
	HashMap<Integer, Vector<Integer>> actors; 
	public ASCtoCSV(View v){
		this.v = v;
		//init();
		actors = new HashMap<Integer, Vector<Integer>>();
		//readandwrite();
	}
	
	public void setInputFile(String fileName){
		inputName = fileName;
	}
	
	public void setOuputFile(String fileName){
		outputName = fileName;
	}
	
	public void setCollaborators(String collaborators){
		this.collaborators = collaborators;
		v.status.setText("Status: set collaborators");
	}
	
	public void setinitialPlans(String plans){
		this.initialPlans = plans;
		v.status.setText("Status: set plans");
	}
	public void setActorClass(String className){
		actor = className;
		v.status.setText("Status: set ActorClass");
	}
	
	public void init(){
		try{
			v.status.setText("Status: init File");
			String dateiNameId = inputName;
			readIn = new BufferedReader(new FileReader(dateiNameId));
			String line = readIn.readLine();
			nCols = Integer.parseInt(lastToken(line));
			 line = readIn.readLine();
			nRows = Integer.parseInt(lastToken(line));
			 line = readIn.readLine();
			 StringTokenizer st = new StringTokenizer(lastToken(line), ",");
			xllCorner = Integer.parseInt(st.nextToken());
			 line = readIn.readLine();
			 st = new StringTokenizer(lastToken(line), ",");
			yllCorner = Integer.parseInt(lastToken(st.nextToken()));
			 line = readIn.readLine();
			cellsize = Integer.parseInt(lastToken(line));
			 line = readIn.readLine();
			noData = Integer.parseInt(lastToken(line));
			System.out.println(nCols);
			System.out.println(nRows);
			System.out.println(xllCorner);
			System.out.println(yllCorner);
			System.out.println(cellsize);
			System.out.println(noData);
		}
		catch(Exception e){System.out.println(e);}
	}
	private String lastToken(String line){
		StringTokenizer st = new StringTokenizer(line, " ");
		String back="";
		while(st.hasMoreTokens()){
			back = st.nextToken();
		}
		return back;
	}
	
	public void readandwrite(){

		try{
			v.status.setText("Status: Start Convert");
			writeOut = new FileWriter(outputName, false);
			writeOut.write("");
			writeOut.flush();
			writeOut = new FileWriter(outputName, true);
		}catch(Exception e){System.out.println(e);}
		
		try{
			String dateiNameId = inputName;
			readIn = new BufferedReader(new FileReader(dateiNameId));
			for(int i= 0;i<6;i++)readIn.readLine();
		
			String line;
			int pid = 0;
			
			while((line = readIn.readLine())!=null){
				StringTokenizer st = new StringTokenizer(line, " ");
				while(st.hasMoreTokens()){
					int zahl = Integer.parseInt(st.nextToken());
					if(zahl==noData){
						
					}
					else{
						if(actors.containsKey(zahl)){
							Vector<Integer> actorSet = actors.get(zahl);
							actorSet.add(pid);
							actors.put(zahl, actorSet);
						}
						else{
							Vector<Integer> destSet = new Vector<Integer>();
							destSet.add(pid);
							actors.put(zahl, destSet);
						}
					}
					pid++;
				}
				
			}
			
			writeOut.write("id;class;location;collaborators;initialPlans\n");
			writeOut.write("intVal;string;intSet;intSet;intSet\n");
			for(Entry<Integer, Vector<Integer>> entry:actors.entrySet()){
				writeOut.write(entry.getKey()+";"+actor+";"+"{");
				int z = 0;
				for(int i : entry.getValue()){
					if(z>0){
						writeOut.write(","+i);
					}
					else{
						writeOut.write(""+i);
					}
					z++;
				}
				writeOut.write("};{"+collaborators+"};{"+initialPlans+"}\n");
			}
			writeOut.flush();
			writeOut.close();
			readIn.close();
			v.status.setText("Status: Convertion finished");
		}
		catch(Exception e){System.out.println(e);}
	}
}
