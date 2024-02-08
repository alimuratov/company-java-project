public class DayInterval {
    private Day startDay;
    private Day endDay;
    private int intervalLength;

    // Constructor

    public DayInterval(Day startDay, Day endDay) {
        this.startDay = startDay;
        this.endDay = endDay;
        intervalLength = Day.daysBetween(startDay, endDay);
    }

    // Getters

    public Day getStartDay() {
        return startDay;
    }

    public Day getEndDay() {
        return endDay;
    }

    public int getIntervalLength() {
        return intervalLength;
    }

    // Static methods

    // Returns true if the two specified intervals overlap, otherwise returns false
    public static boolean isOverlap(DayInterval l1, DayInterval l2) {
        if (l1.startDay.compareTo(l2.endDay) <= 0 && l2.startDay.compareTo(l1.endDay) <= 0) {
            return true;
        }
        return false;
    }

    // Instance methods

    // Calculates and returns the duration of overlap in days between the implicit (this) interval and the provided interval.
    public int overlapSize(DayInterval projectInterval) {

        if (isOverlap(this, projectInterval)) {
            Day overlapStartDay = (this.startDay.compareTo(projectInterval.startDay) >= 0) ? this.startDay : projectInterval.startDay;
            Day overlapEndDay = (this.endDay.compareTo(projectInterval.endDay) <= 0) ? this.endDay : projectInterval.endDay;
            return Day.daysBetween(overlapStartDay, overlapEndDay);
        }
        return 0;
    }

    // Overriden methods

    @Override
    public String toString() {
        return String.format("%s to %s", startDay, endDay);
    }

}
