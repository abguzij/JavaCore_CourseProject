package mapper.user;

import entity.user.UserType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class UserTxtMappingUtils {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final Integer ID_FIELD_INDEX = 0;
    public static final Integer LOGIN_FIELD_INDEX = 1;
    public static final Integer PASSWORD_FIELD_INDEX = 2;
    public static final Integer REGISTRATION_DATE_FIELD_INDEX = 3;
    public static final Integer USER_TYPE_FIELD_INDEX = 4;

    public static UserType getRepresentedUserType(String strUserType) {
        if (strUserType.equals(UserType.PERSON.toString())) {
            return UserType.PERSON;
        } else {
            return UserType.ORGANISATION;
        }
    }

    public static LocalDate convertStrToLocalDate(String strDate){
        return LocalDate.parse(strDate, formatter);
    }
}
