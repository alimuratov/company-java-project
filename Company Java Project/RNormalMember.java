public class RNormalMember implements Role {

    private static RNormalMember instance = new RNormalMember();

    private RNormalMember() {}

    public static RNormalMember getInstance() {return instance;}

    @Override
    public void listTeamMember(Employee e) {
        System.out.printf("%-10s%-10s%-13s\n", "Member", e.getName(), e.leaveString());
    }
    
}
