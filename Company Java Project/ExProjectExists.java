public class ExProjectExists extends Exception {
    public ExProjectExists() {super("Project already exists!"); }
    public ExProjectExists(String message) {super(message); }
}