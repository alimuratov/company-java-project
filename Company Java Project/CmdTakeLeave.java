public class CmdTakeLeave extends RecordedCommand {
    private Employee e = null;
    private Leave leave = null;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeNotFound, ExOverlappingLeaves, ExInsufficientBalanceOfLeaves, ExLeaveInProjectFinalStage {
        Leave overlappingLeave = null;
        
        if (cmdParts.length < 4) throw new ExInsufficientArguments();
        
        Company c = Company.getInstance();
        
        e = c.searchEmployee(cmdParts[1]);
        leave = new Leave(cmdParts[2], cmdParts[3]);
        overlappingLeave = e.addLeave(leave);
        
        if (overlappingLeave != null) throw new ExOverlappingLeaves(overlappingLeave.getStartDay(), overlappingLeave.getEndDay());
        
        System.out.printf("Done.  %s's remaining annual leave: %d days\n", e.getName(), e.getEntitledAnnualLeaves() - e.getUsedAnnualLeaves());
        
        addUndoCommand(this);
        clearRedoList();
    }

    @Override
    public void undoMe() {
        e.removeLeave(leave);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        try {
            e.addLeave(leave);
        } catch (Exception ex) {
            // we can do nothing because we can be sure that if there is any action in redo, then after corresponding undo no additional action was taken
        }
        addUndoCommand(this);
    }
    
}
