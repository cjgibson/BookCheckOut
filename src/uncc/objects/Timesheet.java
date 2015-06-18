package uncc.objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class Timesheet extends HSQLDatabaseObject {
    /**
     * The serialVersionID required by java.io.Serializable.
     */
    private static final long serialVersionUID = 1231306635987666623L;
    
    /**
     * Final field used by uncc.objects.Timesheet:
     *   String[] weekDays: The days of the week (Used as initial keys for the
     *     timesheet's internal HashMap).
     */
    public static final String[] weekDays = {
        "Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"
    };
    private HashMap<String, ArrayList<HourRange>> timesheet = new HashMap<>();
    
    /**
     * @return Empty timesheet object, initialized with the keys found in
     *          final field weekDays.
     */
    public Timesheet() {
        for (String day : weekDays) {
            this.timesheet.put(day, new ArrayList<HourRange>());
        }
    }
    
    /**
     * @param hours The HourRange for the tutor's availability on Sunday.
     */
    public void setSundayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Sunday", hours);
    }
    
    /**
     * @param hours The HourRange for the tutor's availability on Monday.
     */
    public void setMondayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Monday", hours);
    }
    
    /**
     * @param hours The HourRange for the tutor's availability on Tuesday.
     */
    public void setTuesdayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Tuesday", hours);
    }
    
    /**
     * @param hours The HourRange for the tutor's availability on Wednesday.
     */
    public void setWednesdayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Wednesday", hours);
    }
    
    /**
     * @param hours The HourRange for the tutor's availability on Thursday.
     */
    public void setThursdayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Thursday", hours);
    }
    
    /**
     * @param hours The HourRange for the tutor's availability on Friday.
     */
    public void setFridayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Friday", hours);
    }
    
    /**
     * @param hours The HourRange for the tutor's availability on Saturday.
     */
    public void setSaturdayHours(ArrayList<HourRange> hours) {
        this.timesheet.put("Saturday", hours);
    }
    
    /**
     * @param range The HourRange to add to the Tutor's availabilty on Sunday.
     */
    public void addSundayHours(HourRange range) {}
    
    /**
     * @param range The HourRange to add to the Tutor's availabilty on Monday.
     */
    public void addMondayHours(HourRange range) {}
    
    /**
     * @param range The HourRange to add to the Tutor's availabilty on Tuesday.
     */
    public void addTuesdayHours(HourRange range) {}
    
    /**
     * @param range The HourRange to add to the Tutor's availabilty on Wednesday.
     */
    public void addWednesdayHours(HourRange range) {}
    
    /**
     * @param range The HourRange to add to the Tutor's availabilty on Thursday.
     */
    public void addThursdayHours(HourRange range) {}
    
    /**
     * @param range The HourRange to add to the Tutor's availabilty on Friday.
     */
    public void addFridayHours(HourRange range) {}
    
    /**
     * @param range The HourRange to add to the Tutor's availabilty on Saturday.
     */
    public void addSaturdayHours(HourRange range) {}
}