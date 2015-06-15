package uncc.objects;

import java.io.Serializable;

public abstract class HSQLDatabaseObject implements Serializable {
    private static final long serialVersionUID = -1758407370011472754L;
    private transient int id;
    public int getID() { return id; }
    public void setID(int id) { this.id = id; }
}
