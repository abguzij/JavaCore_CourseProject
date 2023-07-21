package entity.user;

import java.time.LocalDate;


public class Person extends User{
    private String name;
    private String surname;
    private LocalDate dateOfBirth;

    public Person(String login, String password, LocalDate registrationDate, UserType userType, String name, String surname, LocalDate dateOfBirth) {
        super(login, password, registrationDate, userType);
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(Integer id, String login, String password, LocalDate registrationDate, UserType userType, String name, String surname, LocalDate dateOfBirth) {
        super(id, login, password, registrationDate, userType);
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(String name, String surname, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }
    public Person() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String getUserShortInfo() {
        return String.format(
                "[%s] %s %s",
                this.getUserType(),
                this.getSurname(),
                this.getName()
        );
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] {\n" +
                "\tID: %s;\n" +
                "\tПолное имя: [%s] %s %s;\n" +
                "\tДата рождения: %s;\n" +
                "\tЛогин: %s;\n" +
                "}",
                this.getUserType().toString(),
                this.getId(),
                this.getUserType().toString(), this.surname, this.name,
                this.dateOfBirth.toString(),
                this.getLogin()
        );
    }
}
