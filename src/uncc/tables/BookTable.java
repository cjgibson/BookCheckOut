package uncc.tables;

import java.sql.SQLException;

import uncc.HSQLDatabaseConnection;
import uncc.objects.Book;

/**
 * Java handler used to communicate with an HSQL Database Table that stores 
 *   Book objects.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class BookTable extends HSQLDatabaseTable<Book> {
    public BookTable(HSQLDatabaseConnection dbname) throws SQLException {
        super(dbname);
    }
}