package uncc.objects;

/**
 * Object that holds information about a Student.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @since   June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class Student extends HSQLDatabaseObject {
    /**
     * The serialVersionID required by java.io.Serializable.
     */
    private static final long serialVersionUID = 2428236299171180421L;
    
    /**
     * Fields used by the Student object.
     *   String firstName: The student's first name.
     *   String lastName: The student's last name.
     *   String middleInit: The student's middle initial.
     *   String studentID: The student's school ID.
     *   String emailAddress: The student's email address.
     */
    private String firstName, lastName, middleInit, studentID, emailAddress;
    
    /**
     * Constructor for the Student object.
     * 
     * @param firstName The student's first name.
     * @param lastName The student's last name.
     */
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInit = null;
    }
    
    /**
     * @param name A string including the student's name. It is split on any
     *          occurrence of whitespace in the standard regular expression
     *          whitespace class (tab, newline, space, vertical tab) and passed
     *          through the Student(String[] name) constructor.
     */
    public Student(String name) {
        this(name.split("\\s"));
    }
    
    /**
     * @param parts A string array containing a student's name in parts. The
     *          first String located longer than one character is assumed to be
     *          the first name, the next string longer than one character is
     *          assumed to be the last name, and the first occurrence of any
     *          string exactly one character in length is assumed to be the
     *          Student's middle initial. If at any point a first name, last
     *          name, and middle initial are believed to be found, the constructor
     *          stops processing the array and constructs the object at that point.
     */
    public Student(String[] parts) {
        for (String part : parts) {
            if (part.length() < 1) {
                continue;
            } else if (this.middleInit == null && part.length() < 2) {
                this.middleInit = part;
            } else {
                if (this.firstName == null) {
                    this.firstName = part;
                } else if (this.lastName == null) {
                    this.lastName = part;
                }
            }
            if (!(this.firstName == null) &&
                !(this.lastName == null) && 
                !(this.middleInit == null)) 
                break;
        }
    }
    
    /**
     * @return The Student's first name.
     */
    public String getFirstName() { return this.firstName; }
    
    /**
     * @return The Student's last name.
     */
    public String getLastName() { return this.lastName; }
    
    /**
     * @return The Student's middle initial.
     */
    public String getMiddleInitial() { return this.middleInit; }
    
    /**
     * @return The Student's school ID.
     */
    public String getStudentID() { return this.studentID; }
    
    /**
     * @return The Student's email address.
     */
    public String getEmailAddress() { return this.emailAddress; }
    
    /**
     * @param uniqueID A new school ID for the student.
     */
    public void setID(String uniqueID) { this.studentID = uniqueID; }
    
    /**
     * @param emailAddress A new email address for the student.
     */
    public void setEmail(String emailAddress) { this.emailAddress = emailAddress; }
}
