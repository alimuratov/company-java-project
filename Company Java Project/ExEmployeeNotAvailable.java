public class ExEmployeeNotAvailable extends Exception {
    public ExEmployeeNotAvailable() {super("Employee is already in a team!"); }
    public ExEmployeeNotAvailable(String message) {super(message); }
    public ExEmployeeNotAvailable(String eName, String eTeamName) {
        super(String.format("%s has already joined another team: %s\n", eName, eTeamName));
    }
}
