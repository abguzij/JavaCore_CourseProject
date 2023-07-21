package input;

import exceptions.UserTypeInputException;
import entity.user.UserType;

public abstract class UserTypeInput {
    public static UserType scanUserType(){
        while (true) {
            try {
                String strUserType = showUserTypeInputDialog();
                validateUserTypeInput(strUserType);

                if(strUserType.equals("0")){
                    return UserType.PERSON;
                }
                return UserType.ORGANISATION;
            } catch (UserTypeInputException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private static void validateUserTypeInput(String strUserType) throws UserTypeInputException {
        if(!(strUserType.equals("0") || strUserType.equals("1"))){
            throw new UserTypeInputException();
        }
    }
    private static String showUserTypeInputDialog(){
        System.out.println(InfoMessage.userTypeRules);
        System.out.print("Ведите тип пользователя (0 или 1): ");
        return ConsoleInputUtils.scanToFirstWhitespace();
    }
}
