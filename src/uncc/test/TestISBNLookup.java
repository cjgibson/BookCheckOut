package uncc.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import uncc.ISBNLookup;
import uncc.helpers.Book;

public class TestISBNLookup {

    @Test
    public void testISBN10Conversion() {
        try {
            ISBNLookup lookup = new ISBNLookup();
            Book book = lookup.Lookup("0-9752298-0-X");
            assertEquals("9780975229804", book.getISBN13());
        } catch (IOException e) {
            fail();
        }
    }
    
    @Test
    public void testISBN13Conversion() {
        try {
            ISBNLookup lookup = new ISBNLookup();
            Book book = lookup.Lookup("9781413304541");
            assertEquals("1413304540", book.getISBN10());
        } catch (IOException e) {
            fail();
        }
    }

}
