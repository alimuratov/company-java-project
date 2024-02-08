public class CmdAssign extends RecordedCommand {
    private Team t;
    private Project p;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExProjectNotFound, ExTeamNotFound {
        if (cmdParts.length < 3) throw new ExInsufficientArguments();
        Company c = Company.getInstance();
        t = c.searchTeam(cmdParts[2]);
        p = c.searchProject(cmdParts[1]);
        p.setTeam(t);
        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        p.removeAssignedTeam();
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        p.setTeam(t);
        addUndoCommand(this);
    }
    
}
