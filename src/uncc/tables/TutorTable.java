package uncc.tables;

import java.sql.SQLException;

import uncc.HSQLDatabaseConnection;
import uncc.objects.Tutor;

/**
 * Java handler used to communicate with an HSQL Database Table that stores 
 *   Tutor objects.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @since   June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class TutorTable extends HSQLDatabaseTable<Tutor> {
    public TutorTable(HSQLDatabaseConnection dbname) throws SQLException {
        super(dbname);
    }
}
