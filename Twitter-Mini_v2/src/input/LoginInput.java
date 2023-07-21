package input;

import data.UserData;
import exceptions.ExceptionMessage;
import exceptions.IncorrectLoginException;
import validation.StringInputValidator;

public class LoginInput {
    private static final Integer MIN_LOGIN_SIZE = 3;
    private static final Integer MAX_LOGIN_SIZE = 10;
    private static LoginInput instance = null;
    private static UserData allUsers;

    private LoginInput(UserData allUsers){
        LoginInput.allUsers = allUsers;
    }

    public static LoginInput getInstance(UserData allUsers) {
        if(instance == null){
            instance = new LoginInput(allUsers);
        }
        return instance;
    }

    public static String scanNewLogin(){
        while (true) {
            try {
                String currentProcessingLogin = showLoginCreationDialog();
                validateLogin(currentProcessingLogin);
                validateNewLogin(currentProcessingLogin);
                return currentProcessingLogin;
            } catch (IncorrectLoginException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static String searchExistingLogin(){
        while (true) {
            try {
                String currentProcessingLogin = showLoginCreationDialog();
                validateLogin(currentProcessingLogin);
                validateExistingLogin(currentProcessingLogin);
                return currentProcessingLogin;
            } catch (IncorrectLoginException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static String showLoginCreationDialog(){
        System.out.println(InfoMessage.loginRulesMessage);
        System.out.print("Введите логин: ");
        return ConsoleInputUtils.scanLine();
    }

    private static void validateLogin(String currentProcessingLogin)
            throws IncorrectLoginException {
        StringInputValidator stringInputValidator = new StringInputValidator(currentProcessingLogin);
        validateLoginSize(stringInputValidator);
        checkIfLoginContainsWhitespaces(stringInputValidator);
        validateLoginCharacterSet(stringInputValidator);
    }

    private static void validateNewLogin(String login) throws IncorrectLoginException {
        if(LoginInput.allUsers.checkIfContainsLogin(login)){
            throw new IncorrectLoginException(ExceptionMessage.loginAlreadyExistsExceptionMessage);
        }
    }

    private static void validateExistingLogin(String login) throws IncorrectLoginException {
        if(!LoginInput.allUsers.checkIfContainsLogin(login)){
            throw new IncorrectLoginException(ExceptionMessage.loginDoesntExistExceptionMessage);
        }
    }

    private static void validateLoginSize(StringInputValidator stringInputValidator)
            throws IncorrectLoginException {
        if(!stringInputValidator.checkSize(MIN_LOGIN_SIZE, MAX_LOGIN_SIZE)){
            throw new IncorrectLoginException(ExceptionMessage.incorrectLoginSizeExceptionMessage);
        }
    }
    private static void checkIfLoginContainsWhitespaces(StringInputValidator stringInputValidator)
            throws IncorrectLoginException {
        if(stringInputValidator.checkIfContainsWhitespaces()){
            throw new IncorrectLoginException(ExceptionMessage.loginContainsWhitespaceExceptionMessage);
        }
    }
    private static void validateLoginCharacterSet(StringInputValidator stringInputValidator)
            throws IncorrectLoginException {
        if(!stringInputValidator.checkIfContainsOnlyLatinLetters()){
            throw new IncorrectLoginException(ExceptionMessage.loginContainsInvalidCharactersExceptionMessage);
        }
    }
}
