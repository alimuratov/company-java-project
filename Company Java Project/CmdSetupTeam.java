public class CmdSetupTeam extends RecordedCommand {
    // create a team, put it into the list of teams
    // undo: delete that team from the list of teams
    // redo: add that team back
    private Team t = null;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExTeamExists, ExEmployeeNotAvailable, ExEmployeeNotFound{
        Team currentTeam = null; // so that it is visible to the catch block ExLeaderNotAvailable

        if (cmdParts.length < 3) throw new ExInsufficientArguments();

        String leaderName = cmdParts[2];
        Company c = Company.getInstance();

        currentTeam = c.employeeTeam(leaderName); 
        if (currentTeam != null) {
            throw new ExEmployeeNotAvailable(cmdParts[2], currentTeam.getName());
        }

        if (c.teamExists(cmdParts[1])) throw new ExTeamExists();

        t = c.createTeam(cmdParts[1], leaderName); 

        addUndoCommand(this);   
        clearRedoList();
        
        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeTeam(t);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addTeam(t);
        addUndoCommand(this);
    }
    
}
