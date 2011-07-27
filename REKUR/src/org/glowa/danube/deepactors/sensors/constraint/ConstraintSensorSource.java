package org.glowa.danube.deepactors.sensors.constraint;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.glowa.danube.deepactors.actors.actor.ConstraintProperties;
import org.glowa.danube.deepactors.actors.constraint.Constraint;
import org.glowa.danube.deepactors.sensors.ConstraintSensorContext;
import org.glowa.danube.deepactors.sensors.sensor.SensorSource;
import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ConstraintSensorSource.java,v 1.2 2007/10/29 09:32:58 janisch Exp $ 
 */
public class ConstraintSensorSource implements SensorSource {

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ConstraintSensorSource.class);

    private Set<Constraint<ConstraintProperties>> constraints = Collections.emptySet();
    private ConstraintSensorContext context;

    public ConstraintSensorSource(ConstraintSensorContext csc) {
        context = csc;
    }
    
    @SuppressWarnings("unchecked")
    public void update() {
        constraints = new HashSet<Constraint<ConstraintProperties>>();
        Set<Class<?>> constraintClasses = context.getConstraintClasses();
        for(Class<?> cc:constraintClasses) {
            Constraint<ConstraintProperties> c = 
                createConstraint((Class<Constraint<ConstraintProperties>>) cc);
            if(c != null) constraints.add(c);
            else log.warn("Ignoring" + cc.getSimpleName() + "...");
        }
    }
    private Constraint<ConstraintProperties> createConstraint(
            Class<Constraint<ConstraintProperties>> cc) {
        String warnMes = "Failed to create Constraint " + cc.getSimpleName();
        try { return cc.newInstance(); } 
        catch(InstantiationException e) { log.exception(warnMes, e); } 
        catch(IllegalAccessException e) { log.exception(warnMes, e); }
        catch(ClassCastException e){ log.exception(warnMes, e); }
        return null;
    }



    /**
     * ToDo: ..
     * 
     * Note: protected because usage is restricted to the 
     * corresponding sensor implementation package
     */
    Set<Constraint<ConstraintProperties>> getConstraints() {
        return constraints;
    }
}
/**
 * $Log: ConstraintSensorSource.java,v $
 * Revision 1.2  2007/10/29 09:32:58  janisch
 * - Aligned package imports to Danubia 2.0 within almost all framework classes
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.1  2005/08/26 11:15:29  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */