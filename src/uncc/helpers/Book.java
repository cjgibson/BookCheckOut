package uncc.helpers;

/**
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 13th, 2015
 * @isodate 2015-06-13T14:00:00-04:00
 */

public class Book {
    /**
     * 
     */
    private ISBN isbn;
    private String title, author, publisher, publishedDate;
    
    /**
     * @param title
     * @param author
     * @param publisher
     * @param publishedDate
     * @param isbn
     */
    public Book(String title, String author, String publisher,
                String publishedDate, ISBN isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
    }
    
    /**
     * @return
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * @return
     */
    public String getAuthor() {
        return this.author;
    }
    
    /**
     * @return
     */
    public String getPublisher() {
        return this.publisher;
    }
    
    /**
     * @return
     */
    public String getPublishedDate() {
        return this.publishedDate;
    }
    
    /**
     * @return
     */
    public String getISBN10() {
        return this.isbn.getISBN10();
    }
    
    /**
     * @return
     */
    public String getISBN13() {
        return this.isbn.getISBN13();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String.format("ISBN-13: %s\nISBN-10:    %s\n%s\nBy: %s\n%s (%s)",
                this.getISBN13(), this.getISBN10(), this.title,
                this.author, this.publisher, this.publishedDate);
    }
}
