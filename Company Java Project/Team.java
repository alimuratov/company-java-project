import java.util.*;

public class Team implements Comparable<Team> {

    private String teamName;
    private Employee head;
    private ArrayList<Employee> teamMembers;
    private Day dateSetup;
    
    public Team(String n, Employee hd) {
        teamName = n;
        head = hd;
        dateSetup = SystemDate.getInstance().clone();
        teamMembers = new ArrayList<>();
    }

    // Getters

    public String getName() {
        return teamName;
    }

    public String getLeaderName() {
        return head.getName();
    }

    // Setters

    // Static methods

    // Prints the names, leaders, and setup dates for all teams within the company.
    public static void list(ArrayList<Team> list) {
        System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
        for (Team t : list) {
            System.out.printf("%-30s%-10s%-13s\n", t.teamName, t.head.getName(), t.dateSetup);
        }
    }


    // Instance methods

    // Returns a string listing the team name followed by the names of its members
    public String getTeamAndMemberNames() {
        String res = teamName + " (" + head.getName();
        for (Employee e : teamMembers) {
            res +=  String.format(", %s", e.getName());
        }
        res += ")";
        return res;
    }

    // Returns true if an employee with the specified name is a member of the current team.
    public boolean searchForTeamMember(String name) {
        for (Employee e : teamMembers) {
            if (e.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Adds the specified employee to the current team if they are not already a member of another team.
    public Team addTeamMember(Employee e) {
        Company c = Company.getInstance();
        Team employeesTeam = c.employeeTeam(e.getName());
        if (employeesTeam == null) {
            teamMembers.add(e);
            Collections.sort(teamMembers);
        }
        return employeesTeam;
    }

    // Prints the information about team members
    public void listTeamMembers() {
        System.out.printf("%-10s%-10s%-13s\n", "Role", "Name", "Current / coming leaves");
        head.listEmployeeInfo();
        for (Employee e : teamMembers) {
            e.listEmployeeInfo();
        }
    }

    // Removes the specified employee from the team
    public void removeTeamMember(Employee e) {
        teamMembers.remove(e);
    }

    // Calculates and sums up the fractions of time each project assigned to the team overlaps 
    // with the specified interval (another project's lifetime), and then returns the total.
    public double projectCount(DayInterval interval) {
        Company c = Company.getInstance();
        ArrayList<Project> teamProjects = c.searchTeamProjects(this.getName());
        double totalProjectCount = 0;
        for (Project p : teamProjects) {    
            totalProjectCount += p.intervalCoverage(interval);
        }
        return totalProjectCount;
    }

    // Computes and sums up the fractions of time each member 
    // will be working during the specified interval (project's lifetime)
    public double manpower(DayInterval interval) {
        double totalManpower = head.workload(interval);
        for (Employee e : teamMembers) {
            totalManpower += e.workload(interval);
        }
        return totalManpower;
    }

    // Overriden methods

    @Override
    public int compareTo(Team o) {
        // compare by their names
        return this.teamName.compareTo(o.teamName);
    }
}
