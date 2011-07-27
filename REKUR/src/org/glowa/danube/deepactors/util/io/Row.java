package org.glowa.danube.deepactors.util.io;

import java.util.Hashtable;
import java.util.StringTokenizer;



/**
 * ToDo: javadoc.
 * 
 * Note: the column entries id is not handled case-sensitive, i.e.
 * an identifer 'CLASS' is equal to 'class' or even 'CLaSs'.  
 * @author janisch
 * @version $Id: Row.java,v 1.4 2005/08/26 11:17:12 janisch Exp $ 
 */
public class Row {

    /** Row id as given by the first entry of this row */
    private int id;
    
    /** Number of columns (excluding id column). */
    private int numCols; // without id col
    
    /** Key is the colum id as given by the columEntry 
     * (internal the lower case representation is applied)*/
    private Hashtable<String,ColumnEntry> colEntries = 
        new Hashtable<String,ColumnEntry>();
    
    /**
     * Assumes identical cardinality of the resulting columns.
     */
    public Row(String columnIds, String columnTypes, String row) {
        parseRow(removeSpaces(columnIds), 
                removeSpaces(columnTypes), 
                removeSpaces(row));
    }
    
    public int getNumColumns() { return numCols; }
    public int getId() { return id; }
    
    public ColumnEntry getColEntry(String colId) {
        return colEntries.get(colId.toLowerCase());
    }

    private void parseRow(String colIds, String colTypes, String row) {
        StringTokenizer idsTok = new StringTokenizer(colIds,";");
        StringTokenizer typesTok = new StringTokenizer(colTypes,";");
        StringTokenizer rowTok = new StringTokenizer(row,";");
        
        // first entry in row tok is always id of this row
        numCols = idsTok.countTokens() - 1 ; // without id col
        String rowId = rowTok.nextToken();
        String colId = idsTok.nextToken();
        ColumnEntry ce = 
            new ColumnEntry(colId.toLowerCase(), 
                    colType(typesTok.nextToken()), 
                    rowId);
        id = Integer.parseInt(rowId);
        colEntries.put(colId, ce);
        while(idsTok.hasMoreTokens()) {
            colId = idsTok.nextToken();
            ce = new ColumnEntry(colId.toLowerCase(), 
                    colType(typesTok.nextToken()),
                    rowTok.nextToken());
            colEntries.put(colId.toLowerCase(), ce);         
        }
    }
    private ColumnType colType(String s) {
        return ColumnType.valueOf(ColumnType.class, s.toLowerCase());
    }
    private String removeSpaces(String s) {
        StringTokenizer st = new StringTokenizer(s," ",false);
        StringBuffer buf = new StringBuffer();
        while (st.hasMoreElements()) buf.append(st.nextElement());
        return buf.toString();
    }
}
/**
 * $Log: Row.java,v $
 * Revision 1.4  2005/08/26 11:17:12  janisch
 * Release 1.0.0
 *
 * Revision 1.3  2005/01/24 10:06:00  janisch
 * Bugfix: column id wasn't stored in lower case representation.
 *
 * Revision 1.2  2005/01/12 07:39:11  janisch
 * Added cvs log.
 *
 */