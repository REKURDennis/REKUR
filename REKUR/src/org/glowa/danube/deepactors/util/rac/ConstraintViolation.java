package org.glowa.danube.deepactors.util.rac;

/**
 * ToDo: javadoc.
 * 
 * @author janisch
 * @version $Id: ConstraintViolation.java,v 1.1 2005/08/26 11:17:22 janisch Exp $ 
 */
public class ConstraintViolation extends AssertionError {

    public ConstraintViolation(Object detailMessage) {
        super(detailMessage);
    }
}
/**
 * $Log: ConstraintViolation.java,v $
 * Revision 1.1  2005/08/26 11:17:22  janisch
 * Release 1.0.0
 *
 * Revision 1.2  2005/01/12 07:39:12  janisch
 * Added cvs log.
 *
 */