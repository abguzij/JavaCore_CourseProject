package data;

import exceptions.ExceptionMessage;
import exceptions.PublicationSearchException;
import input.PublicationTagsInput;
import input.PublicationTextInput;
import input.PublicationThemeInput;
import entity.publication.Publication;
import entity.user.User;
import entity.user.UserType;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.List;

public class PublicationDataUtils {
    private static PublicationDataUtils instance = null;
    private static PublicationsData allPublications;

    private PublicationDataUtils(PublicationsData allPublications){
        PublicationDataUtils.allPublications = allPublications;
    }
    public static PublicationDataUtils getInstance(PublicationsData allPublications){
        if(instance == null){
            instance = new PublicationDataUtils(allPublications);
        }
        return instance;
    }

    public static Publication createNewPublication(User author){
        return new Publication(
                author,
                PublicationThemeInput.scanPublicationTheme(),
                PublicationTextInput.scanPublicationText(),
                PublicationTagsInput.scanPublicationTags(),
                LocalDateTime.now()
        );
    }

    public static Publication getLatestUserPublication(User user)  {
        try {
            Publication publication = allPublications.getLatestByDatePublicationByLogin(user.getLogin());
            checkIfThereAreAnyPublicationsInSystem();
            checkIfContainsSuchLatestPublication(publication);
            return publication;
        } catch (PublicationSearchException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public static List<Publication> getAllUserPublication(User user)  {
        try {
            List<Publication> publications = allPublications.getPublicationsByLogin(user.getLogin());
            checkIfThereAreAnyPublicationsInSystem();
            checkIfUserWithSuchLoginHaveAnyPublications(publications);
            return publications;
        } catch (PublicationSearchException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public static List<String> printAllPublications()  {
        try {
            List<String> publications = allPublications.printAllPublications();
            checkIfThereAreAnyPublicationsInSystem();
            return publications;
        } catch (PublicationSearchException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public static  List<Publication> getPublicationsByTag(String tag){
        try {
            List<Publication> publications = allPublications.getPublicationsByTag(tag);
            checkIfThereAreAnyPublicationsInSystem();
            checkIfThereIsNoPublicationsWithSuchTag(publications);
            return publications;
        } catch (PublicationSearchException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public static List<Publication> getUserPublicationsByLogin(String login)  {
        try {
            List<Publication> publications = allPublications.getPublicationsByLogin(login);
            checkIfThereAreAnyPublicationsInSystem();
            checkIfUserWithSuchLoginHaveAnyPublications(publications);
            return publications;
        } catch (PublicationSearchException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public static List<Publication> getPublicationsByUserType(UserType userType)  {
        try {
            List<Publication> publications = allPublications.getPublicationsByUserType(userType);
            checkIfThereAreAnyPublicationsInSystem();
            checkIfThereIsNoPublicationsWithSuchUserType(publications);
            return publications;
        } catch (PublicationSearchException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }


    private static void checkIfThereAreAnyPublicationsInSystem() throws PublicationSearchException {
        if(allPublications.getPublicationsNumber() == 0){
            throw new PublicationSearchException(ExceptionMessage.noPublicationsInSystemExceptionMessage);
        }
    }
    private static void checkIfContainsSuchLatestPublication(Publication publication) throws PublicationSearchException {
        if(publication.equals(new Publication())){
            throw new PublicationSearchException(ExceptionMessage.noPublicationsFromSuchUserExceptionMessage);
        }
    }
    private static void checkIfUserWithSuchLoginHaveAnyPublications(List<Publication> publications)
            throws PublicationSearchException {
        if(publications.isEmpty()) {
            throw new PublicationSearchException(ExceptionMessage.noPublicationsFromSuchUserExceptionMessage);
        }
    }
    private static void checkIfThereIsNoPublicationsWithSuchTag(List<Publication> publications)
            throws PublicationSearchException {
        if(publications.isEmpty()){
            throw new PublicationSearchException(ExceptionMessage.noPublicationsWithSuchTagExceptionMessage);
        }
    }
    private static void checkIfThereIsNoPublicationsWithSuchUserType(List<Publication> publications)
            throws PublicationSearchException {
        if(publications.isEmpty()){
            throw new PublicationSearchException(ExceptionMessage.noPublicationsWithSuchUserTypeExceptionMessage);
        }
    }
}
