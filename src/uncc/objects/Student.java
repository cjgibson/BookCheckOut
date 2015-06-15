package uncc.objects;

/**
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 13th, 2015
 * @isodate 2015-06-13T14:00:00-04:00
 */

public class Student extends HSQLDatabaseObject {
    /**
     * 
     */
    private static final long serialVersionUID = 2428236299171180421L;
    private transient int id;
    
    /**
     * 
     */
    private String firstName, lastName, middleInit, uniqueID, emailAddress;
    
    /**
     * @param firstName
     * @param lastName
     */
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInit = null;
    }
    
    /**
     * @param name
     */
    public Student(String name) {
        this(name.split("\\s"));
    }
    
    /**
     * @param parts
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
     * @return
     */
    public String getFirstName() { return this.firstName; }
    /**
     * @return
     */
    public String getLastName() { return this.lastName; }
    /**
     * @return
     */
    public String getMiddleInitial() { return this.middleInit; }
    /**
     * @return
     */
    public String getStudentID() { return this.uniqueID; }
    /**
     * @return
     */
    public String getEmailAddress() { return this.emailAddress; }
    public int getID() { return this.id; }
    
    /**
     * @param uniqueID
     */
    public void setID(String uniqueID) { this.uniqueID = uniqueID; }
    /**
     * @param emailAddress
     */
    public void setEmail(String emailAddress) { this.emailAddress = emailAddress; }
    public void setID(int id) { this.id = id; }
}
