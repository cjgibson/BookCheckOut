package uncc.tables;

import uncc.objects.Book;

public class BookTable extends HSQLDatabaseTable<Book> {
    public BookTable(String dbname) {
        super(dbname);
    }
}