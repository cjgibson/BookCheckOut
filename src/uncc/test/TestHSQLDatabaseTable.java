package uncc.test;

import static org.junit.Assert.*;

import org.junit.Test;

import uncc.tables.BookTable;

public class TestHSQLDatabaseTable {

    @Test
    public void test() {
        BookTable test = new BookTable("testdb");
    }

}
