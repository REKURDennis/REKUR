package org.glowa.danube.deepactors.sensors.event;

/**
 * Abstract base class to implement sensor events.
 * 
 * The design follows exactly the same pattern which was applied to 
 * the <code>constraints</code> package. 
 */
public abstract class AbstractSensorEvent<S extends EventSource> 
    implements SensorEvent<S> {
    
    public final boolean condition(S src) {
        if(src != null && condHolds(src)) return true;
        return false;
    }
    
    public final int getId() { 
        return id(); 
    }
    
    private boolean condHolds(S src) {
        try {
            if (conditionImpl(src)) return true;        
            return false;        
        }
        catch(ClassCastException e){
            return false;
        }
    }

    protected abstract boolean conditionImpl(S src);
    protected abstract int id();
        
}
/**
 * $Log: AbstractSensorEvent.java,v $
 * Revision 1.3  2006/01/31 14:39:36  janisch
 * Introduced Type Parameter to interface
 *
 * Revision 1.2  2005/09/27 08:59:07  janisch
 * Added plug-point id(); implemented getId();
 *
 * Revision 1.1  2005/08/26 11:17:49  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */