public class ExInsufficientBalanceOfLeaves extends Exception {

    public ExInsufficientBalanceOfLeaves() {super("Insufficient balance of annual leave!"); }
    public ExInsufficientBalanceOfLeaves(String message) { super(message); }
    public ExInsufficientBalanceOfLeaves(int dayLeft) {
        super(String.format("Insufficient balance of annual leave. %d days left only!\n", dayLeft));
    }

}
