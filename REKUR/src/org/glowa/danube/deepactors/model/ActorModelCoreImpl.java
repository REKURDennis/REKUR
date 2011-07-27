package org.glowa.danube.deepactors.model;

import static org.glowa.danube.deepactors.model.ResourceKeys.ActionsInit;
import static org.glowa.danube.deepactors.model.ResourceKeys.ActionsInitCfgKey;
import static org.glowa.danube.deepactors.model.ResourceKeys.ActorSensorEvents;
import static org.glowa.danube.deepactors.model.ResourceKeys.ActorSensorEventsCfgKey;
import static org.glowa.danube.deepactors.model.ResourceKeys.ActorsInit;
import static org.glowa.danube.deepactors.model.ResourceKeys.ActorsInitCfgKey;
import static org.glowa.danube.deepactors.model.ResourceKeys.ConstraintSensorEvents;
import static org.glowa.danube.deepactors.model.ResourceKeys.ConstraintSensorEventsCfgKey;
import static org.glowa.danube.deepactors.model.ResourceKeys.Constraints;
import static org.glowa.danube.deepactors.model.ResourceKeys.ConstraintsCfgKey;
import static org.glowa.danube.deepactors.model.ResourceKeys.PlansInit;
import static org.glowa.danube.deepactors.model.ResourceKeys.PlansInitCfgKey;
import static org.glowa.danube.deepactors.model.ResourceKeys.PlansInitLoad;
import static org.glowa.danube.deepactors.model.ResourceKeys.PlansInitLoadCfgKey;
import static org.glowa.danube.deepactors.model.ResourceKeys.ProxelSensorEvents;
import static org.glowa.danube.deepactors.model.ResourceKeys.ProxelSensorEventsCfgKey;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.glowa.danube.components.DanubiaInterface;
import org.glowa.danube.components.DanubiaInterfaceNotFoundException;
import org.glowa.danube.deepactors.DeepActorManager;
import org.glowa.danube.deepactors.util.DeepActorLogger;
import org.glowa.danube.deepactors.util.InterfaceImpl;
import org.glowa.danube.metadata.AreaMetaData;
import org.glowa.danube.metadata.ComponentMetaData;
import org.glowa.danube.simulation.model.AbstractModel;
import org.glowa.danube.simulation.model.proxel.AbstractProxel;
import org.glowa.danube.simulation.model.proxel.ProxelTable;
import org.glowa.danube.utilities.internal.DanubiaLogger;
import org.glowa.danube.utilities.time.DanubiaCalendar;

/**
 * ToDo: javadoc.
 * 
 * Note that this core implementation is somehow special. It inherits from Danubia's
 * base class AbstractModel and implements core aspects of the deepactor framework
 * at the same time, i.e. it intertwingles things. Reason is that inheritance between
 * different components is not thoroughly thought through in our component framework.
 * 
 * What does effectively happen? Well, Danubia instantiates this class via Reflection
 * (-> default constructor), the default constructor instantiates the DeepActorManager
 * which is the topmost container component of the DeepActor framework responsible for
 * instantiation of the complete DeepActor story. This manager receives a reference to 
 * 'this', passes this reference to the factory of the actor model component here which
 * then is able to use this reference as an implementation of the interfaces provided
 * by the actor model component. 
 * The point is, normally the factory would instantiate this core impl. Since we want
 * Danubia to do this (in order the get all the proxel initialisation stuff) we need
 * to pass this instance around and share it between the danubia framework and the
 * deepactor framework level.
 * 
 * 
 * @author janisch
 * @version $Id: ActorModelCoreImpl.java,v 1.19 2008/04/14 08:51:06 janisch Exp $ 
 */
