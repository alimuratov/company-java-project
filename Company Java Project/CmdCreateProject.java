public class CmdCreateProject extends RecordedCommand {
    private Project p;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExProjectExists, NumberFormatException {
        try {
            if (cmdParts.length < 4) throw new ExInsufficientArguments();
            Company c = Company.getInstance();
            if (c.projectExists(cmdParts[1])) throw new ExProjectExists();
            p = new Project(cmdParts[1], cmdParts[2], Integer.parseInt(cmdParts[3])); // Number format exception 
            c.addProject(p);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format for project duration!");
        } 
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeProject(p);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addProject(p);
        addUndoCommand(this);
    }
    
}
