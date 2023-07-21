package data;

import entity.user.Organisation;
import entity.user.Person;
import entity.user.User;
import entity.user.UserType;
import exceptions.DuplicatedUserException;
import exceptions.FileException;
import exceptions.InvalidFieldException;
import exceptions.UserCredentialsException;
import repository.user.UsersRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class UserData {

    private static final String LOGIN_REGEX = "[a-zA-Z]{3,10}";
    private static final String PASSWORD_REGEX = "[a-zA-Z]{3,10}[0-9]+[`~!@#$%^&*()_\\-+={}\\[\\]|:;\"'<>,.?/]+";
    private Set<User> allUsers;
    private UsersRepository usersRepository;

    public UserData(UsersRepository usersRepository) {
        allUsers = new HashSet<>();
        this.usersRepository = usersRepository;
    }

    // TODO Дописать проверки
    public void addUser(User newUser) throws DuplicatedUserException, UserCredentialsException, InvalidFieldException{
        allUsers = usersRepository.getAllUsersFromRepository();
        User possibleDuplicate = allUsers
                .stream()
                .filter(x -> x.getLogin().equals(newUser.getLogin()) || x.getId().equals(newUser.getId()))
                .findFirst().orElse(null);
        if (!Objects.isNull(possibleDuplicate)) {
            throw new DuplicatedUserException("Пользователь с таким логином уже зарегистрирован.");
        }
        if (!newUser.getLogin().matches(LOGIN_REGEX) || newUser.getPassword().matches(PASSWORD_REGEX)) {
            throw new UserCredentialsException("Неверно задан логин или пароль пользователя.");
        }
        if (this.calculateOlderDate(newUser)) {
            throw new InvalidFieldException("Слишком поздняя дата.");
        }
        Set<User> userSet = usersRepository.getAllUsersFromRepository();
        newUser.setId(userSet.size());
        newUser.setRegistrationDate(LocalDate.now());
        try {
            usersRepository.saveUser(newUser);
        } catch (FileException | DuplicatedUserException e) {
            System.out.println("Ошибка:");
            System.out.println(e.getLocalizedMessage());
        }
        this.allUsers.add(newUser);
    }

    public boolean checkIfContainsLogin(String loginToCheck){
        allUsers = usersRepository.getAllUsersFromRepository();
        return this.allUsers.stream().anyMatch(x -> x.getLogin().equals(loginToCheck));
    }

    public List<String> printAllUsers(){
        allUsers = usersRepository.getAllUsersFromRepository();
        return this.allUsers
                .stream()
                .map(x -> x.toString())
                .collect(
                        Collectors.toCollection(LinkedList<String>::new)
                );
    }
    public User getUserByLogin(String login){
        allUsers = usersRepository.getAllUsersFromRepository();
        for (User user:
             this.allUsers) {
            if(user.getLogin().equals(login)){
                return user;
            }
        }
        return null;
    }
    public Integer getUsersCount(){
        allUsers = usersRepository.getAllUsersFromRepository();
        return this.allUsers.size();
    }

    private Boolean calculateOlderDate(User user) {
        LocalDate now = LocalDate.now();
        if (user.getUserType().equals(UserType.PERSON)) {
            Person person = (Person) user;
            return now.minusYears(10).isBefore(person.getDateOfBirth());
        } else {
            Organisation organisation = (Organisation) user;
            return now.minusMonths(1).isBefore(organisation.getDateOfFoundation());
        }
    }
}
