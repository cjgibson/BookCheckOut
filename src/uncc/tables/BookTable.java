package uncc.tables;

import java.sql.SQLException;

import uncc.HSQLDatabaseConnection;
import uncc.objects.Book;

public class BookTable extends HSQLDatabaseTable<Book> {
    public BookTable(HSQLDatabaseConnection dbname) throws SQLException {
        super(dbname);
    }
}