package uncc.helpers;

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
    private Timesheet timesheet;
    private String[] expertise;
    private String blurb;
    
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
     * @param name
     * @param timesheet
     * @param expertise
     */
    public Tutor(String[] name, Timesheet timesheet, String[] expertise) {
        super(name);
        this.timesheet = timesheet;
        this.expertise = expertise;
    }
}
