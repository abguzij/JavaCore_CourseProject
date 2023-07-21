package entity.user;

import java.time.LocalDate;

public class Organisation extends User {
    private String name;
    private String typeOfActivity;
    private LocalDate dateOfFoundation;

    public Organisation(Integer id, String login, String password, LocalDate registrationDate, UserType userType, String name, String typeOfActivity, LocalDate dateOfFoundation) {
        super(id, login, password, registrationDate, userType);
        this.name = name;
        this.typeOfActivity = typeOfActivity;
        this.dateOfFoundation = dateOfFoundation;
    }

    public Organisation(String login, String password, LocalDate registrationDate, UserType userType,
                        String name, String typeOfActivity, LocalDate dateOfFoundation) {
        super(login, password, registrationDate, userType);
        this.name = name;
        this.typeOfActivity = typeOfActivity;
        this.dateOfFoundation = dateOfFoundation;
    }

    public Organisation(String name, String typeOfActivity, LocalDate dateOfFoundation) {
        this.name = name;
        this.typeOfActivity = typeOfActivity;
        this.dateOfFoundation = dateOfFoundation;
    }

    public Organisation() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfActivity() {
        return typeOfActivity;
    }

    public void setTypeOfActivity(String typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
    }

    public LocalDate getDateOfFoundation() {
        return dateOfFoundation;
    }

    public void setDateOfFoundation(LocalDate dateOfFoundation) {
        this.dateOfFoundation = dateOfFoundation;
    }

    @Override
    public String getUserShortInfo() {
        return String.format(
                "[%s] %s",
                this.getUserType(),
                this.getName()
        );
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] {\n" +
                        "\tID: %s;\n" +
                        "\tНаименование: [%s] %s;\n" +
                        "\tДата основания: %s;\n" +
                        "\tРод занятий: %s;\n" +
                        "\tЛогин: %s;\n" +
                "}",
                this.getUserType().toString(),
                this.getId(),
                this.getUserType().toString(), this.name,
                this.dateOfFoundation.toString(),
                this.typeOfActivity,
                this.getLogin()
        );
    }
}
