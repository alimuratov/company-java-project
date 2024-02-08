
import java.util.ArrayList;

// abstract class means that some of the methods will be implemented by its subclasses
public abstract class RecordedCommand implements Command {
    public abstract void undoMe();
    public abstract void redoMe();

    // undoList and redoList are shared among the subclasses of the RecordedCommand
    private static ArrayList<RecordedCommand> undoList = new ArrayList<>();
    private static ArrayList<RecordedCommand> redoList = new ArrayList<>();

    // static methods are called on the entire class. static methods cannot use instance attributes
    protected static void addUndoCommand(RecordedCommand cmd) {
        undoList.add(cmd);
    }

    protected static void addRedoCommand(RecordedCommand cmd) {
        redoList.add(cmd);
    }

    protected static void clearRedoList() {
        redoList.clear();
    }

    public static void undoOneCommand() {
        if (undoList.size() > 0) {
            int lastIndex = undoList.size() - 1;
            undoList.get(lastIndex).undoMe();
            undoList.remove(lastIndex);
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public static void redoOneCommand() {
        if (redoList.size() > 0) {
            // remove() returns the last command and deletes it from the list
            RecordedCommand r = redoList.remove(redoList.size() - 1);
            r.redoMe();
        } else {
            System.out.println("Nothing to redo.");
        }
    }

}
