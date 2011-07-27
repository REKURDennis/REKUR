package org.glowa.danube.deepactors.util;

import org.glowa.danube.simulation.model.proxel.AbstractProxel;

/**
 * Wrapper for DANUBIA base class <tt>Proxel</tt>. 
 * ToDo: Not necessary if <tt>Proxel</tt> would implement DataMapEntry. 
 */
public class ProxelMapEntry implements MapEntry {

    private AbstractProxel proxel;
    private int key;
    
    public ProxelMapEntry(AbstractProxel p){
        this.proxel = p;
        this.key = p.pid();
    }
    
    public int getId() {return key;}
    public AbstractProxel getProxel(){return proxel;}
}
/**
 * $Log: ProxelMapEntry.java,v $
 * Revision 1.2  2007/10/29 09:37:38  janisch
 * - Aligned package imports to Danubia 2.0
 * - Proxel -> AbstractProxel
 *
 * Revision 1.1  2005/09/23 11:00:59  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.1  2005/08/26 11:17:09  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */