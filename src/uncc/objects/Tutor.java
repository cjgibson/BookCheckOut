package uncc.objects;

/**
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 13th, 2015
 * @isodate 2015-06-13T14:00:00-04:00
 */

public class Tutor extends Student {
    /**
     * 
     */
    private static final long serialVersionUID = 5624670506783199112L;
    
    /**
     * 
     */
    private Timesheet timesheet = new Timesheet();
    private String[] expertise = new String[] {};
    private String blurb = "";
    
    /**
     * @param firstName
     * @param lastName
     */
    public Tutor(String firstName, String lastName) {
        super(firstName, lastName);
    }
    
    /**
     * @param firstName
     * @param lastName
     * @param timesheet
     * @param expertise
     */
    public Tutor(String firstName, String lastName, Timesheet timesheet, String[] expertise) {
        super(firstName, lastName);
        this.timesheet = timesheet;
        this.expertise = expertise;
    }
    
    /**
     * @param firstName
     * @param lastName
     * @param timesheet
     * @param expertise
     * @param blurb
     */
    public Tutor(String firstName, String lastName, Timesheet timesheet,
            String[] expertise, String blurb) {
        this(firstName, lastName, timesheet, expertise);
        this.blurb = blurb;
    }
    
    /**
     * @param name
     */
    public Tutor(String[] name) {
        super(name);
    }
    
    /**
     * @param name
     * @param timesheet
     * @param expertise
     */
    public Tutor(String[] name, Timesheet timesheet, String[] expertise) {
        super(name);
        this.timesheet = timesheet;
        this.expertise = expertise;
    }
    
    /**
     * @param name
     * @param timesheet
     * @param expertise
     * @param blurb
     */
    public Tutor(String[] name, Timesheet timesheet, String[] expertise,
            String blurb) {
        super(name);
        this.blurb = blurb;
    }
    
    public Timesheet getTimesheet() {
        return this.timesheet;
    }
    
    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }
    
    public String[] getExpertise() {
        return this.expertise;
    }
    
    public String getBlurb() {
        return this.blurb;
    }
}
