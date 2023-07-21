package input;

import exceptions.ExceptionMessage;
import exceptions.IncorrectPublicationThemeException;
import validation.StringInputValidator;

public abstract class PublicationThemeInput {
    private static final Integer MIN_THEME_LENGTH = 5;
    public static String scanPublicationTheme(){
        while (true) {
            try {
                String currentProcessingTheme = showPublicationThemeCreationDialog();
                validatePublicationTheme(currentProcessingTheme);
                return currentProcessingTheme;
            } catch (IncorrectPublicationThemeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static String showPublicationThemeCreationDialog(){
        System.out.println(InfoMessage.publicationThemeRules);
        System.out.print("Введите тему публикации: ");
        return ConsoleInputUtils.scanLine();
    }

    private static void validatePublicationTheme(String publicationTheme)
            throws IncorrectPublicationThemeException {
        StringInputValidator stringInputValidator = new StringInputValidator(publicationTheme);
        checkIfPublicationThemeIsEmpty(stringInputValidator);
        checkIfPublicationThemeContainsLessCharactersThenRequired(stringInputValidator);
    }

    private static void checkIfPublicationThemeIsEmpty(StringInputValidator stringInputValidator)
            throws IncorrectPublicationThemeException {
        if(stringInputValidator.checkIfNullOrEmpty()){
            throw new IncorrectPublicationThemeException(ExceptionMessage.publicationThemeIsEmptyExceptionMessage);
        }
    }
    private static void checkIfPublicationThemeContainsLessCharactersThenRequired
            (StringInputValidator stringInputValidator)
            throws IncorrectPublicationThemeException {
        if(!stringInputValidator.checkSize(MIN_THEME_LENGTH)){
            throw new IncorrectPublicationThemeException(
                    ExceptionMessage.publicationThemeContainsLessSymbolsThenRequiredExceptionMessage
            );
        }
    }
}
