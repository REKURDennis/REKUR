package org.glowa.danube.deepactors.model;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ResourceKeys.java,v 1.3 2006/08/16 13:58:12 janisch Exp $ 
 */
public class ResourceKeys {
    // assumed keys of config property file
    public static final String ActorsInitCfgKey = "deepactors.actors.init"; 
    public static final String PlansInitCfgKey = "deepactors.plans.init";
    public static final String ActionsInitCfgKey = "deepactors.actions.init";
    public static final String ProxelSensorEventsCfgKey = "deepactors.proxelsensor.events";
    public static final String ActorSensorEventsCfgKey = "deepactors.actorsensor.events";
    public static final String ConstraintSensorEventsCfgKey = "deepactors.constraintsensor.events";
    public static final String ConstraintsCfgKey = "deepactors.constraints";
    public static final String PlansInitLoadCfgKey = "deepactors.plans.init.loadandretainall";
    
    // keys used to store resources (ResourceAdmin)
    public static final String ActorsInit = "actors.init";
    public static final String PlansInit = "plans.init";
    public static final String ActionsInit = "actions.init";
    public static final String ProxelSensorEvents = "proxelsensor.events";
    public static final String ActorSensorEvents = "actorsensor.events";
    public static final String ConstraintSensorEvents = "constraintsensor.events";
    public static final String Constraints = "constraints";
    public static final String PlansInitLoad = "plans.init.loadandretainall";
}

/**
 * $Log: ResourceKeys.java,v $
 * Revision 1.3  2006/08/16 13:58:12  janisch
 * Added configuration parameters of models' cfg file as
 * configuration resource.
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:49  janisch
 * Release 1.0.0
 *
 */