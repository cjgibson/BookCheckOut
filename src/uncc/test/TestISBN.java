package uncc.test;

import static org.junit.Assert.*;

import java.io.CharConversionException;
import java.io.IOException;

import org.junit.Test;

import uncc.ISBNLookup;
import uncc.objects.Book;
import uncc.objects.ISBN;

/**
 * Test class for uncc.objects.ISBN and uncc.ISBNLookup.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class TestISBN {

    String[] testISBN13s = {
            "978-600-119-125-1",
            "978-601-7151-13-3",
            "978-602-8328-22-7",
            "978-603-500-045-1",
            "978-606-8126-35-7",
            "978-607-455-035-1",
            "978-608-203-023-4",
            "978-612-45165-9-7",
            "978-614-404-018-8",
            "978-615-5014-99-4",
            "978-988-00-3827-3",
            "978-9928400529",
            "978-9929801646",
            "978-9930943106",
            "978-9933101473",
            "978-9934015960",
            "978-99937-1-056-1",
            "978-99965-2-047-1"
    };
    
    String[] convISBN13s = {
            "6001191255",
            "6017151134",
            "6028328227",
            "6035000452",
            "6068126358",
            "6074550352",
            "6082030230",
            "6124516594",
            "6144040188",
            "615501499X",
            "9880038274",
            "9928400520",
            "9929801642",
            "9930943102",
            "9933101471",
            "993401596X",
            "9993710563",
            "9996520471"
    };
    
    String[] testISBN10s = {
            "0-330-28498-3",
            "1-58182-008-9",
            "2-226-05257-7",
            "3-7965-1900-8",
            "4-19-830127-1",
            "5-85270-001-0",
            "605-384-057-2",
            "7-301-10299-2",
            "80-85983-44-3",
            "81-7215-399-6",
            "82-530-0983-6",
            "83-08-01587-5",
            "84-86546-08-7",
            "85-7531-015-1",
            "86-341-0846-5",
            "87-595-2277-1",
            "88-04-47328-2",
            "90-5691-187-2",
            "91-1-811692-2",
            "92-67-10370-9",
            "93-8011-236-X",
            "950-04-0442-7",
            "951-0-11369-7",
            "952-471-294-6",
            "953-157-105-8",
            "954-430-603-X",
            "955-20-3051-X",
            "956-7291-48-9",
            "957-01-7429-3",
            "958-04-6278-X",
            "959-10-0363-3",
            "961-6403-23-0",
            "962-04-0195-6",
            "963-7971-51-3",
            "964-6194-70-2",
            "965-359-002-2",
            "967-978-753-2",
            "968-6031-02-2",
            "970-20-0242-7",
            "971-8845-10-0",
            "972-37-0274-6",
            "973-43-0179-9",
            "974-85854-7-6",
            "975-293-381-5",
            "976-640-140-3",
            "977-734-520-8",
            "978-37186-2-2",
            "979-553-483-1",
            "980-01-0194-2",
            "981-3018-39-9",
            "982-301-001-3",
            "983-52-0157-9",
            "984-458-089-7",
            "986-417-191-7",
            "987-98184-2-3"
    };
    
    String[] convISBN10s = {
            "9780330284981",
            "9781581820089",
            "9782226052575",
            "9783796519000",
            "9784198301279",
            "9785852700018",
            "9786053840572",
            "9787301102992",
            "9788085983449",
            "9788172153991",
            "9788253009834",
            "9788308015872",
            "9788486546083",
            "9788575310151",
            "9788634108460",
            "9788759522776",
            "9788804473282",
            "9789056911874",
            "9789118116926",
            "9789267103709",
            "9789380112367",
            "9789500404426",
            "9789510113691",
            "9789524712941",
            "9789531571050",
            "9789544306038",
            "9789552030512",
            "9789567291489",
            "9789570174298",
            "9789580462781",
            "9789591003638",
            "9789616403238",
            "9789620401954",
            "9789637971518",
            "9789646194700",
            "9789653590021",
            "9789679787535",
            "9789686031027",
            "9789702002420",
            "9789718845103",
            "9789723702743",
            "9789734301799",
            "9789748585475",
            "9789752933811",
            "9789766401405",
            "9789777345200",
            "9789783718623",
            "9789795534839",
            "9789800101940",
            "9789813018396",
            "9789823010014",
            "9789835201578",
            "9789844580893",
            "9789864171910",
            "9789879818428",
    };
    
    @Test
    public void testISBN10Conversion() throws CharConversionException {
        try {
            for (int i = 0; i < testISBN10s.length; i++) {
                ISBN isbn = new ISBN(testISBN10s[i]);
                assertEquals(convISBN10s[i], isbn.getISBN13());
            }
        } catch (IllegalArgumentException e) {
            fail();
        }
    }
    
    @Test
    public void testISBN13Conversion() {
        try {
            for (int i = 0; i < testISBN13s.length; i++) {
                ISBN isbn = new ISBN(testISBN13s[i]);
                assertEquals(convISBN13s[i], isbn.getISBN10());
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            fail();
        }
    }
    
    @Test
    public void testISBNMatch() {
        try {
            for (int i = 0; i < testISBN13s.length; i++)
                new ISBN(convISBN13s[i], testISBN13s[i]);
            for (int i = 0; i < testISBN10s.length; i++)
                new ISBN(testISBN10s[i], convISBN10s[i]);
        } catch (Exception e) {
            System.err.println(e);
            fail();
        }
    }
    
    @Test
    public void testISBN10CheckSum() {
        try {
            for (int i = 0; i < testISBN10s.length; i++) {
                int chck = ISBN.calculateISBN10CheckSum(testISBN10s[i]);
                assertEquals(ISBN.cleanISBN(testISBN10s[i]).substring(9, 10),
                             (chck == 10) ? "X" : "" + chck);
            }
        } catch (Exception e) {
            System.err.println(e);
            fail();
        }
    }
    
    @Test
    public void testISBN13CheckSum() {
        try {
            for (int i = 0; i < testISBN13s.length; i++) {
                int chck = ISBN.calculateISBN13CheckSum(testISBN13s[i]);
                assertEquals(ISBN.cleanISBN(testISBN13s[i]).substring(12, 13),
                             "" + chck);
            }
        } catch (Exception e) {
            System.err.println(e);
            fail();
        }
    }
    
    @Test
    public void testNetworkISBN10Conversion() {
        try {
            for (int i = 0; i < testISBN10s.length; i++) {
                try {
                    Book book = ISBNLookup.Lookup(testISBN10s[i]);
                    ISBN isbn = book.getISBN();
                    if (!convISBN10s[i].equals(isbn.getISBN13())) {
                        System.err.println("Expected " + convISBN10s[i]
                                + " but got " + isbn.getISBN13() + ".");
                    }
                    System.out.println(book);
                    System.out.println();
                } catch (NullPointerException e) {
                    System.err.println("Recieved no information for '"
                            + testISBN10s[i] + "'.");
                } catch (IOException e) {
                    System.err.println("Network call failed on '"
                            + testISBN10s[i] + "'.");
                }
            }
        } catch (IllegalArgumentException e) {
            fail();
        }
    }
    
    @Test
    public void testNetworkISBN13Conversion() {
        try {
            for (int i = 0; i < testISBN13s.length; i++) {
                try {
                    Book book = ISBNLookup.Lookup(testISBN13s[i]);
                    ISBN isbn = book.getISBN();
                    if (!convISBN13s[i].equals(isbn.getISBN10())) {
                        System.err.println("Expected " + convISBN13s[i]
                                + " but got " + isbn.getISBN10() + ".");
                    }
                    System.out.println(book);
                } catch (NullPointerException e) {
                    System.err.println("Recieved no information for '"
                            + testISBN13s[i] + "'.");
                } catch (IOException e) {
                    System.err.println("Network call failed on '"
                            + testISBN13s[i] + "'.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            fail();
        }
    } 
}
