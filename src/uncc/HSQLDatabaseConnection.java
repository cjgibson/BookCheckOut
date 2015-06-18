package uncc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import uncc.objects.Book;
import uncc.objects.Rental;
import uncc.objects.Student;
import uncc.tables.BookTable;
import uncc.tables.HSQLDatabaseTable;
import uncc.tables.RentalTable;
import uncc.tables.StudentTable;
import uncc.tables.TutorTable;

/**
 * <p>Simple wrapper class intended to provide high-level access to HyperSQL's
 * Java connector specifically for the database schema utilized in this project.</p>
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class HSQLDatabaseConnection {
    /**
     * Fields utilized by the HSQLDatabaseConnection object.
     * 
     * Three static final strings used for database configuration:
     *   DBPATH: The path to the HSQL instance used to store information.
     *   DBUSER: The user used to authenticate with the database.
     *   DBPASS: The password used to authenticate with the database.
     * 
     * Additionally, several fields created by the object's constructor.
     *   String dbname: The english name of the database.
     *   Connection dbconn: The java.sql.Connection object used to connect to
     *     the HSQL database instance.
     *   static HSQLDatabaseConnection instance: A static object used to ensure
     *     this class is a singleton, so as not to unnecessarily overwhelm the
     *     central database with multiple connections per program instance.
     *   static HSQLDatabaseTable<?> ... : The tables utilized by this
     *     HSQLDatabaseConnection. Again, instantiated as static objects to
     *     ensure uniqueness of the generated primary keys in use by each table
     *     and to not needlessly create multiple table objects for a single
     *     table in our central database.
     */
    private static final String DBPATH = "jdbc:hsqldb:file:";
    private static final String DBUSER = "SA";
    private static final String DBPASS = "";
    private String dbname;
    private Connection dbconn;
    private static HSQLDatabaseConnection instance;
    private static HSQLDatabaseTable<?> books, students, rentals, tutors;
    
    /**
     * Constructor for the HSQLDatabaseConnection object.
     * 
     * @param dbname English name for the database instance this will provide
     *          a connection to.
     * @throws SQLException In the case that the database cannot be found,
     *          or one of the created HSQLDatabaseTable objects encounters an
     *          error during instantiation.
     */
    public HSQLDatabaseConnection(String dbname) throws SQLException {
        this.dbname = dbname;
        HSQLDatabaseConnection.instance = this;
        initializeDriver();
        initializeTables();
    }
    
    /**
     * Get an array of all uncc.objects.Book objects found in our Book table.
     * 
     * @return The array of book objects.
     */
    public Book[] getAllBooks() {
        return (Book[]) HSQLDatabaseConnection.books.getAll();
    }
    
    /**
     * Stores an instance of uncc.objects.Book into our Book table.
     * 
     * @param book The book instance to be stored in the database.
     * @return The book instance as it was stored in the database, with its
     *          transient ID field updated to reflect its associated primary
     *          key in the database.
     */
    public Book addNewBook(Book book) {
        HSQLDatabaseConnection.books.insert(book);
        return book;
    }
    
    /**
     * Generates a uncc.objects.Book object from an ISBN string, and stores it
     *   in our Book table.
     * 
     * @param isbn The ISBN of the book we want to insert.
     * @return The book instance as it appears in the database.
     */
    public Book addNewBook(String isbn) {
        try {
            Book book = ISBNLookup.Lookup(isbn);
            HSQLDatabaseConnection.books.insert(book);
            return book;
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Deletes a uncc.objects.Book instance from our database.
     * 
     * @param book The book we want to remove from the database.
     * @return True if the book was removed successfully, False otherwise.
     */
    public boolean removeBook(Book book) {
        return HSQLDatabaseConnection.books.delete(book);
    }
    
    /**
     * Adds a uncc.objects.Student instance to the database.
     * 
     * @param student The student instance we want to add to the database.
     * @return The student object as it appears in the database, with its
     *          transient ID field updated to reflect its associated primary
     *          key in the database. 
     */
    public Student addNewStudent(Student student) {
        HSQLDatabaseConnection.students.insert(student);
        return student;
    }
    
    /**
     * Removes a uncc.objects.Student instance from the database.
     * 
     * @param student The student instance we want to remove from the database.
     * @return True if the object was removed successfully, False otherwise.
     */
    public boolean removeStudent(Student student) {
        return HSQLDatabaseConnection.students.delete(student);
    }
    
    /**
     * Adds a uncc.objects.Rental instance to our database.
     * 
     * @param student The uncc.objects.Student object that represents the
     *          student who is renting a book.
     * @param book The uncc.objects.Book object that represents the book being
     *          rented from the tutoring center.
     * @return A new uncc.objects.Rental object as it appears in the database.
     */
    public Rental addNewRental(Student student, Book book) {
        Rental rental = new Rental(student, book);
        HSQLDatabaseConnection.rentals.insert(rental);
        return rental;
    }
    
    /**
     * Removes a uncc.objects.Rental object from the database.
     * 
     * @param rental The Rental object we want to remove from the database.
     * @return True if the object was removed successfully, False otherwise.
     */
    public boolean removeRental(Rental rental) {
        return HSQLDatabaseConnection.rentals.delete(rental);
    }
    
    /**
     * Initializes the HSQL Database driver, contained in org.hsqldb.jdbcDriver.
     */
    private void initializeDriver() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            this.dbconn = DriverManager.getConnection(
                    DBPATH + this.dbname, DBUSER, DBPASS);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
    
    /**
     * Initializes the database tables used by this program.
     * 
     * @throws SQLException If the program encounters an SQLException while
     *          initializing any of the HSQLDatabaseTable objects.
     */
    private static void initializeTables() throws SQLException {
        HSQLDatabaseConnection.books = new BookTable(instance);
        HSQLDatabaseConnection.students = new StudentTable(instance);
        HSQLDatabaseConnection.rentals = new RentalTable(instance);
        HSQLDatabaseConnection.tutors = new TutorTable(instance);
    }
    
    /**
     * @return The java.sql.Connection object used to connect to our central
     *          database instance.
     */
    public Connection getConnection() {
        return this.dbconn;
    }
}
