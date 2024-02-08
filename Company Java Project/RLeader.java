public class RLeader implements Role {

    private static RLeader instance = new RLeader();

    private RLeader() {}

    public static RLeader getInstance() {return instance;}

    @Override
    public void listTeamMember(Employee e) {
        System.out.printf("%-10s%-10s%-13s\n", "Leader", e.getName(), e.leaveString());
    }
    
}
