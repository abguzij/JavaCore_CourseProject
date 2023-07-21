package mapper.user;

import entity.user.User;

import java.util.List;

public interface UserMapper <T extends User> {
    String convertUserToFileLine(T user);
    T getOriginalUserFromFileLine(String fileLine);
    T getOriginalUserFromFileLine(List<String> fieldsRepresentations);
}
