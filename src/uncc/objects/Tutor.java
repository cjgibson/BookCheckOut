package uncc.objects;

/**
 * Object used to store information about a Tutor, their schedule, and their
 *   course expertise. Extends uncc.objects.Student;
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @since   June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class Tutor extends Student {
    /**
     * The serialVersionID required by java.io.Serializable.
     */
    private static final long serialVersionUID = 5624670506783199112L;
    
    /**
     * Fields used by uncc.objects.Tutor:
     *   Timesheet timesheet: The tutor's availability.
     *   String[] expertise: A listing of courses the tutor can tutor for.
     *   String blurb: An optional blurb about the tutor.
     */
    private Timesheet timesheet = new Timesheet();
    private String[] expertise = new String[] {};
    private String blurb = "";
    
    /**
     * Constructor for the Tutor object. Relies on the
     *   Student(firstName, lastName) constructor.
     * 
     * @param firstName The tutor's first name.
     * @param lastName The tutor's second name.
     */
    public Tutor(String firstName, String lastName) {
        super(firstName, lastName);
    }
    
    /**
     * Constructor for the Tutor object. Relies on the 
     *   Student(firstName, lastName) constructor.
     * 
     * @param firstName The tutor's first name.
     * @param lastName The tutor's second name.
     * @param timesheet The tutor's timesheet.
     * @param expertise The tutor's expertise.
     */
    public Tutor(String firstName, String lastName, Timesheet timesheet, String[] expertise) {
        super(firstName, lastName);
        this.timesheet = timesheet;
        this.expertise = expertise;
    }
    
    /**
     * Constructor for the Tutor object. Relies on the
     *   Student(firstName, lastName) constructor.
     * 
     * @param firstName The tutor's first name.
     * @param lastName The tutor's second name.
     * @param timesheet The tutor's timesheet.
     * @param expertise The tutor's expertise.
     * @param blurb The tutor's blurb.
     */
    public Tutor(String firstName, String lastName, Timesheet timesheet,
            String[] expertise, String blurb) {
        this(firstName, lastName, timesheet, expertise);
        this.blurb = blurb;
    }
    
    /**
     * Constructor for the Tutor object. Relies on the Student(String[] parts)
     *   constructor.
     * 
     * @param name The parts of the tutor's name, as a string array.
     */
    public Tutor(String[] name) {
        super(name);
    }
    
    /**
     * Constructor for the Tutor object. Relies on the Student(String[] parts)
     *   constructor.
     * 
     * @param name The parts of the tutor's name, as a string array.
     * @param timesheet The tutor's timesheet.
     * @param expertise The tutor's expertise.
     */
    public Tutor(String[] name, Timesheet timesheet, String[] expertise) {
        super(name);
        this.timesheet = timesheet;
        this.expertise = expertise;
    }
    
    /**
     * Constructor for the Tutor object. Relies on the Student(String[] parts)
     *   constructor.
     * 
     * @param name The parts of the tutor's name, as a string array.
     * @param timesheet The tutor's timesheet.
     * @param expertise The tutor's expertise.
     * @param blurb The tutor's blurb.
     */
    public Tutor(String[] name, Timesheet timesheet, String[] expertise,
            String blurb) {
        super(name);
        this.blurb = blurb;
    }
    
    /**
     * @return The timesheet associated with this user.
     */
    public Timesheet getTimesheet() {
        return this.timesheet;
    }
    
    /**
     * @param timesheet A new timesheet for this tutor.
     */
    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }
    
    /**
     * @return This tutor's expertise.
     */
    public String[] getExpertise() {
        return this.expertise;
    }
    
    /**
     * @return The blurb contained about this tutor.
     */
    public String getBlurb() {
        return this.blurb;
    }
}
