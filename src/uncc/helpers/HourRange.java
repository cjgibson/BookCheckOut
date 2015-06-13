package uncc.helpers;

import javax.management.BadAttributeValueExpException;

/**
 * 
 * @author  Christian Gibson
 * @version 0.1
 * @date    June 13th, 2015
 * @isodate 2015-06-13T14:00:00-04:00
 */

public class HourRange {
    /**
     * 
     */
    private static final Float MINUTES_IN_HOUR = new Float(60),
                               HOURS_IN_DAY = new Float(24);
    private Float startTime, endTime, timeSpan;
    
    /**
     * @param startTime
     * @param endTime
     * @throws BadAttributeValueExpException
     */
    public HourRange(Float startTime, Float endTime)
            throws BadAttributeValueExpException {
        if (startTime > 0 && startTime < 24 &&
                endTime > 0 && endTime < 24) {
            this.startTime = Math.min(startTime, endTime);
            this.endTime = Math.max(startTime, endTime);
            this.timeSpan = this.endTime - this.startTime;
        } else {
            if (startTime < 0 || startTime > 24) {
                throw new BadAttributeValueExpException(startTime);
            } else {
                throw new BadAttributeValueExpException(endTime);
            }
        }
    }
    
    /**
     * @return
     */
    public Float getStartTime() { return this.startTime; }
    /**
     * @return
     */
    public Float getEndTime() { return this.endTime; }
    /**
     * @return
     */
    public Float getHourSpan() { return this.timeSpan * MINUTES_IN_HOUR; }
    /**
     * @return
     */
    public Float getDaySpan() { return this.timeSpan / HOURS_IN_DAY; }
    /**
     * @param time
     * @return
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
     * @return
     */
    public String getStartString() {
        return getTimeString(this.startTime);
    }
    /**
     * @return
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
