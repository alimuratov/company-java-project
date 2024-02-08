public class CmdListLeaves implements Command {

    @Override
    public void execute(String[] cmdParts) throws ExEmployeeNotFound {
        Company c = Company.getInstance();
        if (cmdParts.length == 1) {
            c.listAllLeaves();
        } else if (cmdParts.length == 2) {
            Employee e = c.searchEmployee(cmdParts[1]);
            System.out.println(e.getName() + ": " + e.leaveString());
        }
    }
    
}
