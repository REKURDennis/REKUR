package org.glowa.danube.deepactors.actors.action;

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
 * Note: default values are not yet supported, i.e. all necessary
 * columns <emph>must</emph> exist.
 * @author janisch
 * @version $Id: ActionInitTable.java,v 1.3 2007/10/31 10:16:50 janisch Exp $ 
 */
public final class ActionInitTable extends AbstractInitTable<ActionInit>{

    public ActionInitTable(File file, ClassLoader cl) {
        parseInitFile(file, cl);
    }
    private void parseInitFile(File file, ClassLoader cl) {
        try {
            InitFile aif  = new InitFile(file);
            Set<Row> rows = aif.getRows();
            for(Row r : rows) {
                int id = r.getId();
                Class<? extends AbstractAction> actionType = AbstractAction.class; 
                ActionInit ait = new ActionInit(
                        id,
                        r.getColEntry("class").contentToClass(cl).asSubclass(actionType),
                        r.getColEntry("period").contentToTimePeriod(),
                        r.getColEntry("mandatory").contentToBoolean());
                initObjects.put(id, ait);
            }
        } catch (IOException e) { handleIOException(file, e); }
    }
    private void handleIOException(File file, IOException e) 
    throws PreconditionViolation {
        final String nl = System.getProperty("line.separator");
        String mes = "IOException while processing file: " + file +  
            "Stacktrace: " + nl + e.getMessage();
        throw new PreconditionViolation("Invalid action init file." + 
                nl + mes);
    }
}
/**
 * $Log: ActionInitTable.java,v $
 * Revision 1.3  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.2  2006/01/31 14:34:24  janisch
 * Refactored generic type usage, introduced SuppressWarnings("unchecked")
 *
 * Revision 1.1  2005/08/26 11:14:54  janisch
 * Release 1.0.0
 *
 * Revision 1.7  2005/02/18 16:00:31  janisch
 * An actions proxel map is now automatically set by the framework
 * implementation to refer to all proxel objects of the location of the
 * associated actor. The column 'pids" must be removed from any
 * action initialization file (action.init.csv) in order to be usable up
 * from this release..
 *
 * Revision 1.6  2005/01/21 11:19:41  janisch
 * Refactored handling of IO exceptions.
 *
 * Revision 1.5  2005/01/21 10:59:03  janisch
 * Implemented IOException handling: throws Precondition-Violation.
 *
 * Revision 1.4  2005/01/21 07:45:48  janisch
 * Made class final.
 *
 * Revision 1.3  2005/01/21 07:41:27  janisch
 * Refactored initialization tables to extend the new abstract base class AbstractInitTable.
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */