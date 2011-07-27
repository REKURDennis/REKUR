package org.glowa.danube.deepactors;

import org.glowa.danube.deepactors.actors.ActorsManager;
import org.glowa.danube.deepactors.model.ActorModelCoreImpl;
import org.glowa.danube.deepactors.model.ActorModelFactory;
import org.glowa.danube.deepactors.resmgt.ResourceManagementFactory;
import org.glowa.danube.deepactors.sensors.SensorsManager;
import org.glowa.danube.deepactors.util.DeepActorLogger;
import org.glowa.danube.deepactors.util.comp.AbstractComponentManager;
import org.glowa.danube.deepactors.util.comp.ComponentManager;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: DeepActorManager.java,v 1.4 2007/10/31 10:16:51 janisch Exp $ 
 */
public class DeepActorManager
extends AbstractComponentManager
implements ComponentManager{

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(DeepActorManager.class);

    // Package acces to configured components, e.g. for testing purposes 
    ActorModelFactory actorModelPrinciple;
    ResourceManagementFactory resourcePrinciple;
    SensorsManager sensorsContainer;
    ActorsManager actorsContainer;
    ModelProxy modelProxy;
    ActorsProxy actorsProxy;
    ASCProxy ascProxy;
    PSCProxy pscProxy;
    CSCProxy cscProxy;
    
    
    public DeepActorManager(ActorModelCoreImpl actorModelCore, 
            ClassLoader danubiaClassLoader) {
        log.info("Instantiate DeepActor container ...");
        createAndBind(actorModelCore, danubiaClassLoader);
    }
    
    private void createAndBind(ActorModelCoreImpl actorModelCore, 
            ClassLoader danubiaClassLoader) {
        sensorsContainer = new SensorsManager();
        actorsContainer = new ActorsManager();

        // query container for provided interfaces 
        Class<org.glowa.danube.deepactors.actors.Actors> actorsInterface = 
            org.glowa.danube.deepactors.actors.Actors.class;
        Class<org.glowa.danube.deepactors.actors.ActorsControl> actorsControlInterface = 
            org.glowa.danube.deepactors.actors.ActorsControl.class;
        Class<org.glowa.danube.deepactors.sensors.SensorsControl> sensorsControlInterface = 
            org.glowa.danube.deepactors.sensors.SensorsControl.class;
        Class<org.glowa.danube.deepactors.sensors.SensorQueries> sensorQueriesInterface = 
            org.glowa.danube.deepactors.sensors.SensorQueries.class;

        // create and bind principles
        resourcePrinciple = new ResourceManagementFactory(danubiaClassLoader);
        
        modelProxy = new ModelProxy(resourcePrinciple.getResourceAdmin(),
                    sensorsContainer.getProvided(sensorsControlInterface),
                    actorsContainer.getProvided(actorsInterface),
                    actorsContainer.getProvided(actorsControlInterface));
        actorModelPrinciple = 
            new ActorModelFactory(modelProxy, modelProxy, modelProxy, 
                    actorModelCore);
        
        // bind container
        actorsProxy = new ActorsProxy(
                    resourcePrinciple.getResourceAllocator(),
                    actorModelPrinciple.getTimeQuery(),
                    sensorsContainer.getProvided(sensorQueriesInterface));
        actorsContainer.bindRequired(org.glowa.danube.deepactors.actors.ResourceAllocator.class, actorsProxy);
        actorsContainer.bindRequired(org.glowa.danube.deepactors.actors.TimeQuery.class, actorsProxy);
        actorsContainer.bindRequired(org.glowa.danube.deepactors.actors.Sensors.class, actorsProxy);
        
        ascProxy = 
            new ASCProxy(resourcePrinciple.getResourceAllocator(),
                    actorsContainer.getProvided(actorsInterface));
        sensorsContainer.bindRequired(org.glowa.danube.deepactors.sensors.ActorSensorContext.class, ascProxy);
        
        cscProxy = 
            new CSCProxy(resourcePrinciple.getResourceAllocator());
        sensorsContainer.bindRequired(org.glowa.danube.deepactors.sensors.ConstraintSensorContext.class, cscProxy);
        
        pscProxy = 
            new PSCProxy(resourcePrinciple.getResourceAllocator(),
                    actorModelPrinciple.getActorModelCore());
        sensorsContainer.bindRequired(org.glowa.danube.deepactors.sensors.ProxelSensorContext.class, pscProxy);
        
        log.info("Finished DeepActor-Framework component configuration.");
    }

    public boolean isBound(){
        // isBound for principle components is always true 
        return super.isBound() && actorsContainer.isBound() 
        && sensorsContainer.isBound();
    }
}

/**
 * $Log: DeepActorManager.java,v $
 * Revision 1.4  2007/10/31 10:16:51  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.3  2007/10/29 08:02:20  janisch
 * Receive and assign instance of ActorCoreModelImpl instead of
 * AAM. This is due to the dedicated DeepActor instantantiation in Danubia 2.0.1
 *
 * Revision 1.2  2005/09/08 08:12:58  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:16:10  janisch
 * Release 1.0.0
 *
 */