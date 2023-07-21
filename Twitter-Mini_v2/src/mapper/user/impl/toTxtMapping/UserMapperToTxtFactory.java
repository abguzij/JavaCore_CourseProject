package mapper.user.impl.toTxtMapping;

import entity.user.Organisation;
import entity.user.Person;
import entity.user.User;
import entity.user.UserType;
import mapper.TxtMappingUtils;
import mapper.user.UserTxtMappingUtils;

import java.util.List;

public class UserMapperToTxtFactory {
    public UserMapperToTxtFactory() {
    }

    public static String getFileContentLineByUserType(User user){

        if(user.getUserType().equals(UserType.PERSON)){
            return new PersonMapperForTxt().convertUserToFileLine((Person) user);
        } else {
            return new OrganisationMapperToTxt().convertUserToFileLine((Organisation) user);
        }
    }
    public static User getSpecificUserByUserType(String fileLine){
        List<String> fieldsStringRepresentations
                = TxtMappingUtils.extractFieldsStringRepresentationsFromRepresentationInFile(fileLine);
        UserType userType
                = UserTxtMappingUtils.getRepresentedUserType(
                        fieldsStringRepresentations.get(UserTxtMappingUtils.USER_TYPE_FIELD_INDEX)
        );
        if(userType.equals(UserType.PERSON)){
            return new PersonMapperForTxt().getOriginalUserFromFileLine(fieldsStringRepresentations);
        } else {
            return new OrganisationMapperToTxt().getOriginalUserFromFileLine(fieldsStringRepresentations);
        }
    }
}
