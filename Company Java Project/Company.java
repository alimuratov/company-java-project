import java.util.*;

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;

    private static Company instance = new Company();

    // Constructor

    private Company() {
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
        allProjects = new ArrayList<>();
    }

    // Getters

    public static Company getInstance() {
        return instance;
    }

    public ArrayList<Team> getTeams() {
        return allTeams;
    }

    // Setters

    // Employee logic

    // Adds the specified employee to the list of employees working for the company
    public void addEmployee(Employee e) {
        allEmployees.add(e);
        Collections.sort(allEmployees);
    }

    // Removes the specified employee from the list of employees working for the company
    public void removeEmployee(Employee e) {
        allEmployees.remove(e);
    } 

    // Prints information about all employees working for the company
    public void listEmployees() {
        Employee.listEmployees(allEmployees);
    }

    // Searches employee in the list of employees by the specified name
    public Employee searchEmployee(String name) throws ExEmployeeNotFound {
        for (Employee e : allEmployees) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        throw new ExEmployeeNotFound();
    }

    // Returns true if there is an employee with a specified name working for the company
    public boolean isEmployee(String name) {
        try {
            searchEmployee(name);
            return true;
        }
        catch (ExEmployeeNotFound e) {
            return false;
        }
    }

    // Finds the team of the employee by their name
    public Team employeeTeam(String employeeName) {
        for (Team t : allTeams) {
            if (t.getLeaderName().equals(employeeName) || t.searchForTeamMember(employeeName)) {
                return t;
            }
        }
        return null;
    }

    // Prints information about all employee leaves
    public void listAllLeaves() {
        Employee.listAllLeaves(allEmployees);
    }

    // Team logic

    // Creates a team and adds it to the list of teams working for the company
    public Team createTeam(String teamName, String leaderName) throws ExEmployeeNotFound {
        Employee e = searchEmployee(leaderName);
        e.setRole(RLeader.getInstance());
        Team t = new Team(teamName, e);
        allTeams.add(t);
        Collections.sort(allTeams);
        return t;
    }

    // Removes the specified team from the list of teams working for the company
    public void removeTeam(Team t) {
        allTeams.remove(t);
    }

    // Adds already created team to the list of teams working for the company
    public void addTeam(Team t) {
        allTeams.add(t);
        Collections.sort(allTeams);
    }

    // Print information about all teams working for the company
    public void listTeams() {
        Team.list(allTeams);
    }

    // Searches for the team by its name
    public Team searchTeam(String teamName) throws ExTeamNotFound {
        for (Team t : allTeams) {
            if (t.getName().equals(teamName)) {
                return t;
            }
        }
        throw new ExTeamNotFound();
    }

    // Returns true if a team with the given name exists within the company.
    public boolean teamExists(String teamName) {
        try {
            searchTeam(teamName);
            return true;
        } catch (ExTeamNotFound e) {
            return false;
        }
    }

    // Project logic

    // Adds given project to the list of projects belonging to the company
    public void addProject(Project p) {
        allProjects.add(p);
        Collections.sort(allProjects);
    }

    // Removes the specified project from the list of projects belonging to the company
    public void removeProject(Project p) {
        allProjects.remove(p);
    }

    // Prints information about all projects belonging to the company
    public void listProjects() {
        Project.list(allProjects);
    }

    // Searches for the project by its name
    public Project searchProject(String projectName) throws ExProjectNotFound {
        for (Project p : allProjects) {
            if (p.getName().equals(projectName)) {
                return p;
            }
        }
        throw new ExProjectNotFound();
    }

    // Returns true if a project with the given name belongs to the company.
    public boolean projectExists(String projectName) {
        try {
            searchProject(projectName);
            return true;
        } catch (ExProjectNotFound e) {
            return false;
        }
    }

    // Returns an array of projects that the specified employee is working on
    public ArrayList<Project> findEmployeeProjects(Employee e) {
        ArrayList<Project> projects = new ArrayList<>();
        for (Project p : allProjects) {
            Team t = p.getTeam();
            if (t != null && (t.searchForTeamMember(e.getName()) || t.getLeaderName().equals(e.getName()))) {
                projects.add(p);
            }
        }
        return projects;
    }

    // Returns an array of projects that the specified team is working on
    public ArrayList<Project> searchTeamProjects(String teamName) {
        ArrayList<Project> teamProjects = new ArrayList<>();
        for (Project p : allProjects) {
            if (p.getTeam() != null && p.getTeam().getName().equals(teamName)) {
                teamProjects.add(p);
            }
        }
        return teamProjects;
    }

}
