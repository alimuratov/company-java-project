public class ExOverlappingLeaves extends Exception {

    public ExOverlappingLeaves() {super("Overlapping Leaves!"); }
    public ExOverlappingLeaves(String message) {super(message); }
    public ExOverlappingLeaves(Day startDay, Day endDay) {
        super(String.format("Leave overlapped: The leave period %s to %s is found!\n", startDay, endDay));
    }

}
