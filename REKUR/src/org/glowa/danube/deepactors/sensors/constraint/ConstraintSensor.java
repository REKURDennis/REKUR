package org.glowa.danube.deepactors.sensors.constraint;

import java.util.Set;

import org.glowa.danube.deepactors.actors.actor.Actor;
import org.glowa.danube.deepactors.actors.actor.ConstraintProperties;
import org.glowa.danube.deepactors.actors.constraint.Constraint;
import org.glowa.danube.deepactors.sensors.ConstraintSensorContext;
import org.glowa.danube.deepactors.sensors.sensor.AbstractSensor;
import org.glowa.danube.deepactors.sensors.sensor.ComputeDataMap;
import org.glowa.danube.deepactors.util.DataMap;
import org.glowa.danube.deepactors.util.MutableDataMap;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ConstraintSensor.java,v 1.3 2007/11/28 10:27:38 janisch Exp $ 
 */
public final class ConstraintSensor 
    extends AbstractSensor<Constraint<ConstraintProperties>, ConstraintSensorSource> 
    implements ComputeDataMap<Constraint<ConstraintProperties>,ConstraintSensorSource>{

    public ConstraintSensor(ConstraintSensorContext csc) {
        super.bind(new ConstraintSensorSource(csc), this, csc);
    }
    /**
     * ToDo: ...
     * 
     * Note: the actor does not yet play any role for the compuation of
     * the data map. All actors always get the whole set of available constraints.
     */
    public DataMap<Constraint<ConstraintProperties>> compute(ConstraintSensorSource src, Actor actor){
        // ToDo: return only those constraints which "fit" to the requesting actor,
        // fit may mean, the actor implements the required ConstraintProperties
        Set<Constraint<ConstraintProperties>> constraints = 
            src.getConstraints();
        MutableDataMap<Constraint<ConstraintProperties>> resultMap = 
            new MutableDataMap<Constraint<ConstraintProperties>>();
        for(Constraint<ConstraintProperties> c : constraints) { resultMap.add(c); }
        return resultMap;
    }

    
}
/**
 * $Log: ConstraintSensor.java,v $
 * Revision 1.3  2007/11/28 10:27:38  janisch
 * - Update to Danubia 2.1.1
 * - Bugfix AbstractActorModel: query amd not via proxeltable but from am
 * - Align and fix unit tests
 * - Eclipse compiler settings: ignore raw type warnings
 * - Resolved any other compiler warning
 *
 * Revision 1.2  2007/10/29 09:32:58  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.1  2005/08/26 11:15:30  janisch
 * Release 1.0.0
 *
 * Revision 1.3  2005/02/10 13:41:24  janisch
 * Renamed CoreSensor to AbstractSensor and made it abstract.
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */