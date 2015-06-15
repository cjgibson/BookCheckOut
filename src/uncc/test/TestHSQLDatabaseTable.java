package uncc.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import uncc.HSQLDatabaseConnection;
import uncc.tables.BookTable;
import uncc.tables.RentalTable;
import uncc.tables.StudentTable;
import uncc.tables.TutorTable;

public class TestHSQLDatabaseTable {

    @Test
    public void test() {
        try {
            HSQLDatabaseConnection conn = new HSQLDatabaseConnection("bookdb");
            BookTable book = new BookTable(conn);
            StudentTable student = new StudentTable(conn);
            RentalTable rental = new RentalTable(conn);
            TutorTable tutor = new TutorTable(conn);
        } catch (SQLException e) {
            fail();
        }
    }

}
