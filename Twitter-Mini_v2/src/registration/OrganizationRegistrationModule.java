package registration;

import data.UserData;
import exceptions.*;
import input.ConsoleInputUtils;
import input.InfoMessage;
import entity.user.Organisation;
import entity.user.User;
import entity.user.UserType;

import java.time.LocalDate;

public class OrganizationRegistrationModule extends UserRegistrationModule {

    public OrganizationRegistrationModule(UserData userData) {
        super(userData);
    }

    @Override
    public User createNewUser() {
        return new Organisation(
                LoginCreationUtils.createNewLogin(),
                PasswordCreationUtils.createNewPassword(),
                LocalDate.now(),
                UserType.ORGANISATION,
                OrganizationNameCreationUtils.createNewOrganizationName(),
                TypeOfActivityCreationUtils.createNewTypeOfActivity(),
                FoundationDateCreationUtils.createNewFoundationDate()
        );
    }
    private static abstract class FoundationDateCreationUtils extends BeginningDateCreationUtils {
        private static final Integer MAX_MONTHS_DELTA = 1;
        private static String showFoundationDateInputDialog() {
            System.out.println("---------------[ДАТА ОСНОВАНИЯ ОРГАНИЗАЦИИ]---------------");
            System.out.print(InfoMessage.foundationDateDialogMessage);
            return ConsoleInputUtils.scanToFirstWhitespace();
        }
        private static LocalDate createNewFoundationDate() {
            while (true) {
                try {
                    String currentProcessingStrDate = showFoundationDateInputDialog();
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
        private static void validateLocalDateInput(LocalDate currentProcessingDate) throws IncorrectDateInputException {
            checkIfDateIsAfterCurrentDate(currentProcessingDate);
            checkFoundationDate(currentProcessingDate);
        }
        private static void checkFoundationDate(LocalDate currentProcessingDate) throws IncorrectDateInputException {
            Integer monthsDelta = getPeriodBetweenDateAndCurrentDate(currentProcessingDate).getMonths();
            Integer yearsDelta = getPeriodBetweenDateAndCurrentDate(currentProcessingDate).getYears();

            if(yearsDelta.equals(0) && (monthsDelta > MAX_MONTHS_DELTA || monthsDelta.equals(0))){
                throw new IncorrectDateInputException(ExceptionMessage.foundationDateExceptionMessage);
            }
        }
    }
    private static abstract class OrganizationNameCreationUtils {
        private static String showOrganizationNameInputDialog(){
            System.out.println("---------------[НАИМЕНОВАНИЕ ОРГАНИЗАЦИИ]---------------");
            System.out.print("Ведите наименование организации: ");
            return ConsoleInputUtils.scanLine();
        }
        private static String createNewOrganizationName() {
            while (true) {
                try {
                    String organizationName = showOrganizationNameInputDialog();
                    checkIfOrganizationNameIsEmpty(organizationName);
                    return organizationName;
                } catch (IncorrectOrganizationNameException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        private static void checkIfOrganizationNameIsEmpty(String organizationName)
                throws IncorrectOrganizationNameException {
            if(organizationName == null || organizationName.isEmpty()){
                throw new IncorrectOrganizationNameException(ExceptionMessage.organizationNameIsEmptyExceptionMessage);
            }
        }
    }
    private static abstract class TypeOfActivityCreationUtils {
        private static String showTypeOfActivityInputDialog(){
            System.out.println("---------------[РОД ДЕЯТЕЛЬНОСТИ ОРГАНИЗАЦИИ]---------------");
            System.out.print("Введите род деятельности: ");
            return ConsoleInputUtils.scanLine();
        }

        private static String createNewTypeOfActivity() {
            while (true) {
                try {
                    String typeOfActivity = showTypeOfActivityInputDialog();
                    checkIfTypeOfActivityIsEmpty(typeOfActivity);
                    return typeOfActivity;
                } catch (IncorrectOrganizationTypeOfActivityException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        private static void checkIfTypeOfActivityIsEmpty(String typeOfActivity)
                throws IncorrectOrganizationTypeOfActivityException {
            if(typeOfActivity == null || typeOfActivity.isEmpty()){
                throw new IncorrectOrganizationTypeOfActivityException(
                        ExceptionMessage.organizationTypeOfActivityIsEmptyExceptionMessage
                );
            }
        }
    }
}
