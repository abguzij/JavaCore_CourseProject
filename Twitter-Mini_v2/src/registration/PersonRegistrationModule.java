package registration;

import data.UserData;
import exceptions.ExceptionMessage;
import exceptions.IncorrectDateInputException;
import exceptions.IncorrectPersonNameException;
import exceptions.IncorrectPersonSurnameException;
import input.ConsoleInputUtils;
import input.InfoMessage;
import entity.user.Person;
import entity.user.User;
import entity.user.UserType;

import java.time.LocalDate;

public class PersonRegistrationModule extends UserRegistrationModule {
    public PersonRegistrationModule(UserData userData) {
        super(userData);
    }

    @Override
    public User createNewUser() {
        return new Person(
                LoginCreationUtils.createNewLogin(),
                PasswordCreationUtils.createNewPassword(),
                LocalDate.now(),
                UserType.PERSON,
                NameCreationUtils.createPersonName(),
                SurnameCreationUtils.createPersonSurname(),
                BirthDateCreationUtils.createNewBirthDate()
        );
    }

    private static abstract class NameCreationUtils {
        private static String showNameCreationDialog(){
            System.out.println("---------------[ВАШЕ ИМЯ]---------------");
            System.out.print("Введите имя: ");
            return ConsoleInputUtils.scanToFirstWhitespace();
        }

        private static String createPersonName(){
            while (true) {
                try {
                    String name = showNameCreationDialog();
                    checkIfNameIsNotEmpty(name);

                    return name;
                } catch (IncorrectPersonNameException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        private static void checkIfNameIsNotEmpty(String name) throws IncorrectPersonNameException {
            if(name == null || name.isEmpty()){
                throw new IncorrectPersonNameException(ExceptionMessage.userNameIsEmptyExceptionMessage);
            }
        }

    }
    private static abstract class SurnameCreationUtils {
        private static String showSurnameCreationDialog(){
            System.out.println("---------------[ВАША ФАМИЛИЯ]---------------");
            System.out.print("Введите фамилию: ");
            return ConsoleInputUtils.scanToFirstWhitespace();
        }

        private static String createPersonSurname(){
            while (true) {
                try {
                    String surname = showSurnameCreationDialog();
                    checkIfSurnameIsNotEmpty(surname);
                    return surname;
                } catch (IncorrectPersonSurnameException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        private static void checkIfSurnameIsNotEmpty(String surname) throws IncorrectPersonSurnameException {
            if(surname == null || surname.isEmpty()){
                throw new IncorrectPersonSurnameException(ExceptionMessage.userSurnameIsEmptyExceptionMessage);
            }
        }

    }
    private static abstract class BirthDateCreationUtils extends BeginningDateCreationUtils {
        private static final Integer MIN_PERSON_AGE = 10;
        private static String showBirthDateInputDialog() {
            System.out.println("---------------[ДАТА РОЖДЕНИЯ]---------------");
            System.out.print(InfoMessage.birthDateDialogMessage);
            return ConsoleInputUtils.scanToFirstWhitespace();
        }

        private static LocalDate createNewBirthDate() {
            while (true) {
                try {
                    String currentProcessingStrDate = showBirthDateInputDialog();
                    LocalDate currentProcessingDate = convertStrDateToLocalDate(currentProcessingStrDate);

                    if(currentProcessingDate != null){
                        validateLocalDateInput(currentProcessingDate);
                    }

                    return currentProcessingDate;
                } catch (IncorrectDateInputException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        private static void validateLocalDateInput(LocalDate currentProcessingDate)
                throws IncorrectDateInputException {
            checkIfDateIsAfterCurrentDate(currentProcessingDate);
            checkPersonsAge(currentProcessingDate);
        }

        private static void checkPersonsAge(LocalDate currentProcessingDate) throws IncorrectDateInputException {
            int age = getPeriodBetweenDateAndCurrentDate(currentProcessingDate).getYears();

            if(age < MIN_PERSON_AGE){
                throw new IncorrectDateInputException(ExceptionMessage.userShouldBeOlderExceptionMessage);
            }
        }
    }
}
