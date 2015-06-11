package uncc.helpers;

import java.io.CharConversionException;
import java.util.InputMismatchException;

public class ISBN {
    private String isbn, isbn10, isbn13;
    private static final String[] LEGAL_ISBN13_PREFIX = {
        "978", "979"
    };
    
    public ISBN(String isbn) {
        this.isbn = interpretISBN(cleanISBN(isbn));
    }
    
    public ISBN(String isbn10, String isbn13) {
        this.isbn10 = interpretISBN10(cleanISBN(isbn10));
        this.isbn13 = interpretISBN13(cleanISBN(isbn13));
        if (this.isbn10 != null && match(this.isbn10, this.isbn13)) {
            this.isbn = this.isbn13;
        } else {
            throw new InputMismatchException();
        }
    }
    
    public ISBN(int isbn) {
        this("" + isbn);
    }
    
    public ISBN(int isbn10, int isbn13) {
        this("" + isbn10, "" + isbn13);
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
    public String getOriginalISBN() {
        return this.isbn;
    }
    
    /**
     * @param isbn
     * @return
     */
    public static String cleanISBN(String isbn) {
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
    public static boolean validateISBN(String isbn) {
        if (isbn.length() == 10) {
            return validateISBN10(isbn);
        } else if (isbn.length() == 13) {
            return validateISBN13(isbn);
        }
        return false;
    }
    
    /**
     * @param isbn
     */
    private String interpretISBN(String isbn) {
        if (isbn.length() == 10) {
            this.isbn10 = interpretISBN10(isbn);
            this.isbn13 = convertISBN10(this.isbn10);
            return this.isbn10;
        } else if (isbn.length() == 13) {
            this.isbn13 = interpretISBN13(isbn);
            this.isbn10 = convertISBN13(this.isbn13);
            return this.isbn13;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * @param isbn
     * @return
     */
    private String interpretISBN10(String isbn) {
        if (validateISBN10(isbn)) {
            this.isbn10 = isbn;
            this.isbn13 = convertISBN10(this.isbn10);
        } else {
            throw new IllegalArgumentException();
        }
        return this.isbn10;
    }
    
    /**
     * @param isbn
     * @return
     */
    private String convertISBN10(String isbn) {
        isbn = "978" + isbn.substring(0, 9);
        try {
            isbn += calculateISBN13CheckSum(isbn);
        } catch (CharConversionException e) {
            throw new IllegalArgumentException();
        }
        return isbn;
    }
    
    /**
     * @param isbn
     * @return
     */
    private String interpretISBN13(String isbn) {
        if (validateISBN13(isbn)) {
            this.isbn13 = isbn;
            this.isbn10 = convertISBN13(this.isbn13);
        } else {
            throw new IllegalArgumentException();
        }
        return this.isbn13;
    }
    
    /**
     * @param isbn
     * @return
     */
    private String convertISBN13(String isbn) {
        if (isbn.startsWith("978")) {
            isbn = isbn.substring(3, 12);
            try {
                isbn += calculateISBN10CheckSum(isbn);
            } catch (CharConversionException e) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return isbn;
    }
    
    /**
     * @param isbn10
     * @param isbn13
     * @return
     */
    private boolean match(String isbn10, String isbn13) {
        if (isbn13.startsWith("978")) {
            if (isbn10.substring(0, 9).equalsIgnoreCase(isbn13.substring(3, 12))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param isbn
     * @return
     * @throws CharConversionException
     */
    public static boolean validateISBN10(String isbn) {
        char checkChar = isbn.charAt(isbn.length() - 1);
        int checkSum;
        
        try {
            if (Character.isDigit(checkChar)) {
                checkSum = Character.getNumericValue(checkChar);
            } else if (checkChar == 'X' || checkChar == 'x') {
                checkSum = 10;
            } else {
                return false;
            }
            
            if (calculateISBN10CheckSum(isbn) == checkSum) {
                return true;
            }
        } catch (CharConversionException e) {}
        
        return false;
    }
    
    /**
     * @param isbn
     * @return
     * @throws CharConversionException
     */
    public static int calculateISBN10CheckSum(String isbn)
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
    public static boolean validateISBN13(String isbn) {
        boolean validPrefix = false;
        for (String prefix : LEGAL_ISBN13_PREFIX) {
            if (isbn.startsWith(prefix))
                validPrefix = true;
        }
        if (!validPrefix)
            return false;
        
        char checkChar = isbn.charAt(isbn.length() - 1);
        int checkSum;
        
        try {
            if (Character.isDigit(checkChar)) {
                checkSum = Character.getNumericValue(checkChar);
            } else {
                return false;
            }
            
            if (calculateISBN13CheckSum(isbn) == checkSum) {
                return true;
            }
        } catch (CharConversionException e) {}
        
        return false;
    }
    
    /**
     * @param isbn
     * @return
     * @throws CharConversionException
     */
    public static int calculateISBN13CheckSum(String isbn)
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
