package uncc;

import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import uncc.helpers.Book;
import uncc.helpers.ISBN;

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
    private static final String API = ""
            + "https://www.googleapis.com/books/v1/volumes?q=";
    
    /**
     * @param isbn
     * @throws IOException
     */
    public ISBNLookup() throws IOException {
        
    }
    
    /**
     * @param isbn
     * @return
     * @throws IOException
     */
    public static Book Lookup(String inISBN) throws IOException {
        String cnISBN = ISBN.cleanISBN(inISBN);
        URL url = new URL(API + cnISBN);
        BufferedReader res = new BufferedReader(
                new InputStreamReader(url.openStream(),
                        Charset.forName("UTF-8")));
        
        StringBuilder con = new StringBuilder();
        int nextChar;
        while ((nextChar = res.read()) != -1) {
            con.append((char) nextChar);
        }
        
        JSONObject json = (JSONObject) JSONValue.parse(con.toString());
        JSONArray books = (JSONArray) json.get("items");
        JSONObject book = (JSONObject) books.get(0);
        JSONObject info = (JSONObject) book.get("volumeInfo");
        
        String title = info.get("title").toString();
        String author = ((JSONArray) info.get("authors"))
                                         .stream()
                                         .map(Object::toString)
                                         .collect(Collectors.joining("; "))
                                         .toString();
        String publisher = info.get("publisher").toString();
        String publishedDate = info.get("publishedDate").toString();
        
        String isbn10 = null, isbn13 = null;
        JSONArray ident = (JSONArray) info.get("industryIdentifiers");
        for (Object id : ident) {
            if (((JSONObject) id).get("type")
                                 .toString()
                                 .equalsIgnoreCase("ISBN_10"))
                isbn10 = ((JSONObject) id).get("identifier").toString();
            else if (((JSONObject) id).get("type")
                                      .toString()
                                      .equalsIgnoreCase("ISBN_13"))
                isbn13 = ((JSONObject) id).get("identifier").toString();
        }
        
        ISBN isbn = null;
        if (isbn13 != null) {
            String temp = isbn10;
            if (!ISBN.validateISBN13(isbn13)) {
                isbn13 = null;
                isbn10 = temp;
            } else {
                isbn = new ISBN(isbn10, isbn13);
            }
        } else if (isbn10 != null) {
            if (!ISBN.validateISBN10(isbn10)) {
                isbn13 = null;
                isbn10 = null;
            } else {
                isbn = new ISBN(isbn10, isbn13);
            }
        } else {
            if (!ISBN.validateISBN(cnISBN)) {
                throw new IllegalArgumentException();
            } else {
                isbn = new ISBN(cnISBN);
            }
        }
        
        return new Book(title, author, publisher, publishedDate, isbn);
    }
}
