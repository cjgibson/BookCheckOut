package uncc.helpers;

import java.util.Date;

/**
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 13th, 2015
 * @isodate 2015-06-13T14:00:00-04:00
 */

public class Rental {
    /**
     * 
     */
    private Student student;
    private Tutor tutor;
    private Book book;
    private Date dueDate, rentalDate;
    
    /**
     * @param student
     * @param book
     */
    public Rental(Student student, Book book) {
        this.student = student;
        this.book = book;
        this.dueDate = null;
        this.rentalDate = new Date();
    }
    
    /**
     * @param student
     * @param book
     * @param tutor
     */
    public Rental(Student student, Book book, Tutor tutor) {
        this(student, book);
        this.tutor = tutor;
    }
    
    /**
     * @param student
     * @param book
     * @param dueDate
     */
    public Rental(Student student, Book book, Date dueDate) {
        this(student, book);
        this.dueDate = dueDate;
    }
    
    /**
     * @param student
     * @param book
     * @param dueDate
     * @param tutor
     */
    public Rental(Student student, Book book, Date dueDate, Tutor tutor) {
        this(student, book, dueDate);
        this.tutor = tutor;
    }
    
    /**
     * @return
     */
    public Student getStudent() { return this.student; }
    /**
     * @return
     */
    public Tutor getTutor() { return this.tutor; }
    /**
     * @return
     */
    public Book getBook() { return this.book; }
    /**
     * @return
     */
    public Date getDueDate() { return this.dueDate; }
    /**
     * @return
     */
    public Date getRentalDate() { return this.rentalDate; }
    
    /**
     * @param dueDate
     */
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
}
