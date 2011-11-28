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
	public String relationName;
	public String userName;
	public String dbName;
	public String password;
	public View v;
	public Reader(View v){
		this.v = v;
	}
	
	public void readDemography(){
		establishConnection();
		//readIn();
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
			//line = line.replaceAll("\\.", "");
			//line = line.replaceAll(",", ".");
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
	
	private void writeDemoTupel(String relation,String line){
		try {
			//System.out.println(line);
			line = line.replaceAll("\\.", "");
			line = line.replaceAll(",", ".");
			String[] columns = line.split(";", -1);
			String id = columns[0];
			
			String queryDemo ="insert into demography\n"+"values('"+id+"','09'";
			//String queryCiti ="insert into citizen\n"+"values('"+id+"','09'";
			int year = 10;
			for(int i = 1;i<columns.length;i++){
				queryDemo+=",'"+columns[i]+"'";
				if(i%11==0){
					queryDemo+=")";
					System.out.println(queryDemo);
					stmt.executeUpdate(queryDemo);
					queryDemo ="insert into demography\n"+"values('"+id+"','"+year+"'";
					year++;
				}
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
	    }
	}
	
	private void generateDemoRelation(String name, String attributeNames){
		try {
			attributeNames = attributeNames.replaceAll("\\.", "");
			String query = "";
			stmt.executeUpdate("drop table demography");
			
			String[] attributes = attributeNames.split(";", -1);
			
			query="Create table demography("+attributes[0]+" varchar(255), year varchar(255)";
			for(int i =1; i<11;i++){
				attributes[i] = attributes[i].replaceAll(" ", "");
				query+=","+attributes[i]+" varchar(255)";
			}
			query+=",summary varchar(255))";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
	}
	
	private void generateCitiRelation(String name, String attributeNames){
		try {
			attributeNames = attributeNames.replaceAll("\\.", "");
			String query = "";
			//stmt.executeUpdate("drop table citizen");			
			query="Create table citizen(id varchar(255), Jahr09 varchar(255)";
			for(int i =10; i<30;i++){
				query+=",Jahr"+i+" varchar(255)";
			}
			query+=")";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
	}
	public void demoRelationsPerYear(){
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
	
	public void generateRelation(String name, String attributeNames){
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
}
