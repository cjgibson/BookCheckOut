package uncc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.stream.Stream;
import org.hsqldb.*;

/**
 * <p>Simple wrapper class intended to provide simplified access to HyperSQL's
 * Java connector specifically for the database schema utilized in this project.</p>
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 13th, 2015
 * @isodate 2015-06-13T14:00:00-04:00
 */

public class HSQLDatabaseConnector {
    private Connection dbconn;
    private static final String[] DATABASE_RESET = {
            "DROP TABLE IF EXISTS Rentals",
            "DROP TABLE IF EXISTS Books"};
    private static final String[] DATABASE_DEFAULTS = {
            "SET DATABASE DEFAULT TABLE TYPE MEMORY"};
    private static final String[] CREATE_TABLE = {"CREATE TABLE Books ("
            + "  isbn13         NUMERIC(13)  NOT NULL,"
            + "  isbn10         NUMERIC(10),"
            + "  title          VARCHAR(200),"
            + "  author         VARCHAR(200),"
            + "  publisher      VARCHAR(200),"
            + "  published_date DATE,"
            + "  PRIMARY KEY (isbn13)"
            + ")", "CREATE TABLE Rentals ("
            + "  student_name   VARCHAR(60)  NOT NULL,"
            + "  rental_time    DATE         NOT NULL,"
            + "  rental_due     DATE         NOT NULL,"
            + "  isbn13         NUMERIC(13)  NOT NULL,"
            + "  CONSTRAINT legal_due_date CHECK (rental_time < rental_due),"
            + "  FOREIGN KEY (isbn13) REFERENCES Books (isbn13),"
            + "  PRIMARY KEY (student_name, isbn13)"
            + ")"};
    private static final String[] DATABASE_GENERATE = Stream
            .of(DATABASE_RESET, DATABASE_DEFAULTS, CREATE_TABLE)
            .flatMap(Stream::of)
            .toArray(String[]::new);

    public HSQLDatabaseConnector() throws SQLException {
        this.dbconn = DriverManager.getConnection(
                "jdbc:hsqldb:file:bookdb", "SA", "");
    }
    
    private void GenerateDefaultTables() throws SQLException {
        Statement tableGen = this.dbconn.createStatement();
        for (String stmt : DATABASE_GENERATE) {
            tableGen.execute(stmt);
        }
    }
}
