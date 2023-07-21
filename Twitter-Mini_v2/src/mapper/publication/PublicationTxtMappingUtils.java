package mapper.publication;

import data.UserData;
import entity.user.User;

public abstract class PublicationTxtMappingUtils {
    public static User getPublicationAuthorByLogin(String authorLogin, UserData users){
        if(users.checkIfContainsLogin(authorLogin)){
            return users.getUserByLogin(authorLogin);
        }
        return null;
    }
}
