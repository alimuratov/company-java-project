public class CmdStartNewDay extends RecordedCommand {
    private String oldDate;
    private String newDate;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments {
        if (cmdParts.length < 2) throw new ExInsufficientArguments();

        newDate = cmdParts[1];

        if (SystemDate.getInstance() == null) {
            SystemDate.createTheInstance(newDate);
        } else {
            SystemDate sd = SystemDate.getInstance();

            oldDate = sd.toString();
            sd.set(newDate);

            addUndoCommand(this);
            clearRedoList();
            
            System.out.println("Done.");
        }
    }

    @Override
    public void undoMe() {
        SystemDate.getInstance().set(oldDate);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        SystemDate.getInstance().set(newDate);
        addUndoCommand(this);
    }
    
}
