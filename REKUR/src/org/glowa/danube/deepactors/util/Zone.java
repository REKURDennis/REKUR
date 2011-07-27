package org.glowa.danube.deepactors.util;

/**
 * @author janisch
 * @version $Id: Zone.java,v 1.4 2005/09/23 11:00:59 janisch Exp $ 
 */
public class Zone{

    private int[] pids;
    
    public Zone(int[] pids){
        this.pids = pids;
    }

    public int[] getPIDArray() {
        return pids.clone();
    }

    public Zone copy() {
        return new Zone(pids.clone());        
    }
}
/**
 * $Log: Zone.java,v $
 * Revision 1.4  2005/09/23 11:00:59  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.1  2005/08/26 11:17:08  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */