package commands;

import exceptions.InvalidCommandException;
import input.ConsoleInputUtils;

public class CommandInputManager {
    private static CommandInputManager instance = null;
    private ProgramStateAndOnCommandActionsManager programStateAndOnCommandActionsManager;

    private CommandInputManager() {
        programStateAndOnCommandActionsManager = ProgramStateAndOnCommandActionsManager.getInstance();
    }
    public static CommandInputManager getInstance() {
        if (instance == null) instance = new CommandInputManager();
        return instance;
    }

    public static boolean checkIfProgramIsQuited(){
        return ProgramStateAndOnCommandActionsManager.ProgramState.getProgramIsQuitedFlag();
    }
    public void processCommand(String command) throws InvalidCommandException {
        programStateAndOnCommandActionsManager.executeCommand(command);
    }
    public static String scanCommand(){
        System.out.print("Введите команду: ");
        return ConsoleInputUtils.scanToFirstWhitespace().toLowerCase();
    }
}
