import java.util.ArrayList;

public class Project implements Comparable<Project> {
    private DayInterval projectInterval;
    private Team t = null;
    private String projectName;

    // Constructor

    public Project(String name, String start, int projectLength) {
        Day startDay = new Day(start);
        Day endDay = Day.calculateEndDay(startDay, projectLength);
        projectInterval = new DayInterval(startDay, endDay);
        projectName = name;

    }

    // Getters

    public String getName() {
        return projectName;
    }

    public Team getTeam() {
        return t;
    }

    public Day getEndDay() {
        return projectInterval.getEndDay();
    }

    public Day getStartDay() {
        return projectInterval.getStartDay();
    }

    public DayInterval getProjectInterval() {
        return projectInterval;
    }

    // Setters

    public void setTeam(Team t) {
        this.t = t;
    }

    // Static methods

    // Prints a list of all projects along with their respective start and end dates
    public static void list(ArrayList<Project> allProjects) {
        System.out.printf("%-9s%-13s%-13s%-13s\n", "Project", "Start Day", "End Day", "Team");
        for (Project p : allProjects) {
            String assignedteam = (p.t == null) ? "--" : p.t.getTeamAndMemberNames();
            System.out.printf("%-9s%-13s%-13s%-13s\n", p.projectName, p.projectInterval.getStartDay().toString(), p.projectInterval.getEndDay().toString(), assignedteam);
        }
    }

    // Instance methods

    public void removeAssignedTeam() {
        this.t = null;
    }

    // Calculates the fraction of duration of overlap between the current project's lifetime and the specified interval (another project's lifetime)
    public double intervalCoverage(DayInterval interval) {
        return (0.0 + projectInterval.overlapSize(interval)) / interval.getIntervalLength();
    }

    // Overriden methods

    @Override
    public int compareTo(Project other) {
        return this.projectName.compareTo(other.projectName);
    }
}
