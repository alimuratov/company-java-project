public class Leave extends DayInterval implements Comparable<Leave>{

    public Leave(String start, String end) {
        super(new Day(start), new Day(end));
    }


    public int getLeaveDuration() {
        return super.getIntervalLength();
    }

    @Override
    public int compareTo(Leave o) {
        return super.getStartDay().compareTo(o.getStartDay());
    }
}
