import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.StringTokenizer;


public class destinationReader {
	public Connection con;
	public Statement stmt;
	public BufferedReader readIn;
	public String readInFile="destinations";
	public String relationName ="destinations";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new destinationReader();
	}
	public destinationReader(){
		establishConnection();
		readIn();
	}
	public void readIn(){
		try {
			readIn = new BufferedReader(new FileReader(readInFile+".csv"));
			String line = readIn.readLine();
			generateRelation(relationName, line);
			//generateDemoRelation(readInFile, line);
			//generateCitiRelation(readInFile, line);
			while((line = readIn.readLine())!=null){
				writeTupel(relationName,line);
				//writeDemoTupel(readInFile, line);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
	}
	public void writeTupel(String relation,String line){
		try {
			//System.out.println(line);
			line = line.replaceAll("\\.", "");
			line = line.replaceAll(",", ".");
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
	
	public void writeDemoTupel(String relation,String line){
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
	
	public void generateDemoRelation(String name, String attributeNames){
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
	
	public void generateCitiRelation(String name, String attributeNames){
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
	
	
	public void generateRelation(String name, String attributeNames){
		try {
			attributeNames = attributeNames.replaceAll("\\.", "");
			//stmt.executeUpdate("drop table "+name);
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
	public void establishConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost/rekur?user=root&password=bla");
			stmt = con.createStatement();
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
	}
}
