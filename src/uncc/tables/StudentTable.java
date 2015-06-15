package uncc.tables;

import java.sql.SQLException;

import uncc.HSQLDatabaseConnection;
import uncc.objects.Student;

public class StudentTable extends HSQLDatabaseTable<Student> {
    public StudentTable(HSQLDatabaseConnection dbname) throws SQLException {
        super(dbname);
    }

}