public class ActorModelCoreImpl extends AbstractModel<AbstractProxel> 
implements TimeQuery, ActorModelCore{

    private final DeepActorLogger log = 
        DeepActorLogger.newInstance(ActorModelCoreImpl.class); 
    // In danubia a distinguished class loader is applied
    private ClassLoader classLoader;

    // allow sub class access to attributes in order to enable testing
    // independent of the instantiation procedure of Danubia (see ModelManager) 
    protected Sensors sensors;
    protected ResourceAdmin resource;
    protected Actors actors;
    protected AbstractActorModel<? extends AbstractProxel> base;
    
    // store current simulation time to be queried by provided interface
    protected DanubiaCalendar time;
    
    // store initiallly the set of object references to import interfaces.
    protected Set<InterfaceImpl> impInterfSet;
    // store initially the component meta data.
    protected ComponentMetaData componentConfig;
    // store initially reference to danubia logger
    protected DanubiaLogger logger;
    // store the simulation start date, also initially
    protected DanubiaCalendar simStart;
    // and the amd ...
    protected AreaMetaData areaMetaData;
    
    /**
     * This constructor is called by the danubia framework, triggering 
     * instantiation of the DA framework
     */
    public ActorModelCoreImpl(ClassLoader danubiaClassLoader, ComponentMetaData compConfig) {
        classLoader = danubiaClassLoader;
        componentConfig = compConfig;
        DeepActorManager dam = new DeepActorManager(this, danubiaClassLoader);
        if(!dam.isBound()) {
            log.exception("DeepActor-Framework binding failed.");
        }
        // create base (i.e. the concrete deepactor model), required amongst others as 
        // implementor of export interfaces during initialisation in the danubia framework
        // Note that base is not initialised yet: this is done in init() below
        base = createBase();
    }
    
    /**
     * We can bind required interfaces with the constructor - Danubia 
     * does not know anything about these paremeters, hence binding 
     * will be done in the ActorModelFactory after having received the 
     * core implementation instance. 
     * @param s
     * @param rm
     * @param a
     */
    @SuppressWarnings("unchecked")
    void bindRequired(Sensors s, ResourceAdmin rm, Actors a){
        sensors = s;
        resource = rm;
        actors = a;
    }
    
    // -- implementing provided interfaces ----------------------------------
    public DanubiaCalendar getSimulationTime() { return time; }
    public ProxelTable<? extends AbstractProxel> getProxelTable() { return this.proxelTable(); }
    public Set<InterfaceImpl> getModelImport() { return impInterfSet; }

    // ----------------------------------------------------------------------
    // -- implementing plug points of AbstractModel -------------------------    
    // ----------------------------------------------------------------------
    @Override
    protected void init(){
        if(componentConfig == null){
            componentConfig = this.componentConfig();
           //else it was set directly e.g.g by some test suite without 
           // using Danubia instantiation
        } 
        // query logger instance, analog to componentConfig and impInterfSet
        if(logger == null){
            logger = this.logger();
        }
       
        // add init files and class names to resource (e.g. actor.init, etc.)
        addToResource(componentConfig.getComponentProperties());
        // create actors to be able to initialise base class state
        actors.create();

        // query references for import interfaces
        if(impInterfSet == null){
            impInterfSet = new HashSet<InterfaceImpl>();
            String[] impInterfArray = componentConfig.getImpInterfaces();
            for(String interf:impInterfArray) {
                DanubiaInterface interfObject = this.getImport(interf);
                impInterfSet.add(new InterfaceImpl(interfObject, interf));
            }
            // else it was set directly see above
        }
        // ... same for amd
        if(areaMetaData == null){
            areaMetaData = this.areaMetaData();
        }

        // initialise base state
        base.setRef(actors.getActors(), this);
        base.setAbstractComponentAttributes(impInterfSet, logger, componentConfig,
                areaMetaData);
        base.setAbstractModelAttributes(checkFailedLogger(), 
                localVisualization(), proxelTable(), modeloutpath(), timeseriespath());
        
        // actors and model may query the simulation time during init 
        if(simStart == null){
            simStart = this.simulationStart();
        }
        time = simStart;
        
        actors.init();
        // not that the actor objects are now fully initialised to be accessed during model init
        base.init();
    }
    @SuppressWarnings({ "unchecked"})
    private AbstractActorModel<? extends AbstractProxel> createBase() {
        AbstractActorModel<? extends AbstractProxel> model = null;
        try { 
            String deepActorModelClassName = componentConfig.getCompClass();
            Class<? extends AbstractActorModel<? extends AbstractProxel>> deepActorModelClass;
            if(classLoader == null){
                deepActorModelClass = (Class<? extends AbstractActorModel<? extends AbstractProxel>>) 
                Class.forName(deepActorModelClassName);
            }
            else{
                deepActorModelClass = (Class<? extends AbstractActorModel<? extends AbstractProxel>>) 
                Class.forName(deepActorModelClassName, true, classLoader);
            }
            model = deepActorModelClass.newInstance();
        } 
        catch (InstantiationException e) { log.exception(e); }
        catch (IllegalAccessException e) { log.exception(e); } 
        catch (ClassNotFoundException e) { log.exception(e); }
        
        return model;
    }
    
    @Override
    protected void getData(DanubiaCalendar time){
        this.time = time;
        log.info(time + ": sensors update their data ...");
        sensors.update();
        log.info(time + ": trigger model data import ...");
        
        //timeMeas.startMeasurement();
        base.getData();
        //timeMeas.endMeassurement(modelClassName, "base.getData");
    }

    @Override
    protected void compute(DanubiaCalendar time) {
        // Moved query call from end of processgetData implementation. 
        // It's semantically equivalent! and improves the performance of a
        // integrated simulation because coupled danubia models must not 
        // need to wait until actor objects finished their query. They must
        // only wait until the model finished getData! The query step may
        // be considered local to the respective model implementation, no sensor
        // refers to external data but to the local proxel table only.
        // Note that it would be wrong to change the simulation time here ...
        log.info(time + ": trigger actors query ...");
        //timeMeas.startMeasurement();
        actors.query();
        //timeMeas.endMeassurement(modelClassName, "actors.query");

        // but it is required to change the time for the following steps
        this.time = time;
        base.preCompute();
        actors.decide();
        base.postCompute();
    }    

    @Override
    protected void provide(DanubiaCalendar time) {
        this.time = time;
        actors.export();
        actors.store();
        base.commit();
    }

    @Override
    protected void store(DanubiaCalendar time) {
        this.time = time;
        base.store();
    }
    
    @Override
    protected void finish() {
        base.cleanup();
    }
    
    /**
     * Returns for any provided interface name the main class of the DeepActor model.
     * Yes, this might be over simplified and we do not check here that this main
     * class is indeed an implementation of the export interfaces as declared in the
     * components meta data. Instead we assume that this plug-in is called only with
     * interface names which 1. are declared to be export in the meta data and
     * 2. are implemented by the main class. Consider these things to be preconditions.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected <I extends DanubiaInterface> I getImplementor(String interfaceName)
            throws DanubiaInterfaceNotFoundException {
        try{
            return (I) base;            
        }
        catch(ClassCastException e){
            throw new DanubiaInterfaceNotFoundException("The interface '" + 
                    interfaceName + "' is not implemented by " + base.getClass() +
                    ". Detailed message: "+ e.getMessage());
        }
    }

    @Override
    protected void loadPrivateRecoveryState(ObjectInputStream oin) {
        throw new RuntimeException(
                "Recovery is not yet implemented for the DeepActor models.");
    }

    @Override
    protected void savePrivateRecoveryState(ObjectOutputStream oout) {
        throw new RuntimeException(
        "Recovery is not yet implemented for the DeepActor models.");
    }    
    
    // -------------------------------------------------------------------------
    // -- Private implementation
    // -------------------------------------------------------------------------

    private void addToResource(Properties p) {
        addFile(p, ActorsInitCfgKey, ActorsInit);
        addFile(p, PlansInitCfgKey, PlansInit);
        addFile(p, ActionsInitCfgKey, ActionsInit);
        
        addClassSet(p, ProxelSensorEventsCfgKey, ProxelSensorEvents);
        addClassSet(p, ActorSensorEventsCfgKey, ActorSensorEvents);
        addClassSet(p, ConstraintSensorEventsCfgKey, ConstraintSensorEvents);
        addClassSet(p, ConstraintsCfgKey, Constraints);
        
        addConfigParameter(p, PlansInitLoadCfgKey, PlansInitLoad);
    }
    
    private void addFile(Properties p, String configKey, String resourceKey) {
        String propValue = p.getProperty(configKey); 
        if( propValue != null) {
            String file = 
                propValue.replace('/', File.separatorChar);
            log.info("Adding file resource(s) <"+configKey+","+file+"> ...");
            resource.addFile(resourceKey, file);
        }
    }
    private void addClassSet(Properties p, String configKey, 
            String resourceKey) {
        String propValue = p.getProperty(configKey); 
        if( propValue != null) { 
            log.info("Adding class resource(s) <"+configKey+","+propValue+"> ...");
            resource.addClassSet(resourceKey, 
                    createSetFromListString(propValue));
        }
    }
    private Set<String> createSetFromListString(String propValue) {
        Set<String> strings = new HashSet<String>();
        StringTokenizer st = new StringTokenizer(propValue, ",");
        while (st.hasMoreTokens()) strings.add(st.nextToken().trim());
        return strings;        
    }
    private void addConfigParameter(Properties p, String configKey, String resourceKey) {
        boolean propValue = Boolean.parseBoolean(p.getProperty(configKey));
        log.info("Adding configuration entry <"+configKey+","+propValue+"> ...");
        resource.addConfigBoolean(resourceKey, propValue);
    }
}

/**
 * $Log: ActorModelCoreImpl.java,v $
 * Revision 1.19  2008/04/14 08:51:06  janisch
 * Catch ClassCastException in getImplementor and throw
 * DanubiaInterfaceNotFoundException instead
 *
 * Revision 1.18  2008/04/11 09:09:26  janisch
 * Bugfix with getImplementor: base was instantiated too late
 * (in plug-point init; now in constructor). Also constructor signature
 * changed and retrieves componentConfig directly from Danubia
 *
 * Revision 1.17  2007/11/28 12:55:29  janisch
 * Draw back release 2.1, fixed yet another bug with areaMetaData.
 *
 * Revision 1.16  2007/11/28 10:27:38  janisch
 * - Update to Danubia 2.1.1
 * - Bugfix AbstractActorModel: query amd not via proxeltable but from am
 * - Align and fix unit tests
 * - Eclipse compiler settings: ignore raw type warnings
 * - Resolved any other compiler warning
 *
 * Revision 1.15  2007/10/31 10:16:51  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.14  2007/10/29 10:44:49  janisch
 * Compiler warnings for generics.
 *
 * Revision 1.13  2007/10/29 10:22:22  janisch
 * Query for sim start instead of config read. Distinguish test and
 * production case in plug-in init.
 *
 * Revision 1.12  2007/10/29 09:29:35  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 * - Removed exception handling for removed exception types ModelComputeException ...
 * - Use new Danubia 2.0 base class AbstractProxel
 * - Even more details
 *   - attributes protected to allow for Danubia independent unit tests
 *   - added default constructor which triggers instantiation of the framework
 *   - removed constructor with "component parameters": is done in bindRequired now
 *   - aligned plug-in signatures to plug-points of the Danubia 2.0 AbstractModel
 *   - distinguish init: cfg and import may be set by subclass also. This is
 *     relevant for unit tests. If cfg and import are not set they are assigned using
 *     AbstractModel queries. This is the production code case.
 *   - removed call to export/store in plug-in init: this is done now by the Danubia
 *     framework, which initially calls init->provide->store?getData etc. see manual
 *     of Danubia 2.0
 *   - create concrete DeepActor model in init via reflection. Danubia creates an
 *     instance of the core impl and the core impl instantiates the model
 *   - actors.store is now part of the model.export, not of processExternalState, i.e.
 *     it is not executed in parallel to model export any more.
 *   - implementes plug-in getImplementor to return concrete DeepActor model instance
 *   - implements plug-ins for load/saveRecoveryData: throwing exceptions, since this
 *     is not yet properly implemented
 *
 * Revision 1.11  2006/08/16 13:58:12  janisch
 * Added configuration parameters of models' cfg file as
 * configuration resource.
 *
 * Revision 1.10  2006/08/01 16:31:35  janisch
 * Switched back to revision 1.8 of this file.
 *
 * Revision 1.8  2006/01/31 14:40:34  janisch
 * Removed PerformanceMeas attribute
 *
 * Revision 1.7  2006/01/27 13:15:37  janisch
 * - Added PerformanceMeas
 * - Moved actors.query to be first step of processCompute
 *
 * Revision 1.6  2005/12/22 14:31:41  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.5  2005/11/22 17:25:43  janisch
 * Modified debug messages.
 *
 * Revision 1.4  2005/11/10 12:38:04  janisch
 * Added info logs.
 *
 * Revision 1.3  2005/09/23 11:00:59  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:14:41  janisch
 * Release 1.0.0
 *
 */