package org.glowa.danube.deepactors.actors.plan;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: PlanInit.java,v 1.2 2007/10/29 09:03:57 janisch Exp $ 
 */
public class PlanInit {

    private int id;
    private Class<?> planClass;
    private int[] actionIds;
    private float rating;
    
    PlanInit(int id, Class<?> planClass, int[] actionIds, float rating) {
        this.id = id;
        this.planClass = planClass;
        this.actionIds = actionIds;
        this.rating = rating;
    }
    int[] getActionIds() {return actionIds;}
    int getId() {return id;}
    Class<?> getPlanClass() {return planClass;}
    float getRating() {return rating;}
    
}
/**
 * $Log: PlanInit.java,v $
 * Revision 1.2  2007/10/29 09:03:57  janisch
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.1  2005/08/26 11:16:26  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */