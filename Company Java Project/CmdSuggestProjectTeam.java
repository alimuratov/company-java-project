import java.util.ArrayList;

public class CmdSuggestProjectTeam implements Command {

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExProjectNotFound {
        Company c = Company.getInstance();

        ArrayList<Team> allTeams = c.getTeams();
        Project p = c.searchProject(cmdParts[1]);

        System.out.printf("During the period of project %s (%s):\n", p.getName(), p.getProjectInterval().toString());
        System.out.printf("  Average manpower (m) and count of existing projects (p) of each team:\n");

        for (Team t : allTeams) {
            System.out.printf("    %s: m=%.2f workers, p=%.2f projects\n", t.getName(), t.manpower(p.getProjectInterval()), t.projectCount(p.getProjectInterval()));
        }

        System.out.printf("  Projected loading factor when a team takes this project %s:\n", p.getName());

        Team teamWithLowestFactor = null;
        double minFactor = Double.MAX_VALUE;

        if (allTeams.size() > 0) {
            teamWithLowestFactor = allTeams.get(0);
            minFactor = (teamWithLowestFactor.projectCount(p.getProjectInterval()) + 1) / teamWithLowestFactor.manpower(p.getProjectInterval());
        }
        for (Team t : allTeams) {
            double factor = (t.projectCount(p.getProjectInterval()) + 1) / t.manpower(p.getProjectInterval());
            
            System.out.printf("    %s: (p+1)/m = %.2f\n", t.getName(), factor);
            
            if (factor < minFactor) {
                teamWithLowestFactor = t;
                minFactor = factor;
            }
        }
        System.out.printf("Conclusion: %s should be assigned to %s for best balancing of loading\n", p.getName(), teamWithLowestFactor.getName());
    }
    
}
