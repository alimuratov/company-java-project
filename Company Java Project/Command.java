
public interface Command {
    void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeNotFound, ExEmployeeExists, ExProjectNotFound, ExTeamNotFound, ExProjectExists, ExTeamExists, ExEmployeeNotAvailable, ExOverlappingLeaves, ExInsufficientBalanceOfLeaves, ExLeaveInProjectFinalStage; // interface specifies what class must do, not how to do it
    // so this interfaces says that the class must have this "execute" method that accepts an array of strings as a parameter
}
