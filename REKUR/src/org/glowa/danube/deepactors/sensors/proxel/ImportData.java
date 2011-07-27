package org.glowa.danube.deepactors.sensors.proxel;

import org.glowa.danube.tables.DataTable;

/**
 * Datatype provided by ProxelSource.
 * 
 * @author janisch
 * @version $Id: ImportData.java,v 1.1 2005/08/26 11:17:15 janisch Exp $ 
 */
final class ImportData {

    private final String tableKey;
    private final DataTable table;
    
    public ImportData(String tableKey, DataTable table) {
        this.table = table;
        this.tableKey = tableKey;
    }
    DataTable getTable() {
        return table;
    }
    String getTableKey() {
        return tableKey;
    }
}
/**
 * $Log: ImportData.java,v $
 * Revision 1.1  2005/08/26 11:17:15  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */