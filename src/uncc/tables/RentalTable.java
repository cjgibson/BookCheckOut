package uncc.tables;

import java.sql.SQLException;

import uncc.HSQLDatabaseConnection;
import uncc.objects.Rental;

public class RentalTable extends HSQLDatabaseTable<Rental> {
    public RentalTable(HSQLDatabaseConnection dbname) throws SQLException {
        super(dbname);
    }
}
