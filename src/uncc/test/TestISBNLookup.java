package uncc.test;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import uncc.src.ISBNLookup;

public class TestISBNLookup {

    @Test
    public void testISBN10Conversion() {
        try {
            ISBNLookup lookup = new ISBNLookup("0-9752298-0-X");
            assertEquals(lookup.getISBN13(), "9780975229804");
        } catch (IOException e) {
            fail();
        }
    }
    
    @Test
    public void testISBN13Conversion() {
        try {
            ISBNLookup lookup = new ISBNLookup("9781413304541");
            assertEquals(lookup.getISBN10(), "1413304540");
        } catch (IOException e) {
            fail();
        }
    }

}
