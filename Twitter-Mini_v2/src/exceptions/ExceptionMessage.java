package exceptions;

public abstract class ExceptionMessage {
    public static final String incorrectPasswordSizeExceptionMessage = "Пароль должен содержать от 5 символов!";
    public static final String passwordContainsWhitespacesExceptionMessage = "Пароль не может содержать пробелов!";
    public static final String invalidCharacterInPasswordExceptionMessage =
            "Пароль не может содержать символов кроме латинских букв, цифр и спецсимволов!";
    public static final String passwordIsEmptyExceptionMessage =
            "Пароль не может быть пустым!";
    public static final String passwordContainsLessCharacterTypesThenExpectedExceptionMessage =
            "Пароль должен содеражать в себе латинские буквы разного регистра," +
                    " хотя бы 1 цифру и хотя бы 1 спецсимвол!";
    public static final String incorrectLoginSizeExceptionMessage = "Логин должен содержать от 3 до 10 символов!";
    public static final String loginIsEmptyExceptionMessage = "Логин не может быть пустым!";
    public static final String loginAlreadyExistsExceptionMessage = "Данный логин уже существует в системе!";
    public static final String loginContainsInvalidCharactersExceptionMessage =
            "Логин может содержать только латинские буквы!";
    public static final String loginContainsWhitespaceExceptionMessage = "Логин не может содержать пробелов!";
    public static final String loginDoesntExistExceptionMessage = "Такого логина не существует в системе!";
    public static final String incorrectDateFormatInputExceptionMessage =
            "Неверный формат даты! Дата должна быть введена в формате (гггг-мм-дд)";
    public static final String dateIsAfterCurrentDateExceptionMessage =
            "Дата не может быть позже текущей даты!";
    public static final String userShouldBeOlderExceptionMessage =
            "Пользователь должен быть старше 10 лет!";
    public static final String foundationDateExceptionMessage =
            "Дата основания компании не может быть позже чем (текущая дата - 1 месяц) !";
    public static final String userNameIsEmptyExceptionMessage =
            "Имя пользователя не может быть пустым!";
    public static final String userSurnameIsEmptyExceptionMessage =
            "Фамилия пользователя не может быть пустой!";
    public static final String organizationNameIsEmptyExceptionMessage =
            "Наименование организации не может быть пустым!";
    public static final String organizationTypeOfActivityIsEmptyExceptionMessage =
            "Род занятости организации не может быть пустым!";
    public static final String publicationThemeIsEmptyExceptionMessage = "Тема публикации не может быть пустой!";
    public static final String publicationThemeContainsLessSymbolsThenRequiredExceptionMessage =
            "Тема публикации должна содержать не менее 5 символов!";
    public static final String publicationTextIsEmptyExceptionMessage = "Текст публикации не может быть пустым!";
    public static final String publicationTextContainsMoreSymbolsThenRequiredExceptionMessage =
            "Текст публикации должен содержать не более 255 символов!";
    public static final String publicationTagContainsWhitespacesExceptionMessage =
            "Тэг публикации не может содержать пробелов!";
    public static final String publicationTagSizeExceptionMessage =
            "Тэг может содержать от 3 до 15 символов!";
    public static final String noPublicationsFromSuchUserExceptionMessage =
            "Публикаций от данного пользователя не существует в системе!";
    public static final String noPublicationsInSystemExceptionMessage =
            "В системе еще не было добавлено ни одной публикации!";
    public static final String noPublicationsWithSuchTagExceptionMessage =
            "Публикаций с таким тегом не существует в системе!";
    public static final String noPublicationsWithSuchUserTypeExceptionMessage =
            "Публикаций с таким типом пользователя не существует в системе!";
}
