package input;

public class InputDialog {
    public static final boolean TO_WHITESPACE = true;
    public static final boolean WHOLE_LINE = false;

    public InputDialog(String dialogMessage) {
        this.dialogMessage = dialogMessage;
    }
    private String dialogMessage;
    private String infoMessage = null;

    public InputDialog(String dialogMessage, String infoMessage) {
        this.dialogMessage = dialogMessage;
        this.infoMessage = infoMessage;
    }

    public String showInputDialog(boolean scanWholeLine){
        showMessages();
        if(scanWholeLine){
            return ConsoleInputUtils.scanLine();
        }
        return ConsoleInputUtils.scanToFirstWhitespace();
    }
    private void showMessages() {
        if(this.infoMessage != null){
            System.out.println(this.infoMessage);
        }
        System.out.print(this.dialogMessage);
    }
}
