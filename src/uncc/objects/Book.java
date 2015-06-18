package uncc.objects;

/**
 * Object that holds information about a book used in the tutoring center.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @since   June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class Book extends HSQLDatabaseObject {
    /**
     * The serialVersionID required by java.io.Serializable.
     */
    private static final long serialVersionUID = -7555989536191994405L;
    
    /**
     * Fields used by the Book object:
     *   ISBN isbn: A uncc.objects.ISBN object that contains the ISBN13 and
     *     (if possible) associated ISBN10 for a given book.
     *   String ... : Associated information about the book, either as fetched
     *     from the Google Books API, or as provided by the user.
     */
    private ISBN isbn;
    private String title, author, publisher, publishedDate;
    
    /**
     * Most basic constructor for a Book object.
     * 
     * @param title The title of the book.
     */
    public Book(String title) {
        this.title = title;
    }
    
    /**
     * Constructor for the Book object.
     * 
     * @param title The title of the book.
     * @param author The book's author or authors, separated by semicolons.
     */
    public Book(String title, String author) {
        this(title);
        this.author = author;
    }
    
    /**
     * Constructor for the Book object.
     * 
     * @param title The title of the book.
     * @param author The book's author or authors, separated by semicolons.
     * @param publisher
     */
    public Book(String title, String author, String publisher) {
        this(title, author);
        this.publisher = publisher;
    }
    
    /**
     * Constructor for the Book object.
     * 
     * @param title The title of the book.
     * @param author The book's author or authors, separated by semicolons.
     * @param publisher The book's publisher.
     * @param publishedDate The date the book was published, as a string.
     */
    public Book(String title, String author, String publisher, String publishedDate) {
        this(title, author, publisher);
        this.publishedDate = publishedDate;
    }
    
    /**
     * Constructor for the Book object.
     * 
     * @param title The title of the book.
     * @param author The book's author or authors, separated by semicolons.
     * @param publisher The book's publisher.
     * @param publishedDate The date the book was published, as a string.
     * @param isbn The ISBN associated with the book, as a string.
     */
    public Book(String title, String author, String publisher,
            String publishedDate, String isbn) {
        this(title, author, publisher, publishedDate);
        this.isbn = new ISBN(isbn);
    }
    
    /**
     * Constructor for the Book object.
     * 
     * @param title The title of the book.
     * @param author The book's author or authors, separated by semicolons.
     * @param publisher The book's publisher.
     * @param publishedDate The date the book was published, as a string.
     * @param isbn The ISBN object associated with the Book.
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
     * @return The title of the book.
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * @return The author or authors of the book.
     */
    public String getAuthor() {
        return this.author;
    }
    
    /**
     * @return The book's publisher.
     */
    public String getPublisher() {
        return this.publisher;
    }
    
    /**
     * @return The date the book was published, as a string.
     */
    public String getPublishedDate() {
        return this.publishedDate;
    }
    
    /**
     * @return The uncc.objects.ISBN object associated with the book.
     */
    public ISBN getISBN() {
        return this.isbn;
    }
    
    /**
     * @return The ISBN-10 associated with the book, as a string.
     */
    public String getISBN10() {
        return this.isbn.getISBN10();
    }
    
    /**
     * @return The ISBN-13 associated with the book, as a string.
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
