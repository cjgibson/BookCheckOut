package uncc.objects;

import java.io.Serializable;

/**
 * Abstract base class used to enforce object standards across Java objects that
 *   will be used to create tables in our database. Primarily, the creation of
 *   a transient integer used to store the object's primary ID, associated
 *   "getter" and "setter" methods, and to require the implemnetation of Java's
 *   java.io.Serializable interface.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public abstract class HSQLDatabaseObject implements Serializable {
    /**
     * The primary ID of this HSQLDatabaseObject, and the serialVersionID as
     *   required by java.io.Serializable.
     */
    private static final long serialVersionUID = -1758407370011472754L;
    private transient int id;
    
    /**
     * @return The primary ID associated with this object in our central database.
     */
    public int getID() { return id; }
    
    /**
     * @param id The new primary ID associated with this object in our database.
     */
    public void setID(int id) { this.id = id; }
}
