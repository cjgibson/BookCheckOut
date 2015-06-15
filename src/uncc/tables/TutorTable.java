package uncc.tables;

import java.sql.SQLException;

import uncc.HSQLDatabaseConnection;
import uncc.objects.Tutor;

public class TutorTable extends HSQLDatabaseTable<Tutor> {
    public TutorTable(HSQLDatabaseConnection dbname) throws SQLException {
        super(dbname);
    }
}
