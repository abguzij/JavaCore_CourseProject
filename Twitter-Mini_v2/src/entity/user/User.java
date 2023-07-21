package entity.user;

import java.time.LocalDate;
import java.util.Objects;

public abstract class User {
    public static final Integer MIN_ID_VALUE = 0;
    private static Integer usersCounter = 0;
    private Integer id;
    private String login;
    private String password;
    private LocalDate registrationDate;
    private UserType userType;

    public User(Integer id, String login, String password, LocalDate registrationDate, UserType userType) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
        this.userType = userType;
    }

    public User(String login, String password, LocalDate registrationDate, UserType userType) {
        this.id = usersCounter;
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
        this.userType = userType;
    }
    public User(){
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public static void incrementUsersCounter(){
        usersCounter++;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public User compareByID (User user){
        if(this.id < user.id ||
                (this.id == 0 && user.id == 0)){
            return user;
        }
        return this;
    }

    public abstract String getUserShortInfo();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(registrationDate, user.registrationDate) && userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, registrationDate, userType);
    }
}
