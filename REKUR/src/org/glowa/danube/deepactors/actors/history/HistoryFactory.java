package org.glowa.danube.deepactors.actors.history;

import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: HistoryFactory.java,v 1.2 2005/09/08 08:12:59 janisch Exp $ 
 */
public class HistoryFactory {

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(HistoryFactory.class);

    // provided roles
    private History history;
    private HistoryCore historyCore;
    
    public HistoryFactory() {
        log.info("Instantiate and bind History principle ...");        
    }
    
    public void createHistory() { 
        HistoryImpl hi= new HistoryImpl();
        history = hi;
        historyCore = hi;
    }
    public History getHistory() {return history;}
    public HistoryCore getHistoryCore() {return historyCore;}
}

/**
 * $Log: HistoryFactory.java,v $
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:15:35  janisch
 * Release 1.0.0
 *
 */