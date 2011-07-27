package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map.Entry;
import java.util.*;

public class ASCtoCSVDestination {
	BufferedReader readIn;
	FileWriter writeOut;
	int nCols;
	int nRows;
	int xllCorner;
	int yllCorner;
	int cellsize;
	int noData;
	String actor = "org.glowa.danube.components.actor.destinationModel.deepactors.DD_Destination";
	//String destinationActor = "org.glowa.danube.components.actor.destinationModel.deepactors.DD_Destination";
	String inputName = "desti_110519.asc";
	String outputName = "DD_ActorsInit.csv";
	HashMap<Integer, Vector<Integer>> actors; 
	public ASCtoCSVDestination(){
		init();
		actors = new HashMap<Integer, Vector<Integer>>();
		readandwrite();
		//pseudoActors();
	}
	
	private void pseudoActors(){
		try{
			writeOut = new FileWriter(outputName, false);
			writeOut.write("");
			writeOut.flush();
			writeOut = new FileWriter(outputName, true);
			
			
			writeOut.write("id;class;location;collaborators;initialPlans\n");
			writeOut.write("intVal;string;intSet;intSet;intSet\n");
			for(int i = 0; i<1000000; i++){
				writeOut.write(i+";"+actor+";"+"{113436};{};{1001}\n");
			}
			
		}catch(Exception e){System.out.println(e);}
		
	}
	
	private void init(){
		try{
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
	
	private void readandwrite(){

		try{
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
				writeOut.write("};{};{1001}\n");
			}
			writeOut.flush();
			writeOut.close();
			readIn.close();
		}
		catch(Exception e){System.out.println(e);}
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ASCtoCSVDestination();
	}
		
}
