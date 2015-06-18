package uncc.objects;

import javax.management.BadAttributeValueExpException;

/**
 * Object that holds information about the hours in a day that a
 *   uncc.objects.Tutor object is available for tutoring, as stored by a
 *   uncc.objects.Timesheet object.
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @since   June 17th, 2015
 * @isodate 2015-06-17T20:00:00-04:00
 */

public class HourRange extends HSQLDatabaseObject {
    /**
     * The serialVersionID required by java.io.Serializable.
     */
    private static final long serialVersionUID = -7496825459343024349L;
    
    /**
     * final fields used by the uncc.objects.HourRange object:
     *   Float MINUTES_IN_HOUR: The number of minutes in an hour. (default: 60.0)
     *   Float HOURS_IN_DAY: The number of hours in a day. (default 24.0)
     * 
     * Other fields used by the uncc.objects.HourRange object:
     *   Float startTime: The start of this HourRange (guaranteed to be greater
     *     than endTime).
     *   Float endTime: The end of this HourRange (guaranteed to be less than
     *     startTime).
     *   Float timeSpan:: The number of hours contained in this HourRange.
     */
    private static final Float MINUTES_IN_HOUR = new Float(60),
                               HOURS_IN_DAY = new Float(24);
    private Float startTime, endTime, timeSpan;
    
    /**
     * Constructor for the HourRange object. If startTime is greater than
     *   endTime, then the parameters are switched. A timeSpan of 0.0 (ergo,
     *   an HourRange where HourRange.getStartTime() == HourRange.getEndTime())
     *   is permitted.
     * 
     * @param startTime The hour this HourRange starts from. A positive float
     *          strictly less than HOURS_IN_DAY.
     * @param endTime The hour this HourRange ends at. A positive float strictly
     *          less than HOURS_IN_DAY.
     * @throws BadAttributeValueExpException If either startTime or endTime is
     *          negative, or greater than HOURS_IN_DAY.
     */
    public HourRange(Float startTime, Float endTime)
            throws BadAttributeValueExpException {
        if (startTime > 0 && startTime < HOURS_IN_DAY &&
                endTime > 0 && endTime < HOURS_IN_DAY) {
            this.startTime = Math.min(startTime, endTime);
            this.endTime = Math.max(startTime, endTime);
            this.timeSpan = this.endTime - this.startTime;
        } else {
            if (startTime < 0 || startTime > HOURS_IN_DAY) {
                throw new BadAttributeValueExpException(startTime);
            } else {
                throw new BadAttributeValueExpException(endTime);
            }
        }
    }
    
    /**
     * @return The startTime stored by this HourRange.
     */
    public Float getStartTime() { return this.startTime; }
    
    /**
     * @return The endTime stored by this HourRange.
     */
    public Float getEndTime() { return this.endTime; }
    
    /**
     * @return The timeSpan stored by this HourRange, in minutes.
     */
    public Float getMinuteSpan() { return this.timeSpan; }
    
    /**
     * @return The timeSpan stored by this HourRange, in hours.
     */
    public Float getHourSpan() { return this.timeSpan * MINUTES_IN_HOUR; }
    
    /**
     * @return The timespan stored by this HourRange, in days.
     */
    public Float getDaySpan() { return this.timeSpan / HOURS_IN_DAY; }
    
    /**
     * @param time Float object used to generate time string.
     * @return String representation of the float in the form "HH:MM (A|P)M".
     */
    private String getTimeString(Float time) {
        String end = "AM";
        if (time < 12)
            end = "PM";
        Float hour = new Float(time.intValue());
        Float minutes = (time - hour) * MINUTES_IN_HOUR;
        return String.format("%s:%s %s",
                "" + hour.intValue(), "" + minutes.intValue(), end); 
    }

    /**
     * @return String representation of the current HourRange object's startTime.
     */
    public String getStartString() {
        return getTimeString(this.startTime);
    }
    
    /**
     * @return String representation of the current HourRange object's endTime.
     */
    public String getEndString() {
        return getTimeString(this.endTime);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String.format("A %s hour span, from %s to %s.",
                getHourSpan(), getStartString(), getEndString());
    }
}
