package uncc.tables;

import java.sql.SQLException;

import uncc.HSQLDatabaseConnection;
import uncc.objects.Student;

/**
 * Java handler used to communicate with an HSQL Database Table that stores 
 *   Student objects.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @since   June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class StudentTable extends HSQLDatabaseTable<Student> {
    public StudentTable(HSQLDatabaseConnection dbname) throws SQLException {
        super(dbname);
    }

}
