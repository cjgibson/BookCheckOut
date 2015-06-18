package uncc.objects;

import java.util.Date;

/**
 * Object that holds information about a book Rental.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @since   June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class Rental extends HSQLDatabaseObject {
    /**
     * The serialVersionID required by java.io.Serializable.
     */
    private static final long serialVersionUID = 8122977511621486765L;
    
    /**
     * Fields used by the Rental object:
     *   Student student: The student renting the book.
     *   Tutor tutor: The tutor who rented the book to the student.
     *   Book book: The book that was rented to the student.
     *   Date dueDate: The date the rental is due back to the tutor.
     *   Date rentalDate: The date the rental was made.
     */
    private Student student;
    private Tutor tutor;
    private Book book;
    private Date dueDate, rentalDate;
    
    /**
     * Constructor for the Rental object. It is assumed that this book has no
     *   concrete due date, and that no tutor is in charge of monitoring this
     *   rental.
     * 
     * @param student The student the book is rented to.
     * @param book The book the student rented.
     */
    public Rental(Student student, Book book) {
        this.student = student;
        this.book = book;
        this.dueDate = null;
        this.rentalDate = new Date();
    }
    
    /**
     * Constructor for the Rental object. It is assumed that this book has no
     *   concrete due date.
     * 
     * @param student The student the book is rented to.
     * @param book The book the student rented.
     * @param tutor The tutor who oversees this rental.
     */
    public Rental(Student student, Book book, Tutor tutor) {
        this(student, book);
        this.tutor = tutor;
    }
    
    /**
     * Constructor for the Rental object. It is assumed that no tutor is in
     *   charge of monitoring this rental.
     * 
     * @param student The student the book is rented to.
     * @param book The book the student rented.
     * @param dueDate The date the book is due back to the tutoring center.
     */
    public Rental(Student student, Book book, Date dueDate) {
        this(student, book);
        this.dueDate = dueDate;
    }
    
    /**
     * Constructor for the Rental object.
     * 
     * @param student The student the book is rented to.
     * @param book The book the student rented.
     * @param dueDate The date the book is due back to the tutoring center.
     * @param tutor The tutor who oversees this rental.
     */
    public Rental(Student student, Book book, Date dueDate, Tutor tutor) {
        this(student, book, dueDate);
        this.tutor = tutor;
    }
    
    /**
     * @return The student who rented the book.
     */
    public Student getStudent() { return this.student; }
    
    /**
     * @return The tutor who is overseeing this rental.
     */
    public Tutor getTutor() { return this.tutor; }
    
    /**
     * @return The book involved in this rental.
     */
    public Book getBook() { return this.book; }
    
    /**
     * @return The date the book is due back to the tutoring center.
     */
    public Date getDueDate() { return this.dueDate; }
    
    /**
     * @return The date this rental occurred.
     */
    public Date getRentalDate() { return this.rentalDate; }
    
    /**
     * @param dueDate The new dueDate for this rental.
     */
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
}
