import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.print("Please input the file pathname: ");
        String filepath = in.nextLine();
        Scanner inFile = null;

        try {

            inFile = new Scanner(new File(filepath));
            String cmdLine1 = inFile.nextLine();
            String[] cmdLine1Parts = cmdLine1.split("\\|");
            System.out.println("\n> " + cmdLine1);
            SystemDate.createTheInstance(cmdLine1Parts[1]);

            while (inFile.hasNext()) {
                String cmdLine = inFile.nextLine().trim();
                
                //Blank lines exist in data file as separators.  Skip them.
                if (cmdLine.equals("")) continue;  

                System.out.println("\n> "+cmdLine);

                String[] cmdParts = cmdLine.split("\\|");

                try {
                
                    if (cmdParts[0].equals("hire")) {
                        (new CmdHire()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listEmployees")) {
                        (new CmdListEmployees()).execute(cmdParts);
                    } else if (cmdParts[0].equals("setupTeam")) {
                        (new CmdSetupTeam()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listTeams")) {
                        (new CmdListTeams()).execute(cmdParts);
                    } else if (cmdParts[0].equals("startNewDay")) {
                        (new CmdStartNewDay()).execute(cmdParts);
                    } else if (cmdParts[0].equals("createProject")) {
                        (new CmdCreateProject()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listProjects")) {
                        (new CmdListProjects()).execute(cmdParts);
                    } else if (cmdParts[0].equals("assign")) {
                        (new CmdAssign()).execute(cmdParts);
                    } else if (cmdParts[0].equals("takeLeave")) {
                        (new CmdTakeLeave()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listLeaves")) {
                        (new CmdListLeaves()).execute(cmdParts);
                    } else if (cmdParts[0].equals("joinTeam")) {
                        (new CmdJoinTeam()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listTeamMembers")) {
                        (new CmdListTeamMembers()).execute(cmdParts);
                    } else if (cmdParts[0].equals("suggestProjectTeam")) {
                        (new CmdSuggestProjectTeam()).execute(cmdParts);
                    } else if (cmdParts[0].equals("undo")) {
                        RecordedCommand.undoOneCommand();
                    } else if (cmdParts[0].equals("redo")) {
                        RecordedCommand.redoOneCommand();
                    }
                    else {
                        throw new ExWrongCommand();
                    } 
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (InputMismatchException e) {
            System.out.println("File content problem! Program terminated!");
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format for project duration!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        finally {
            if (inFile != null) {
                inFile.close();
            }
            in.close();
        }
    }
}