package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import view.View;



public class Reader {
	public Connection con;
	public Statement stmt;
	public Statement stmt1;
	public BufferedReader readIn;
	public String readInFile;
	public String demoFolder;
	public String relationName ="";
	public String userName;
	public String dbName;
	public String password;
	public View v;
	
	public Reader(View v){
		this.v = v;
	}
	
	public void readDemography(){
		establishConnection();
		demoRelationsPerYear();
		v.status.setText("Fertig");
	}
	
	public void readIn(){
		establishConnection();
		try {
			readIn = new BufferedReader(new FileReader(readInFile));
			String line = readIn.readLine();
			generateRelation(relationName, line);
			while((line = readIn.readLine())!=null){
				writeTupel(relationName,line);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
		v.status.setText("Fertig");
	}
	
	private void writeTupel(String relation,String line){
		try {
			//System.out.println(line);
			if(relationName.equals("sourceareaoverview")||relationName.equals("kaufkraft")){
				line = line.replaceAll("\\.", "");
				line = line.replaceAll(",", ".");
			}
			String[] columns = line.split(";", -1);
			String query ="insert into "+relation+"\n"+"values(";
			boolean first = true;
			for(String column:columns){
				if(first){
					first = false;
					query+="'"+(column)+"'";
				}
				else{
					query+=",'"+column+"'";
				}
			}
			query+=")";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
	}
	private void demoRelationsPerYear(){
		try {
			String query = " select * from landkreise";
			ResultSet sa = stmt1.executeQuery(query);
			while(sa.next()){
				int lid = Integer.parseInt(sa.getString(2));
				readIn = new BufferedReader(new FileReader(demoFolder+File.separator+lid+".csv"));
				String line = readIn.readLine();
				readIn.readLine();
				generateNewDemoRelation("d"+lid+"");
				while((line = readIn.readLine())!=null){
					writeTupel("d"+lid,line);
				}
				v.status.setText("Write Landkreis"+lid);
			}	
		} catch (Exception ex) {
	        // Fehler behandeln
			ex.printStackTrace();
			System.out.println("Error");
		}
	}
	
	private void generateRelation(String name, String attributeNames){
		try {
			attributeNames = attributeNames.replaceAll("\\.", "");
			try{
				stmt.executeUpdate("drop table "+name);
			}
			catch(Exception e){}
			
			StringTokenizer st = new StringTokenizer(attributeNames, ";");
			String query="Create table "+name+"("+st.nextToken()+" varchar(255)";
			while(st.hasMoreTokens()){
				StringTokenizer st2 = new StringTokenizer(st.nextToken(), " ");
				String attribute = "";
				while(st2.hasMoreTokens()){
					attribute+=st2.nextToken();
				}
				query+=","+attribute+" varchar(255)";
			}
			query+=")";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
	}
	
	private void generateNewDemoRelation(String name){
		try {
			try{
				stmt.executeUpdate("drop table "+name);
			}
			catch(Exception e){}
			String query="Create table "+name+"(Age varchar(255)";
			for(int i = 2008; i <=2030; i++){
				for(int z = 0;z <2;z++){
					if(z==0){
						query += ",m"+i+" varchar(255)";
					}
					else{
						query += ",w"+i+" varchar(255)";
					}
				}
			}
			query+=")";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
	}
	
	
	private void establishConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println(dbName+userName+password);
			con = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName+"?user="+userName+"&password="+password);
			stmt = con.createStatement();
			stmt1 = con.createStatement();
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
    }
	
	public void deleteRun(){
		establishConnection();
		int year =2008;
		try{
			for(int y = year; y<=2030;y++){
				for(int i = 1;i<54;i++){
					try{
						stmt.executeUpdate("drop table touristsPerDests"+y+""+i);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					try{
						stmt.executeUpdate("drop table checkDestTable"+y+""+i);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}	
		}
		catch(Exception ex){}
	}
}
