package uncc.objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 13th, 2015
 * @isodate 2015-06-13T14:00:00-04:00
 */

public class Timesheet extends HSQLDatabaseObject {
    /**
     * 
     */
    private static final long serialVersionUID = 1231306635987666623L;
    
    /**
     * 
     */
    public static final String[] weekDays = {
        "Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"
    };
    private HashMap<String, ArrayList<HourRange>> timesheet = new HashMap<>();
    
    public Timesheet() {
        for (String day : weekDays) {
            this.timesheet.put(day, new ArrayList<HourRange>());
        }
    }
    
    /**
     * @param hours
     */
    public void setSundayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Sunday", hours);
    }
    
    /**
     * @param hours
     */
    public void setMondayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Monday", hours);
    }
    
    /**
     * @param hours
     */
    public void setTuesdayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Tuesday", hours);
    }
    
    /**
     * @param hours
     */
    public void setWednesdayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Wednesday", hours);
    }
    
    /**
     * @param hours
     */
    public void setThursdayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Thursday", hours);
    }
    
    /**
     * @param hours
     */
    public void setFridayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Friday", hours);
    }
    
    /**
     * @param hours
     */
    public void setSaturdayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Saturday", hours);
    }
    
    /**
     * @param range
     */
    public void addSundayHours(HourRange range) {}
    /**
     * @param range
     */
    public void addMondayHours(HourRange range) {}
    /**
     * @param range
     */
    public void addTuesdayHours(HourRange range) {}
    /**
     * @param range
     */
    public void addWednesdayHours(HourRange range) {}
    /**
     * @param range
     */
    public void addThursdayHours(HourRange range) {}
    /**
     * @param range
     */
    public void addFridayHours(HourRange range) {}
    /**
     * @param range
     */
    public void addSaturdayHours(HourRange range) {}
}