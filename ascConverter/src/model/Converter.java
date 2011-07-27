package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;


public class Converter {
	BufferedReader readIn;
	FileWriter writeOut;
	int nCols;
	int nRows;
	int xllCorner;
	int yllCorner;
	int cellsize;
	int noData;
	
	public Converter(){
		init();
		readIn();
		createArea();
		createEast();
		createNorth();
		createElev();
	}
	
	private void init(){
		try{
			String dateiNameId = "europa2.asc";
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
	
	private void readIn(){

		try{
			writeOut = new FileWriter("binaryEurope.asc", false);
			writeOut.write("");
			writeOut.flush();
			writeOut = new FileWriter("binaryEurope.asc", true);
		}catch(Exception e){System.out.println(e);}
		
		try{
			String dateiNameId = "europa2.asc";
			readIn = new BufferedReader(new FileReader(dateiNameId));
			for(int i= 0;i<6;i++)writeOut.write(readIn.readLine()+"\n");
		
			String line;
			
			while((line = readIn.readLine())!=null){
				StringTokenizer st = new StringTokenizer(line, " ");
				while(st.hasMoreTokens()){
					int zahl = Integer.parseInt(st.nextToken());
					if(zahl==noData){
						writeOut.write(zahl+" ");
					}
					else{
						writeOut.write("1 ");
					}
				}
				writeOut.write("\n");
			}
			writeOut.flush();
			writeOut.close();
			readIn.close();
		}
		catch(Exception e){System.out.println(e);}
	}
	
	
	private void createArea(){
		try{
			writeOut = new FileWriter("Area-Europe.asc", false);
			writeOut.write("");
			writeOut.flush();
			writeOut = new FileWriter("Area-Europe.asc", true);
		}catch(Exception e){System.out.println(e);}
		
		try{
			String dateiNameId = "europa2.asc";
			readIn = new BufferedReader(new FileReader(dateiNameId));
			for(int i= 0;i<6;i++)writeOut.write(readIn.readLine()+"\n");
			String line;
			while((line = readIn.readLine())!=null){
				StringTokenizer st = new StringTokenizer(line, " ");
				while(st.hasMoreTokens()){
					st.nextToken();
					writeOut.write((cellsize*cellsize)+" ");
				}
				writeOut.write("\n");
			}
			writeOut.flush();
			writeOut.close();
			readIn.close();
		}
		catch(Exception e){System.out.println(e);}
	}
	
	private void createElev(){
		try{
			writeOut = new FileWriter("Elev-Europe.asc", false);
			writeOut.write("");
			writeOut.flush();
			writeOut = new FileWriter("Elev-Europe.asc", true);
		}catch(Exception e){System.out.println(e);}
		
		try{
			String dateiNameId = "europa2.asc";
			readIn = new BufferedReader(new FileReader(dateiNameId));
			for(int i= 0;i<6;i++)writeOut.write(readIn.readLine()+"\n");
			String line;
			while((line = readIn.readLine())!=null){
				StringTokenizer st = new StringTokenizer(line, " ");
				while(st.hasMoreTokens()){
					st.nextToken();
					writeOut.write((200)+" ");
				}
				writeOut.write("\n");
			}
			writeOut.flush();
			writeOut.close();
			readIn.close();
		}
		catch(Exception e){System.out.println(e);}
	}
	
	
	private void createEast(){
		try{
			writeOut = new FileWriter("East-Europe.asc", false);
			writeOut.write("");
			writeOut.flush();
			writeOut = new FileWriter("East-Europe.asc", true);
		}catch(Exception e){System.out.println(e);}
		
		try{
			String dateiNameId = "europa2.asc";
			readIn = new BufferedReader(new FileReader(dateiNameId));
			for(int i= 0;i<6;i++)writeOut.write(readIn.readLine()+"\n");
		
			String line;
			
			while((line = readIn.readLine())!=null){
				int x = xllCorner+(cellsize/2);
				StringTokenizer st = new StringTokenizer(line, " ");
				while(st.hasMoreTokens()){
					st.nextToken();
					writeOut.write(" "+x);
					x+=cellsize;
				}
				writeOut.write("\n");
			}
			writeOut.flush();
			writeOut.close();
			readIn.close();
		}
		catch(Exception e){System.out.println(e);}
	}
	
	private void createNorth(){
		try{
			writeOut = new FileWriter("North-Europe.asc", false);
			writeOut.write("");
			writeOut.flush();
			writeOut = new FileWriter("North-Europe.asc", true);
		}catch(Exception e){System.out.println(e);}
		
		try{
			String dateiNameId = "europa2.asc";
			readIn = new BufferedReader(new FileReader(dateiNameId));
			for(int i= 0;i<6;i++)writeOut.write(readIn.readLine()+"\n");
		
			String line;
			int x = yllCorner+(cellsize/2);
			while((line = readIn.readLine())!=null){
				
				StringTokenizer st = new StringTokenizer(line, " ");
				while(st.hasMoreTokens()){
					st.nextToken();	
					writeOut.write(" "+x);
						
				}
				writeOut.write("\n");
				x+=cellsize;
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
		new Converter();
	}

}
