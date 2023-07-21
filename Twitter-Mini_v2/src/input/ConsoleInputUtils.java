package input;

import java.util.Scanner;

public abstract class ConsoleInputUtils {
    private static final Scanner consoleInputScanner = new Scanner(System.in);
    public static final Integer STRING_BEGINNING_INDEX = 0;
    public static String scanToFirstWhitespace(){
        String line = scanLine();
        if(line.contains(" ")){
            return line.substring(STRING_BEGINNING_INDEX, line.indexOf(' '));
        }
        return line;
    }

    public static String scanLine(){
        return consoleInputScanner.nextLine().trim();
    }
}
