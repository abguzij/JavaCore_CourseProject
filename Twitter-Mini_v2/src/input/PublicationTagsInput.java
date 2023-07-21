package input;

import exceptions.ExceptionMessage;
import exceptions.IncorrectPublicationTagException;
import validation.StringInputValidator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class PublicationTagsInput {
    private static final Integer MIN_TAG_LENGTH = 3;
    private static final Integer MAX_TAG_LENGTH = 15;
    public static List<String> scanPublicationTags(){
        while (true) {
            try {
                String tagsLine = showPublicationTagsLineInputDialog();
                StringInputValidator validator = new StringInputValidator(tagsLine);
                if(!validator.checkIfNullOrEmpty()){
                    List<String> tagsList = splitTagsLineToTags(tagsLine);
                    tagsList.stream().forEach(PublicationTagsInput::validatePublicationTag);
                    return tagsList;
                }
                return new LinkedList<>();
            } catch (IncorrectPublicationTagException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static String scanSinglePublicationTag(){
        while (true) {
            try {
                String tag = showPublicationTagInputDialog();
                validatePublicationTag(tag);
                return tag;
            } catch (IncorrectPublicationTagException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static String showPublicationTagInputDialog(){
        System.out.println(InfoMessage.publicationTagsRules);
        System.out.print("Введите тег публикации: ");
        return ConsoleInputUtils.scanLine();
    }
    private static String showPublicationTagsLineInputDialog(){
        System.out.println(InfoMessage.publicationTagsRules);
        System.out.print("Введите теги публикации: ");
        return ConsoleInputUtils.scanLine();
    }

    private static List<String> splitTagsLineToTags(String tagsLine){
        return Arrays.asList(tagsLine.split(", "));
    }

    private static void validatePublicationTag(String publicationTag)
            throws IncorrectPublicationTagException {
        // TODO Обсудить количество выделяемой памяти
        StringInputValidator stringInputValidator = new StringInputValidator(publicationTag);
        checkIfPublicationTagContainsWhitespaces(stringInputValidator);
        checkIfPublicationTagSize(stringInputValidator);
    }
    private static void checkIfPublicationTagSize
            (StringInputValidator stringInputValidator)
            throws IncorrectPublicationTagException {
        if(!stringInputValidator.checkSize(MIN_TAG_LENGTH, MAX_TAG_LENGTH)){
            throw new IncorrectPublicationTagException(
                    ExceptionMessage.publicationTagSizeExceptionMessage
            );
        }
    }

    private static void checkIfPublicationTagContainsWhitespaces
            (StringInputValidator stringInputValidator)
            throws IncorrectPublicationTagException {
        if(stringInputValidator.checkIfContainsWhitespaces()){
            throw new IncorrectPublicationTagException(
                    ExceptionMessage.publicationTagContainsWhitespacesExceptionMessage
            );
        }
    }
}
