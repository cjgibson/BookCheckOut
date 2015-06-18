package uncc.tables;

import java.sql.SQLException;

import uncc.HSQLDatabaseConnection;
import uncc.objects.Rental;

/**
 * Java handler used to communicate with an HSQL Database Table that stores 
 *   Rental objects.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @since   June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class RentalTable extends HSQLDatabaseTable<Rental> {
    public RentalTable(HSQLDatabaseConnection dbname) throws SQLException {
        super(dbname);
    }
}
