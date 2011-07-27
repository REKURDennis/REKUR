package org.glowa.danube.deepactors.util.io;

/** This enumeration defines available column types for initialzation files.
 * 
 * A type defines validity of a column entry as follows: <br>
 *   intVal - any integer value, e.g. 46<br>
 *   floatVal - any float value, e.g. 3.78<br>
 *   intSet - enclosed in curly brackets, separated by commas, e.g. {1,3,9,67,4}<br>
 *   bool - false or true, e.g. false<br>
 *   period - {YYYYMMDD,YYYYMMDD} or ALWAYS, e.g. {19950101,19950102}<br>
 *   string - any string not containing semicolons
 * 
 * @author janisch
 * @version $Id: ColumnType.java,v 1.4 2005/08/26 11:17:13 janisch Exp $ 
 */
public enum ColumnType {
    intval,
    floatval,
    intset, 
    bool,
    period,
    string;
}
/**
 * $Log: ColumnType.java,v $
 * Revision 1.4  2005/08/26 11:17:13  janisch
 * Release 1.0.0
 *
 * Revision 1.3  2005/01/11 15:45:05  janisch
 * Added javadoc.
 *
 * Revision 1.2  2005/01/11 15:43:29  janisch
 * Javadoc.
 *
 */