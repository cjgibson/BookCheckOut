package uncc.src;

import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <p>Class utilized to gather information about a book from its ISBN.</p>
 * <p>Relies on Google's Book API.</p>
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 9th, 2015
 * @isodate 2015-06-09T18:00:00-04:00
 */

public class ISBNLookup {
    /**
     * 
     */
    private static final String[] LEGAL_ISBN13_PREFIX = {
        "978", "979"
    };
    private static final String API = ""
            + "https://www.googleapis.com/books/v1/volumes?q=";
    private String isbn = null, isbn13 = null, isbn10 = null;
    private String title, author, publisher, publishedDate;
    
    /**
     * @param isbn
     * @throws IOException
     */
    public ISBNLookup(String isbn) throws IOException {
        this.isbn = cleanISBN(isbn);
        URL url = new URL(API + this.isbn);
        BufferedReader res = new BufferedReader(
                new InputStreamReader(url.openStream(),
                        Charset.forName("UTF-8")));
        
        StringBuilder con = new StringBuilder();
        int nextChar;
        while ((nextChar = res.read()) != -1) {
            con.append((char) nextChar);
        }
        
        JSONObject json = new JSONObject(con.toString());
        JSONObject book = ((JSONObject) json.getJSONArray("items").get(0))
                                            .getJSONObject("volumeInfo");
        
        this.title = book.getString("title");
        this.author = book.getJSONArray("authors").join("; ");
        this.publisher = book.getString("publisher");
        this.publishedDate = book.getString("publishedDate");
        
        JSONArray ident = book.getJSONArray("industryIdentifiers");
        for (Object id : ident) {
            if (((JSONObject) id).getString("type") == "ISBN_10")
                this.isbn10 = ((JSONObject) id).getString("identifier");
            else if (((JSONObject) id).getString("type") == "ISBN_13")
                this.isbn13 = ((JSONObject) id).getString("identifier");
        }
        
        if (this.isbn13 != null) {
            String temp = this.isbn10;
            if (!interpretISBN13(this.isbn13)) {
                this.isbn13 = null;
                this.isbn10 = temp;
            }
        } else if (this.isbn10 != null) {
            if (!interpretISBN10(this.isbn10)) {
                this.isbn13 = null;
                this.isbn10 = null;
            }
        } else {
            if (!validateISBN(this.isbn)) {
                throw new IllegalArgumentException();
            }
        }
    }
    
    /**
     * @return
     */
    public String getISBN13() {
        return this.isbn13;
    }
    
    /**
     * @return
     */
    public String getISBN10() {
        return this.isbn10;
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
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String.format("ISBN-13: %s\nISBN-10:    %s\n%s\nBy: %s\n%s (%s)",
                this.isbn13, this.isbn10, this.title, this.author,
                this.publisher, this.publishedDate);
    }
    
    private String cleanISBN(String isbn) {
        StringBuilder numeric = new StringBuilder();
        
        for (char part : isbn.toCharArray()) {
            if (Character.isDigit(part) || part == 'X' || part == 'x')
                numeric.append(part);
        }
        
        return numeric.toString();
    }
    
    /**
     * @param isbn
     * @return
     */
    private boolean validateISBN(String isbn) {
        try {
            if (isbn.length() == 10) {
                return interpretISBN10(isbn);
            } else if (isbn.length() == 13) {
                return interpretISBN13(isbn);
            }
        } catch (CharConversionException e) {
            return false;
        }
        
        return false;
    }
    
    /**
     * @param isbn
     * @return
     * @throws CharConversionException
     */
    private boolean interpretISBN10(String isbn)
            throws CharConversionException {
        char checkChar = isbn.charAt(isbn.length() - 1);
        int checkSum;
        
        if (Character.isDigit(checkChar)) {
            checkSum = Character.getNumericValue(checkChar);
        } else if (checkChar == 'X' || checkChar == 'x') {
            checkSum = 10;
        } else {
            return false;
        }
        
        if (calculateISBN10CheckSum(isbn) == checkSum) {
            this.isbn13 = "978" + isbn.substring(0, 9);
            this.isbn13 += calculateISBN13CheckSum(this.isbn13);
            this.isbn10 = isbn;
            return true;
        }
        
        return false;
    }
    
    /**
     * @param isbn
     * @return
     * @throws CharConversionException
     */
    private int calculateISBN10CheckSum(String isbn)
            throws CharConversionException {
        if (isbn.length() > 9) {
            isbn = isbn.substring(0, 9);
        }
        int mult = 10;
        int chck = 0;
        for (char next : isbn.toCharArray()) {
            if (Character.isDigit(next)) {
                chck += mult * Character.getNumericValue(next);
                mult -= (mult > 2) ? 1 : mult;
            } else {
                throw new CharConversionException();
            }
        }
        return (11 - (chck % 11)) % 11;
    }
    
    /**
     * @param isbn
     * @return
     * @throws CharConversionException
     */
    private boolean interpretISBN13(String isbn)
            throws CharConversionException {
        boolean validPrefix = false;
        for (String prefix : LEGAL_ISBN13_PREFIX) {
            if (isbn.startsWith(prefix))
                validPrefix = true;
        }
        if (!validPrefix)
            return false;
        
        char checkChar = isbn.charAt(isbn.length() - 1);
        int checkSum;
        
        if (Character.isDigit(checkChar)) {
            checkSum = Character.getNumericValue(checkChar);
        } else {
            return false;
        }
        
        if (calculateISBN13CheckSum(isbn) == checkSum) {
            this.isbn13 = isbn;
            if (this.isbn13.startsWith("978")) {
                this.isbn10 = isbn13.substring(3, 12);
                this.isbn10 += calculateISBN10CheckSum(this.isbn10);
            } else {
                this.isbn10 = null;
            }
            return true;
        }
        
        return false;
    }
    
    /**
     * @param isbn
     * @return
     * @throws CharConversionException
     */
    private int calculateISBN13CheckSum(String isbn)
            throws CharConversionException {
        if (isbn.length() > 12) {
            isbn = isbn.substring(0, 12);
        }
        int mult = 1;
        int chck = 0;
        for (char next : isbn.toCharArray()) {
            if (Character.isDigit(next)) {
                chck += mult * Character.getNumericValue(next);
                mult = (mult == 1) ? 3 : 1;
            } else {
                throw new CharConversionException();
            }
        }
        return (10 - (chck % 10)) % 10;
    }
}
