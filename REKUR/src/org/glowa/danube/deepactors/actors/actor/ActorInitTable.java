package org.glowa.danube.deepactors.actors.actor;

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
 * @version $Id: ActorInitTable.java,v 1.3 2007/10/31 10:16:50 janisch Exp $ 
 */
public final class ActorInitTable extends AbstractInitTable<ActorInit>{

    public ActorInitTable(File file, ClassLoader cl) {
        parseInitFile(file, cl);
    }
    private void parseInitFile(File file, ClassLoader classLoader) {
        try {
            Set<Row> rows = new InitFile(file).getRows();
            for(Row r : rows) {
                int id = r.getId();
                Class<? extends AbstractActor> actorType = AbstractActor.class;
                Class<? extends AbstractActor> cl = 
                    r.getColEntry("class").contentToClass(classLoader).asSubclass(actorType);
                int[] loc = r.getColEntry("location").contentToIntArray();
                int[] col = r.getColEntry("collaborators").contentToIntArray();
                int[] ip = r.getColEntry("initialPlans").contentToIntArray();
                ActorInit ait = new ActorInit(id,cl,loc,col,ip);
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
 * $Log: ActorInitTable.java,v $
 * Revision 1.3  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.2  2006/01/31 14:37:45  janisch
 * Refactored generic type usage, introduced SuppressWarnings("unchecked")
 *
 * Revision 1.1  2005/08/26 11:15:47  janisch
 * Release 1.0.0
 *
 * Revision 1.5  2005/01/24 10:05:08  janisch
 * Sort the array in descending priority order (previos was ascending,
 * which is quite counter intuitive)
 *
 * Revision 1.4  2005/01/21 11:19:41  janisch
 * Refactored handling of IO exceptions.
 *
 * Revision 1.3  2005/01/21 07:46:15  janisch
 * Refactored initialization tables to extend the new abstract base class AbstractInitTable.
 *
 * Revision 1.2  2005/01/11 17:34:36  janisch
 * Added cvs log.
 *
 */