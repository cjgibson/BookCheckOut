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

import uncc.objects.Book;
import uncc.objects.ISBN;

/**
 * <p>Class utilized to gather information about a book from its ISBN.</p>
 * <p>Intended to simply serve as a container for functions related to
 * fetching book information from the Google Books API (version 1).</p>
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @since   June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class ISBNLookup {
    /**
     * Private static final strings used to authenticate with, and access the
     *   Google Books API. The String API contains the root URL used to access
     *   the API itself (default: "https://www.googleapis.com/books/v1/volumes?q="),
     *   EXT contains any RESTful URI parameter/value pairs used in making calls
     *   to the API (default: "&key="), and the final field, KEY, contains the
     *   user's API key for the Google Books API.
     */
    private static final String API = ""
            + "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String EXT = ""
            + "&key=";
    private static final String KEY = ""
            + "";
    
    /**
     * Performs a network-based lookup of a book's details using the Google
     * Books API. Returns a uncc.objects.book object.
     * 
     * @param inISBN The ISBN (either ISBN10 or ISBN13) for a given book as a String.
     * @return A new book object, containing details found for the passed
     *          ISBN String. In the case no information is found, null is returned.
     * @throws IOException In the case of a network error.
     */
    public static Book Lookup(String inISBN) throws IOException {
        try {
            String cnISBN = ISBN.cleanISBN(inISBN);
            URL url = new URL(API + cnISBN + EXT + KEY);
            BufferedReader res = new BufferedReader(
                    new InputStreamReader(url.openStream(),
                            Charset.forName("UTF-8")));
            
            StringBuilder con = new StringBuilder();
            int nextChar;
            while ((nextChar = res.read()) != -1) {
                con.append((char) nextChar);
            }
            
            JSONObject json = (JSONObject) JSONValue.parse(con.toString());
            int count = Integer.valueOf(json.get("totalItems").toString());
            if (count < 1)
                return null;
            JSONArray books = (JSONArray) json.get("items");
            JSONObject book = (JSONObject) books.get(0);
            JSONObject info = (JSONObject) book.get("volumeInfo");
            
            String title = "";
            try {
                title = info.get("title").toString();
            } catch (Exception e) {}
            
            String author = "";
            try {
                author = ((JSONArray) info.get("authors"))
                                          .stream()
                                          .map(Object::toString)
                                          .collect(Collectors.joining("; "))
                                          .toString();
            } catch (Exception e) {}
            
            String publisher = "";
            try {
                publisher = info.get("publisher").toString();
            } catch (Exception e) {}
            
            String publishedDate = "";
            try {
                publishedDate = info.get("publishedDate").toString();
            } catch (Exception e) {}
            
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
        } catch (NullPointerException e) {
            return null;
        }
    }
}
