package org.glowa.danube.deepactors.actors.plan;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.glowa.danube.deepactors.util.io.AbstractInitTable;
import org.glowa.danube.deepactors.util.io.InitFile;
import org.glowa.danube.deepactors.util.io.Row;
import org.glowa.danube.deepactors.util.rac.PreconditionViolation;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: PlanInitTable.java,v 1.2 2007/10/31 10:16:50 janisch Exp $ 
 */
public final class PlanInitTable extends AbstractInitTable<PlanInit>{

    public PlanInitTable(File file, ClassLoader cl) {
        parseInitFile(file, cl);
    }
    private void parseInitFile(File file, ClassLoader cl) {
        try {
            Set<Row> rows = new InitFile(file).getRows();
            for(Row r : rows) {
                int id = r.getId();
                PlanInit pit = new PlanInit(
                        id,
                        r.getColEntry("class").contentToClass(cl),
                        r.getColEntry("actions").contentToIntArray(),
                        r.getColEntry("rating").contentToFloat());
                initObjects.put(id, pit);
            }
        } catch (IOException e) { handleIOException(file, e); }
    }
    private void handleIOException(File file, IOException e) 
    throws PreconditionViolation {
        final String nl = System.getProperty("line.separator");
        String mes = "IOException while processing file: " + file +  
            "Stacktrace: " + nl + e.getMessage();
        throw new PreconditionViolation("Invalid plan init file." + 
                nl + mes);
    }
}
/**
 * $Log: PlanInitTable.java,v $
 * Revision 1.2  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.1  2005/08/26 11:16:23  janisch
 * Release 1.0.0
 *
 * Revision 1.4  2005/01/21 11:19:41  janisch
 * Refactored handling of IO exceptions.
 *
 * Revision 1.3  2005/01/21 07:46:15  janisch
 * Refactored initialization tables to extend the new abstract base class AbstractInitTable.
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */