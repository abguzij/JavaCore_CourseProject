package input;

import exceptions.ExceptionMessage;
import exceptions.IncorrectPublicationThemeException;
import validation.StringInputValidator;

public abstract class PublicationTextInput {
    private static final Integer MAX_TEXT_LENGTH = 255;
    public static String scanPublicationText(){
        while (true) {
            try {
                String currentProcessingText = showPublicationTextCreationDialog();
                validatePublicationText(currentProcessingText);
                return currentProcessingText;
            } catch (IncorrectPublicationThemeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static String showPublicationTextCreationDialog(){
        System.out.println(InfoMessage.publicationTextRules);
        System.out.print("Введите текст публикации: ");
        return ConsoleInputUtils.scanLine();
    }

    private static void validatePublicationText(String publicationText)
            throws IncorrectPublicationThemeException {
        StringInputValidator stringInputValidator = new StringInputValidator(publicationText);
        checkIfPublicationTextIsEmpty(stringInputValidator);
        checkIfPublicationTextContainsMoreCharactersThenRequired(stringInputValidator);
    }

    private static void checkIfPublicationTextIsEmpty(StringInputValidator stringInputValidator)
            throws IncorrectPublicationThemeException {
        if(stringInputValidator.checkIfNullOrEmpty()){
            throw new IncorrectPublicationThemeException(ExceptionMessage.publicationTextIsEmptyExceptionMessage);
        }
    }
    private static void checkIfPublicationTextContainsMoreCharactersThenRequired
            (StringInputValidator stringInputValidator)
            throws IncorrectPublicationThemeException {
        if(!stringInputValidator.checkMaxSize(MAX_TEXT_LENGTH)){
            throw new IncorrectPublicationThemeException(
                    ExceptionMessage.publicationTextContainsMoreSymbolsThenRequiredExceptionMessage
            );
        }
    }
}
