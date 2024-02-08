public class CmdJoinTeam extends RecordedCommand {
    private Employee e = null;
    private Team t = null;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeNotFound, ExTeamNotFound, ExEmployeeNotAvailable {
        Team employeeTeam = null;
        if (cmdParts.length < 3) throw new ExInsufficientArguments();

        Company c = Company.getInstance();
        e = c.searchEmployee(cmdParts[1]);
        t = c.searchTeam(cmdParts[2]);

        employeeTeam = t.addTeamMember(e);

        if (employeeTeam != null) throw new ExEmployeeNotAvailable(e.getName(), employeeTeam.getName());
        
        System.out.println("Done.");
        
        addUndoCommand(this);
        clearRedoList();
    }

    @Override
    public void undoMe() {
        t.removeTeamMember(e);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        t.addTeamMember(e);
        addUndoCommand(this);
    }
    
}
