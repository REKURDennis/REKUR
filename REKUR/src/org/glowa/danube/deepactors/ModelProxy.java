package org.glowa.danube.deepactors;

import java.util.Set;

import org.glowa.danube.deepactors.resmgt.ResourceAdmin ;
import org.glowa.danube.deepactors.sensors.SensorsControl ;
import org.glowa.danube.deepactors.actors.Actors ;
import org.glowa.danube.deepactors.actors.ActorsControl ;
import org.glowa.danube.deepactors.actors.actor.Actor;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ModelProxy.java,v 1.3 2006/08/16 13:55:33 janisch Exp $ 
 */
public class ModelProxy implements
org.glowa.danube.deepactors.model.ResourceAdmin,
org.glowa.danube.deepactors.model.Sensors,
org.glowa.danube.deepactors.model.Actors{
    
    ResourceAdmin resource;
    SensorsControl sensorsControl;
    Actors actors;
    ActorsControl actorsControl;
    
    ModelProxy(ResourceAdmin ra, SensorsControl sc, Actors a, ActorsControl ac){
        resource = ra;
        sensorsControl = sc;
        actors = a;
        actorsControl = ac;
    }
    
    // implementing org.glowa.danube.deepactors.model.ResourceAdmin 
    public void addClassSet(String key, Set<String> classNames) {
        resource.addClassSet(key, classNames);
    }
    public void addFile(String key, String fileName) {
        resource.addFile(key, fileName);
    }
    public void addConfigBoolean(String key, boolean value) {
        resource.addConfigBoolean(key, value);
    }
    
    // implementing org.glowa.danube.deepactors.model.Actors
    public void create() { actorsControl.create(); }
    public void init() { actorsControl.init(); }
    public void query() { actorsControl.query(); }
    public void decide() { actorsControl.decide(); }
    public void export() { actorsControl.export(); }
    public void store() { actorsControl.store(); }
    public Set<Actor> getActors() { return actors.getActors(); }
    
    // implementing org.glowa.danube.deepactors.model.Sensors
    public void update() { sensorsControl.update(); }

}

/**
 * $Log: ModelProxy.java,v $
 * Revision 1.3  2006/08/16 13:55:33  janisch
 * Added configuration parameters of models' cfg file as
 * configuration resource.
 *
 * Revision 1.2  2005/09/08 08:12:59  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:13  janisch
 * Release 1.0.0
 *
 */