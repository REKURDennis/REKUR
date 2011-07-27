package org.glowa.danube.deepactors.actors.exec;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: MemberInvocation.java,v 1.3 2005/12/22 14:31:41 janisch Exp $ 
 */
public interface MemberInvocation {

    void invoke(String methodName) throws Exception;
}

/**
 * $Log: MemberInvocation.java,v $
 * Revision 1.3  2005/12/22 14:31:41  janisch
 * Declared the DANUBIA exceptions ComponentInitException, ModelComputeException and ModelCommitException to be throwable in plug-ins.
 *
 * Revision 1.2  2005/09/08 08:12:57  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:35  janisch
 * Release 1.0.0
 *
 */