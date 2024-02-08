public class CmdListTeamMembers implements Command {

    @Override
    public void execute(String[] cmdParts) throws ExTeamNotFound {
        Company c = Company.getInstance();
        Team t = c.searchTeam(cmdParts[1]);
        t.listTeamMembers();
    }
    
}
