package registration;

import data.UserData;
import exceptions.*;
import input.ConsoleInputUtils;
import input.InfoMessage;
import validation.StringInputValidator;
import entity.user.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

abstract class UserRegistrationModule {
    private static UserData userDataLayer;
    public UserRegistrationModule(UserData userData) {
        userDataLayer = userData;
    }

    public abstract User createNewUser();
    protected static abstract class LoginCreationUtils {
        private static final Integer MIN_LOGIN_SIZE = 3;
        private static final Integer MAX_LOGIN_SIZE = 10;
        protected static String createNewLogin(){
            while (true) {
                try {
                    String currentProcessingLogin = showLoginCreationDialog();
                    validateLogin(currentProcessingLogin);
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

            checkIfLoginAlreadyExists(currentProcessingLogin);
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

        private static void checkIfLoginAlreadyExists(String currentProcessingLogin)
                throws IncorrectLoginException {
            if(userDataLayer.checkIfContainsLogin(currentProcessingLogin)){
                throw new IncorrectLoginException(ExceptionMessage.loginAlreadyExistsExceptionMessage);
            }
        }
    }
    protected static abstract class PasswordCreationUtils {
        private static final Integer MIN_PASSWORD_SIZE = 5;

        protected static String createNewPassword() {
            while (true) {
                try {
                    String currentProcessingPassword = showPasswordCreationDialog();
                    validatePassword(currentProcessingPassword);
                    return currentProcessingPassword;
                } catch (IncorrectPasswordException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        private static String showPasswordCreationDialog() {
            System.out.println(InfoMessage.passwordRulesMessage);
            System.out.print("Введите пароль: ");
            return ConsoleInputUtils.scanLine();
        }

        private static void validatePassword(String currentProcessingPassword) throws IncorrectPasswordException {
            StringInputValidator stringInputValidator = new StringInputValidator(currentProcessingPassword);
            validatePasswordSize(stringInputValidator);
            checkIfPasswordContainsWhitespaces(stringInputValidator);
            checkIfPasswordContainsInvalidSymbols(stringInputValidator);
            checkIfPasswordContainsAllTypesOfSymbols(stringInputValidator);
        }
        private static void validatePasswordSize(StringInputValidator stringInputValidator)
                throws IncorrectPasswordException {
            if(!stringInputValidator.checkSize(MIN_PASSWORD_SIZE)){
                throw new IncorrectPasswordException(ExceptionMessage.incorrectPasswordSizeExceptionMessage);
            }
        }
        private static void checkIfPasswordContainsWhitespaces(StringInputValidator stringInputValidator)
                throws IncorrectPasswordException{
            if(stringInputValidator.checkIfContainsWhitespaces()){
                throw new IncorrectPasswordException(ExceptionMessage.passwordContainsWhitespacesExceptionMessage);
            }
        }
        private static void checkIfPasswordContainsInvalidSymbols(StringInputValidator stringInputValidator)
                throws IncorrectPasswordException {
            if(!stringInputValidator.checkIfContainsOnlyLatinSymbols()){
                throw new IncorrectPasswordException(ExceptionMessage.invalidCharacterInPasswordExceptionMessage);
            }
        }
        private static void checkIfPasswordContainsAllTypesOfSymbols(StringInputValidator stringInputValidator)
                throws IncorrectPasswordException {
            if (!stringInputValidator.checkIfContainsAllTypesOfSymbols()) {
                throw new IncorrectPasswordException(
                        ExceptionMessage.passwordContainsLessCharacterTypesThenExpectedExceptionMessage
                );
            }
        }
    }
    protected static abstract class BeginningDateCreationUtils {
        protected static final LocalDate currentDate = LocalDate.now();
        protected static LocalDate convertStrDateToLocalDate(String currentProcessingStrDate)
                throws IncorrectDateInputException {
            try {
                return LocalDate.parse(currentProcessingStrDate);
            } catch (DateTimeParseException ex) {
                throw new IncorrectDateInputException(ExceptionMessage.incorrectDateFormatInputExceptionMessage);
            }
        }

        protected static void checkIfDateIsAfterCurrentDate(LocalDate currentProcessingDate) throws IncorrectDateInputException{
            if(currentProcessingDate.isAfter(currentDate)){
                throw new IncorrectDateInputException(ExceptionMessage.dateIsAfterCurrentDateExceptionMessage);
            }
        }

        protected static Period getPeriodBetweenDateAndCurrentDate(LocalDate date){
            return Period.between(date, currentDate);
        }
    }
}
