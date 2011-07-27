package org.glowa.danube.deepactors.actors.actor;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ActorInit.java,v 1.2 2006/01/31 14:37:45 janisch Exp $ 
 */
public class ActorInit {

    private int id;
    private Class<? extends AbstractActor> actorClass;
    private int[] location;
    private int[] collab;
    private int[] planIds;

    public ActorInit(int id, 
            Class<? extends AbstractActor> actorClass, 
                    int[] location, int[] collab,
            int[] planIds) {
        this.id = id;
        this.actorClass = actorClass;
        this.location = location;
        this.collab = collab;
        this.planIds = planIds;
    }
    public final Class<? extends AbstractActor> getActorClass() { 
        return actorClass; 
    }
    public final int[] getCollabIds() { return collab; }
    public final int getId() { return id; }
    public final int[] getPlanIds() { return planIds; }
    public final int[] getLocation() { return location; }
}
/**
 * $Log: ActorInit.java,v $
 * Revision 1.2  2006/01/31 14:37:45  janisch
 * Refactored generic type usage, introduced SuppressWarnings("unchecked")
 *
 * Revision 1.1  2005/08/26 11:15:49  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/11 17:34:36  janisch
 * Added cvs log.
 *
 */