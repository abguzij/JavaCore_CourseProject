package commands;

public abstract class ErrorInfo {
    private static void showErrorMessage(String errorMessageText){
        System.out.println("<<<<<<<< Ошибка! >>>>>>>>");
        System.out.println(errorMessageText);
        System.out.println("<<<<<<<< Ошибка! >>>>>>>>");
    }
    static void showIncorrectUserTypeError(){
        showErrorMessage("Некорректный тип пользователя! (0 - человек, 1 - организация)");
    }
    static void showIncorrectDateFormatError(){
        showErrorMessage("Некорректный формат даты! (Допустимый формат даты: гггг-мм-дд )");
    }

    static void showDateIsAfterError(){
        showErrorMessage("Введенная дата позже сегодняшней! Введите корректную дату");
    }

    static void showLoginDoesntExistError(){
        showErrorMessage("Пользователя с данным логином не существует!");
    }

    static void showNoUsersInSystemError(){
        showErrorMessage("В системе еще не зарегистрирован ни один пользователь");
    }

    static void showLoginAlreadyExistsError(){
        showErrorMessage("Введенный логин уже зарегистрирован в системе");
    }
}
