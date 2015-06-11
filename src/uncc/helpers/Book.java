package uncc.helpers;

public class Book {
    private ISBN isbn;
    private String title, author, publisher, publishedDate;
    
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
    
    public String getISBN10() {
        return this.isbn.getISBN10();
    }
    
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
