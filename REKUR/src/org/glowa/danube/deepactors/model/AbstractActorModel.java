package org.glowa.danube.deepactors.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.glowa.danube.components.DanubiaInterface;
import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.InterfaceImpl;
import org.glowa.danube.deepactors.util.MutableDataMap;
import org.glowa.danube.metadata.AreaMetaData;
import org.glowa.danube.metadata.ComponentMetaData;
import org.glowa.danube.simulation.model.proxel.AbstractProxel;
import org.glowa.danube.simulation.model.proxel.ProxelTable;
import org.glowa.danube.utilities.datacheck.ICheckFailedLogger;
import org.glowa.danube.utilities.execution.ParallelProxelIterator;
import org.glowa.danube.utilities.execution.ProxelIterator;
import org.glowa.danube.utilities.execution.ProxelIteratorFactory;
import org.glowa.danube.utilities.execution.SequentialProxelIterator;
import org.glowa.danube.utilities.internal.DanubiaLogger;
import org.glowa.danube.utilities.time.DanubiaCalendar;
import org.glowa.danube.utilities.visualization.LocalVisualization;

/**
 * Base class for the implementation of DeepActor models.
 * 
 * @author janisch
 * @version $Id: AbstractActorModel.java,v 1.24 2007/11/28 12:55:29 janisch Exp $ 
 */
public abstract class AbstractActorModel<P extends AbstractProxel>{
    
    // Links to impls of req interfaces
    private TimeQuery timeQuery;
    private Set<Actor> actors;
    
    // Assuming frozen set of actors => cache after first request.
    private DataMap<Actor> actorMap;
    
    // For queries related to those of AbstractComponent
    private Map<String,DanubiaInterface> importInterfRefMap = 
        new HashMap<String,DanubiaInterface>();
    private DanubiaLogger logger;
    private ComponentMetaData metaData;
    private AreaMetaData areaMetaData;
    
    // Now those related to AbstractModel
    private ICheckFailedLogger checkFailedLogger;
    private LocalVisualization locVis;
    private ProxelTable<P> proxelTable;
    private ProxelIterator proxelIterator; 
    private String modeloutpath;
    private String timeseriespath;
    
    // provide runtime references setter for coreImpl (component req)
    void setRef(Set<Actor> a, TimeQuery tq) {
        timeQuery = tq;
        actors = a;
    }
    // provide setter for state originally from AbstractComponent/Model
    void setAbstractComponentAttributes(Set<InterfaceImpl> is,
            DanubiaLogger l, ComponentMetaData c, AreaMetaData amd){
        for(InterfaceImpl i:is){
            importInterfRefMap.put(i.getInterfName(), i.getInterfImpl());
        }
        logger = l;
        metaData = c;
        areaMetaData = amd;
    }
    @SuppressWarnings("unchecked")
    void setAbstractModelAttributes(ICheckFailedLogger cfl,
        LocalVisualization lv, ProxelTable<AbstractProxel> pt, 
        String mop, String tsp){
        checkFailedLogger = cfl;
        locVis = lv;
        proxelTable = (ProxelTable<P>) pt;
        modeloutpath = mop;
        timeseriespath = tsp;
        // Note: proxelIterator is newly created on each request
    }

    // ------------------------------------------------------------------------    
    // Attribute access for subclasses
    // ------------------------------------------------------------------------
    // some, but not all queries from AbstractComponent
    @SuppressWarnings("unchecked")
    protected final <I extends DanubiaInterface> I getImport(
            String interfaceName) {
        DanubiaInterface d = importInterfRefMap.get(interfaceName);
        if (d==null) {
            throw new RuntimeException("Interface: " + 
                    interfaceName + " not in import map.");
        }
        return (I) d;
    }
    protected final DanubiaLogger logger() { return logger; }
    protected final ComponentMetaData componentConfig() { return metaData; }

    // there are also some queries from AbstractModel
    protected final ProxelTable<P> proxelTable() { return proxelTable; }
    protected final P proxel(int pid) { return proxelTable().getProxel(pid); }
    protected final String modeloutpath() { return modeloutpath; }
    protected final String timeseriespath() { return timeseriespath; }
    protected final ICheckFailedLogger checkFailedLogger(){ return checkFailedLogger; }
    protected final LocalVisualization localVisualization() { return locVis; }
    protected final int[] pids() { return proxelTable().insidePIDs(); }
    protected final AreaMetaData areaMetaData() { return areaMetaData; }
    
    // Finally, the additional queries for deepactor models 
    protected final DataMap<Actor> actorMap(){
        // Assumes frozen set of actors => create data map only once
        if(actorMap == null) {
            MutableDataMap<Actor> mdm = new MutableDataMap<Actor>();
            for(Actor a:actors) mdm.add(a);
            actorMap = mdm;
        }
        return actorMap; 
    }
    protected final DanubiaCalendar simulationTime() { 
        return timeQuery.getSimulationTime(); 
    }

    // ------------------------------------------------------------------------    
    // AbstractModel allows also for some modifier! 
    // ------------------------------------------------------------------------
    protected final void computeProxelsParallel(DanubiaCalendar t, Object data) {
        if (!(proxelIterator instanceof ParallelProxelIterator))
            proxelIterator = ProxelIteratorFactory.createParallelProxelIterator();
        proxelIterator.compute(proxelTable, t, data);   
    }
    protected final void computeProxelsSequential(DanubiaCalendar t, Object data) {
        if (!(proxelIterator instanceof SequentialProxelIterator))
            proxelIterator = ProxelIteratorFactory.createSequentialProxelIterator();
        proxelIterator.compute(proxelTable, t, data);
    }
    
    // ------------------------------------------------------------------------    
    // Plug points/Plug ins: 
    // ------------------------------------------------------------------------
    protected void init(){}
    protected void getData(){}
    protected void preCompute(){}
    protected void postCompute(){}
    protected void commit(){}
    protected void store() {}
    protected void cleanup() {}
}

/**
 * $Log: AbstractActorModel.java,v $
 * Revision 1.24  2007/11/28 12:55:29  janisch
 * Draw back release 2.1, fixed yet another bug with areaMetaData.
 *
 * Revision 1.23  2007/11/28 10:27:38  janisch
 * - Update to Danubia 2.1.1
 * - Bugfix AbstractActorModel: query amd not via proxeltable but from am
 * - Align and fix unit tests
 * - Eclipse compiler settings: ignore raw type warnings
 * - Resolved any other compiler warning
 *
 * Revision 1.22  2007/11/06 07:24:33  janisch
 * Reformat source
 *
 * Revision 1.21  2007/10/29 09:14:48  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 * - Removed exception handling for removed exception types ModelComputeException ...
 * - Use new Danubia 2.0 base class AbstractProxel
 * - Even more details:
 *   - introduced state (attributes) and queries related to queries available in
 *     AbstractComponent/AbstractModel of Danubia 2.0
 *   - removed plug-point getProxelTable - this is implemented by the Danubia
 *     framework now
 *   - removed constructors AbstractActorModel()/AbstractActorModel(ComponentConfig)
 *     which triggered instantiation of the DeepActor framework. Now this is done in
 *     the constructor of ActorCoreModelImpl, which is called by the Danubia framework.
 *     Note that the component config is available as a query now.
 *   - added type parameter for proxel type analogously to AbstractModel
 *
 * Revision 1.20  2005/12/22 14:31:41  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.19  2005/09/23 11:00:59  janisch
 * Dropped package util.dt and moved util.dt.* to util
 *
 * Revision 1.18  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.17  2005/08/26 11:14:40  janisch
 * Release 1.0.0
 *
 */