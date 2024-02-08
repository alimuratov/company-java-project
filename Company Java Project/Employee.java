import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee>{

    private String name;
    private int entitledAnnualLeaves;
    private int usedAnnualLeaves;
    private ArrayList<Leave> leaves;

    private Role role = RNormalMember.getInstance(); 

    // Constructors

    public Employee(String n, int yle) {
        name = n;
        entitledAnnualLeaves = yle;
        usedAnnualLeaves = 0;
        leaves = new ArrayList<>(); 
    }

    // Getters

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
    
    public int getEntitledAnnualLeaves() {
        return entitledAnnualLeaves;
    }

    public int getUsedAnnualLeaves() {
        return usedAnnualLeaves;
    }

    // Setters

    public void setRole(Role r) {
        role = r;
    }

    // Static methods

    // Searches for employee with the specified name within the provided list
    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) {
        for (Employee e : list) {
            if (e.getName().equals(nameToSearch)) {
                return e;
            }
        }
        return null;
    }

    // Prints the names of employees along with their entitled annual leave days
    public static void listEmployees(ArrayList<Employee> allEmployees) {
        for (Employee e : allEmployees) {
            System.out.printf("%s (Entitled Annual Leaves: %d days)\n", e.name, e.entitledAnnualLeaves);
        }
    }

    // Prints the names of employees along with their leaves
    public static void listAllLeaves(ArrayList<Employee> allEmployees) {
        for (Employee e : allEmployees) {
            System.out.println(e.getName() + ": " + e.leaveString());
        }
    }

    // Returns true if there's an overlap between two specified leave periods, otherwise false.
    public static boolean overlap(Leave l1, Leave l2) {
        if (l1.getStartDay().compareTo(l2.getEndDay()) <= 0 && l2.getStartDay().compareTo(l1.getEndDay()) <= 0) {
            return true;
        }
        return false;
    }

    // Instance methods

    //  Prints the name of an employee along with their leaves and a role
    public void listEmployeeInfo() {
        role.listTeamMember(this);
    }

    // Prints all leaves of the employee
    public String leaveString() {
        String res = "";
    
        boolean firstEntryFlag = true;
        boolean loopExecuted = false;

        Day s = SystemDate.getInstance();
        for (Leave leave : leaves) {
            if (leave.getEndDay().compareTo(s) == -1) {
                continue;
            }
            if (firstEntryFlag) {
                loopExecuted = true;
                res += String.format("%s to %s", leave.getStartDay(), leave.getEndDay());
                firstEntryFlag = false;
            }
            else res += String.format(", %s to %s", leave.getStartDay(), leave.getEndDay());
        }
        if (!loopExecuted) {
            res += "--";
        }
        return res;
    }

    // Adds leave to the list of leaves of the employee
    public Leave addLeave(Leave leave) throws ExOverlappingLeaves, ExInsufficientBalanceOfLeaves, ExLeaveInProjectFinalStage {
        for (Leave l : leaves) {
            if (DayInterval.isOverlap(leave, l)) {
                return l;
            }
        }
        
        Company c = Company.getInstance();
        ArrayList<Project> projects = c.findEmployeeProjects(this);
        
        for (Project p : projects) {
            Day finalStageStartDay = p.getEndDay();
            if (Day.daysBetween(p.getStartDay(), p.getEndDay()) < 5) {
                finalStageStartDay = p.getStartDay();
            } else {
                for (int i = 0; i < 4; i++) {
                    finalStageStartDay = finalStageStartDay.prev();
                }
            }

            if (leave.getStartDay().compareTo(finalStageStartDay) >= 0 && leave.getStartDay().compareTo(p.getEndDay()) <= 0 ||
                leave.getEndDay().compareTo(finalStageStartDay) >= 0 && leave.getEndDay().compareTo(p.getEndDay()) <= 0
            ) {
                throw new ExLeaveInProjectFinalStage(p.getName(), finalStageStartDay, p.getEndDay());
            }
        }

        if (usedAnnualLeaves + leave.getLeaveDuration() > entitledAnnualLeaves) {
            throw new ExInsufficientBalanceOfLeaves(entitledAnnualLeaves - usedAnnualLeaves);
        }
        
        leaves.add(leave);
        Collections.sort(leaves);
        
        usedAnnualLeaves += leave.getLeaveDuration();
        return null;
    }

    // Removes a specified leave from an employee's current list of leaves
    public void removeLeave(Leave leave) {
        leaves.remove(leave);
        usedAnnualLeaves -= leave.getLeaveDuration();
    }

    // Computes the fraction of the specified interval (project lifetime) during which the employee will be working and not on leave
    public double workload(DayInterval interval) {
        double totalOverlapDays = 0;
        for (Leave leave : leaves) {
            totalOverlapDays += leave.overlapSize(interval);
        }
        return 1 - totalOverlapDays / interval.getIntervalLength();
    }

    // Overriden methods

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }

}
