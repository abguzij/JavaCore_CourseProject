package authorization;

import data.UserData;
import exceptions.ExceptionMessage;
import exceptions.IncorrectLoginException;
import exceptions.IncorrectPasswordException;
import input.ConsoleInputUtils;
import validation.StringInputValidator;
import entity.user.User;

public class UserAuthorization {
    private static UserAuthorization instance = null;
    public static UserAuthorization getInstance(UserData allUsers) {
        if(UserAuthorization.instance == null){
            UserAuthorization.instance = new UserAuthorization(allUsers);
        }
        return UserAuthorization.instance;
    }
    private static UserData allUsers;


    private UserAuthorization(UserData allUsers) {
        UserAuthorization.allUsers = allUsers;
    }

    public static User authorizeUser(){
        return authenticateByPassword(ByLoginAuthenticationUtils.userAuthenticationByLogin());
    }

    private static User authenticateByPassword(User user) {
        while (true) {
            try {
                String password = showUserPasswordInputDialog();
                validatePassword(user.getPassword(), password);
                return user;
            } catch (IncorrectPasswordException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void validatePassword(String requiredPassword, String password) throws IncorrectPasswordException {
        StringInputValidator stringInputValidator = new StringInputValidator(password);
        checkIfPasswordIsEmpty(stringInputValidator);
        checkIfPasswordContainsWhitespaces(stringInputValidator);

        checkIfPasswordIsWrong(requiredPassword, password);
    }

    private static void checkIfPasswordIsEmpty(StringInputValidator stringInputValidator)
            throws IncorrectPasswordException {
        if(stringInputValidator.checkIfNullOrEmpty()){
            throw new IncorrectPasswordException(ExceptionMessage.passwordIsEmptyExceptionMessage);
        }
    }
    private static void checkIfPasswordContainsWhitespaces(StringInputValidator stringInputValidator)
            throws IncorrectPasswordException {
        if(stringInputValidator.checkIfContainsWhitespaces()){
            throw new IncorrectPasswordException(ExceptionMessage.passwordContainsWhitespacesExceptionMessage);
        }
    }
    private static void checkIfPasswordIsWrong(String requiredPassword, String password) throws IncorrectPasswordException {
        if(!requiredPassword.equals(password)) {
            throw new IncorrectPasswordException("Неверный пароль! Попробуйте еще раз!");
        }
    }

    private static String showUserPasswordInputDialog(){
        System.out.print("Введите пароль: ");
        return ConsoleInputUtils.scanLine();
    }

    private static abstract class ByLoginAuthenticationUtils {
        private static User userAuthenticationByLogin(){
            while (true) {
                try{
                    String login = showUserLoginInputDialog();
                    validateLoginInput(login);
                    return allUsers.getUserByLogin(login);
                } catch (IncorrectLoginException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        private static String showUserLoginInputDialog(){
            System.out.print("Введите логин: ");
            return ConsoleInputUtils.scanLine();
        }

        private static void validateLoginInput(String login)
                throws IncorrectLoginException {
            StringInputValidator stringInputValidator = new StringInputValidator(login);
            checkIfLoginIsEmpty(stringInputValidator);
            checkIfLoginContainsWhitespaces(stringInputValidator);
            checkIfLoginDoNotExist(login);
        }
        private static void checkIfLoginIsEmpty(StringInputValidator stringInputValidator)
                throws IncorrectLoginException {
            if(stringInputValidator.checkIfNullOrEmpty()){
                throw new IncorrectLoginException(ExceptionMessage.loginIsEmptyExceptionMessage);
            }
        }
        private static void checkIfLoginContainsWhitespaces(StringInputValidator stringInputValidator)
                throws IncorrectLoginException {
            if(stringInputValidator.checkIfContainsWhitespaces()){
                throw new IncorrectLoginException(ExceptionMessage.loginContainsWhitespaceExceptionMessage);
            }
        }
        private static void checkIfLoginDoNotExist(String login)
                throws IncorrectLoginException {
            if(!allUsers.checkIfContainsLogin(login)){
                throw new IncorrectLoginException(ExceptionMessage.loginDoesntExistExceptionMessage);
            }
        }
    }
}
