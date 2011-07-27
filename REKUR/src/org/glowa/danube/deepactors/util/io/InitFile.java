package org.glowa.danube.deepactors.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: InitFile.java,v 1.4 2005/09/08 08:13:00 janisch Exp $ 
 */
public class InitFile {

    private Set<Row> rows = new HashSet<Row>();
    
    /**
     * @throws IOException
     * @pre.condition: first line column id, sec line colum types and  
     * value rows start at third line.
     */
    public InitFile(File file) throws IOException {
        parseFile(file);
    }
    private void parseFile(File file) throws IOException {
       FileReader fileReader = new FileReader(file);
       BufferedReader reader = new BufferedReader(fileReader);
       String firstRow = reader.readLine();
       String secRow = reader.readLine();
       String curRow;
       while((curRow = reader.readLine()) != null) {
           rows.add(new Row(firstRow,secRow,curRow));
       }
       reader.close();
       fileReader.close();
    }
    
    public Set<Row> getRows() {
        return rows;
    }
}
/**
 * $Log: InitFile.java,v $
 * Revision 1.4  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.3  2005/08/26 11:17:12  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */