public class ExLeaveInProjectFinalStage extends Exception {

    public ExLeaveInProjectFinalStage() {super("Leave in Project's Final Stage!"); }
    public ExLeaveInProjectFinalStage(String message) { super(message); }
    public ExLeaveInProjectFinalStage(String projectName, Day finalStageStartDay, Day finalStageEndDay) {
        super(String.format("The leave is invalid.  Reason: Project %s will be in its final stage during %s to %s.\n", projectName, finalStageStartDay, finalStageEndDay));
    }


 }
