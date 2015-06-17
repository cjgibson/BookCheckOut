package uncc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import uncc.objects.Book;
import uncc.objects.HSQLDatabaseObject;
import uncc.objects.Rental;
import uncc.objects.Student;
import uncc.tables.BookTable;
import uncc.tables.HSQLDatabaseTable;
import uncc.tables.RentalTable;
import uncc.tables.StudentTable;
import uncc.tables.TutorTable;

/**
 * <p>Simple wrapper class intended to provide simplified access to HyperSQL's
 * Java connector specifically for the database schema utilized in this project.</p>
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 13th, 2015
 * @isodate 2015-06-13T14:00:00-04:00
 */

public class HSQLDatabaseConnection {
    /**
     * 
     */
    private static final String DBPATH = "jdbc:hsqldb:file:";
    private static final String DBUSER = "SA";
    private static final String DBPASS = "";
    private String dbname;
    private Connection dbconn;
    private static HSQLDatabaseConnection instance;
    private static HSQLDatabaseTable<?> books, students, rentals, tutors;
    
    /**
     * @param dbname
     * @throws SQLException
     */
    public HSQLDatabaseConnection(String dbname) throws SQLException {
        this.dbname = dbname;
        HSQLDatabaseConnection.instance = this;
        initializeDriver();
        initializeTables();
    }
    
    /**
     * @return
     */
    public Book[] getAllBooks() {
        return (Book[]) this.books.getAll();
    }
    
    /**
     * @param isbn
     * @return
     */
    public Book addNewBook(String isbn) {
        try {
            Book book = ISBNLookup.Lookup(isbn);
            this.books.insert(book);
            return book;
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * @param book
     * @return
     */
    public boolean removeBook(Book book) {
        return this.books.delete(book);
    }
    
    /**
     * @param student
     * @return
     */
    public Student addNewStudent(Student student) {
        this.students.insert(student);
        return student;
    }
    
    /**
     * @param student
     * @return
     */
    public boolean removeStudent(Student student) {
        return this.students.delete(student);
    }
    
    /**
     * @param student
     * @param book
     * @return
     */
    public Rental addNewRental(Student student, Book book) {
        Rental rental = new Rental(student, book);
        this.rentals.insert(rental);
        return rental;
    }
    
    /**
     * @param rental
     * @return
     */
    public boolean removeRental(Rental rental) {
        return this.rentals.delete(rental);
    }
    
    /**
     * 
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
     * @throws SQLException
     */
    private static void initializeTables() throws SQLException {
        HSQLDatabaseConnection.books = new BookTable(instance);
        HSQLDatabaseConnection.students = new StudentTable(instance);
        HSQLDatabaseConnection.rentals = new RentalTable(instance);
        HSQLDatabaseConnection.tutors = new TutorTable(instance);
    }
    
    /**
     * @return
     */
    public Connection getConnection() {
        return this.dbconn;
    }
}
