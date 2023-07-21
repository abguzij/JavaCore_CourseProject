import commands.CommandInputManager;
import exceptions.InvalidCommandException;

public class Main {
    public static void main(String[] args) {
        System.out.println("<<<<<<<< Добро пожаловать >>>>>>>>");

        CommandInputManager commandInputManager = CommandInputManager.getInstance();
        while (!CommandInputManager.checkIfProgramIsQuited()) {
            try {
                commandInputManager.processCommand(CommandInputManager.scanCommand());
            } catch (InvalidCommandException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("<<<<<<<< Спасибо, что используете нашу программу! >>>>>>>>");
    }
}