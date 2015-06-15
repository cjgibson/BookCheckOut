package uncc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * <p>Simple wrapper class intended to provide simplified access to HyperSQL's
 * Java connector specifically for the database schema utilized in this project.</p>
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 13th, 2015
 * @isodate 2015-06-13T14:00:00-04:00
 */

public class HSQLDatabaseConnection {
    private static final String DBPATH = "jdbc:hsqldb:file:";
    private String dbname;
    private Connection dbconn;
    
    public HSQLDatabaseConnection(String dbname) {
        this.dbname = dbname;
        initializeDriver();
    }
    
    private void initializeDriver() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            this.dbconn = DriverManager.getConnection(
                    DBPATH + this.dbname, "SA", "");
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
    
    public Connection getConnection() {
        return this.dbconn;
    }
}
