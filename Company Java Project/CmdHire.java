
public class CmdHire extends RecordedCommand {
    private Employee e;

    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeExists {
        try {
            if (cmdParts.length < 3) throw new ExInsufficientArguments();

            Company c = Company.getInstance();
            String employeeName = cmdParts[1];

            if (c.isEmployee(employeeName)) throw new ExEmployeeExists();

            e = new Employee(cmdParts[1], Integer.parseInt(cmdParts[2]));
            c.addEmployee(e);
            System.out.println("Done.");

            addUndoCommand(this);
            clearRedoList();
            
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format for annual leaves!");
        }
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeEmployee(e);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addEmployee(e);  
        addUndoCommand(this);
    }

}
