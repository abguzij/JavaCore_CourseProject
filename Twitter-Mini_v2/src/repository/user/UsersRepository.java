package repository.user;

import entity.user.User;
import exceptions.DuplicatedUserException;
import exceptions.FileException;

import java.util.Set;

public interface UsersRepository {
    Set<User> getAllUsersFromRepository();
    void saveUser(User user) throws DuplicatedUserException, FileException;
}
