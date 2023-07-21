package commands;

import authorization.UserAuthorization;
import data.PublicationDataUtils;
import data.PublicationsData;
import data.UserData;
import exceptions.*;
import input.*;
import entity.publication.Publication;
import mapper.publication.PublicationMapper;
import mapper.publication.impl.PublicationMapperForTxt;
import registration.OrganizationRegistrationModule;
import registration.PersonRegistrationModule;
import entity.user.User;
import entity.user.UserType;
import repository.publication.PublicationsRepository;
import repository.publication.impl.PublicationsRepositoryImpl;
import repository.user.UsersRepository;
import repository.user.impl.UsersRepositoryImpl;
import uploader.FileUploader;
import uploader.impl.PublicationsFileUploader;
import uploader.impl.UsersFileUploader;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class ProgramStateAndOnCommandActionsManager {

    private static UserData allUsersData;
    private static PublicationsData allPublicationsData;
    private static PersonRegistrationModule personRegistrationModule;
    private static OrganizationRegistrationModule organizationRegistrationModule;
    private static UserAuthorization userAuthorization;
    private static PublicationDataUtils publicationDataUtils;
    private static ProgramStateAndOnCommandActionsManager instance = null;
    private static Map<CommandType, Map<String, Command>> commandsMap;
    private static UsersRepository usersRepository;
    private static PublicationMapper publicationMapper;
    private static PublicationsRepository publicationsRepository;
    private static FileUploader publicationsFileUploader;
    private static FileUploader usersFileUploader;

    private static LoginInput loginInput;

    private ProgramStateAndOnCommandActionsManager() {
        fillCommandsMap(createCommandsStream());
        try{
            usersRepository = new UsersRepositoryImpl("UsersData.txt");
        } catch (FileException e) {
            System.out.println(e.getMessage());
        }
        allUsersData = new UserData(usersRepository);
        publicationMapper = new PublicationMapperForTxt();
        try{
            publicationsRepository = new PublicationsRepositoryImpl(
                    "PublicationsData.txt",
                    publicationMapper,
                    allUsersData
            );
        } catch (FileException | LoginException e) {
            System.out.println(e.getMessage());
        }
        allPublicationsData = new PublicationsData(publicationsRepository);
        personRegistrationModule = new PersonRegistrationModule(allUsersData);
        organizationRegistrationModule = new OrganizationRegistrationModule(allUsersData);
        userAuthorization = UserAuthorization.getInstance(allUsersData);
        loginInput = LoginInput.getInstance(allUsersData);
        publicationDataUtils = PublicationDataUtils.getInstance(allPublicationsData);
        publicationsFileUploader = new PublicationsFileUploader(allPublicationsData, allUsersData, publicationMapper);
        usersFileUploader = new UsersFileUploader(allUsersData);
    }
    public static ProgramStateAndOnCommandActionsManager getInstance() {
        if(instance == null){
            instance = new ProgramStateAndOnCommandActionsManager();
        }
        return instance;
    }

    private Stream<Command> createCommandsStream(){
        return Stream.of(
                new Command(
                        "exit",
                        "Выход из программы.",
                        CommandType.GENERAL,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::exitProgram
                ),
                new Command(
                        "help",
                        "Вывод списка доступных команд и их описания",
                        CommandType.GENERAL,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::displayCommandsDescription
                ),
                new Command(
                        "register",
                        "Регистрация нового пользователя в системе.",
                        CommandType.FOR_NOT_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::newUserRegistration
                ),
                new Command(
                        "login",
                        "Вход в систему под определенным пользователем.",
                        CommandType.FOR_NOT_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::login
                ),
                new Command(
                        "logout",
                        "Выход из авторизации текущего пользователя.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::logout
                ),
                new Command(
                        "info",
                        "Вывод информации об авторизированном пользователе.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::currentUserInfo
                ),
                new Command(
                        "info_by_login",
                        "Вывод информации о пользователе по его логину.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::usersInfoByLogin
                ),
                new Command(
                        "info_all",
                        "Вывод информации о всех пользователях системы.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::allUsersInfo
                ),
                new Command(
                        "add_post",
                        "Добавить новую публикацию от текущего пользователя.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::addPost
                ),
                new Command(
                        "my_posts",
                        "Вывод всех публикаций текущего пользователя.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::printAllCurrentUserPublications
                ),
                new Command(
                        "all_posts",
                        "Вывод всех публикаций в системе.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::printAllPublications
                ),
                new Command(
                        "posts_by_tag",
                        "Вывод публикаций по тэгу.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::printAllUserPublicationsByTag
                ),
                new Command(
                        "posts_by_login",
                        "Вывод публикаций по логину пользователя.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::printAllUserPublicationsByLogin
                ),
                new Command(
                        "posts_by_user_type",
                        "Вывод публикаций по типу пользователя.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::printAllUserPublicationsByUserType
                ),
                new Command(
                        "read_posts",
                        "Считывает публикации из укзанного файла и добавляет их в систему",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::readPublicationsFromFile
                ),
                new Command(
                        "read_users",
                        "Считывает пользователей из указанного файла и в случае" +
                                " если этого пользователя нет в системе, регистрирует его.",
                        CommandType.FOR_AUTHORIZED_ONLY,
                        ProgramStateAndOnCommandActionsManager.OnCommandActions::readUsersFromFile
                )
        );
    }
    private void fillCommandsMap(Stream<Command> stream){
        Stream<Command> commandsStream = stream;

        commandsMap = commandsStream.collect(
                groupingBy(
                        Command::getCommandType,
                        groupingBy(
                                Command::getStrRepresentation,
                                mapping(command -> command, extractSingleCommandFromList())
                        )
                )
        );
    }
    private static <T extends Command> Collector<T, ?, T> extractSingleCommandFromList(){
        return Collectors.collectingAndThen(
            Collectors.toList(),
            list -> {
                return list.get(0);
            }
        );
    }

    private static Command getCommandFromMap(CommandType commandType, String strCommand){
            return commandsMap.get(commandType).get(strCommand);
    }

    void executeCommand(String strCommand) throws InvalidCommandException{
        if(commandsMap == null || commandsMap.isEmpty()){
            fillCommandsMap(createCommandsStream());
        }

        Command general = getCommandFromMap(CommandType.GENERAL, strCommand);

        if(general != null) {
            general.getOnCommandExecutable().execute();
        } else {
            Command command;
            if(ProgramState.checkIfNoOneIsNotAuthorized()){
                command = getCommandFromMap(CommandType.FOR_NOT_AUTHORIZED_ONLY, strCommand);
            } else {
                command = getCommandFromMap(CommandType.FOR_AUTHORIZED_ONLY, strCommand);
            }

            if(command != null){
                command.getOnCommandExecutable().execute();
            } else {
                throw new InvalidCommandException(
                        "Неправильно введена команда! Чтобы увидеть список доступных команд введите help"
                );
            }
        }
    }

    private static class Command {
        private final String strRepresentation;
        private final String commandDescription;
        private final CommandType commandType;
        private final ProgramOnCommandExecutable onCommandExecutable;

        public Command(String strRepresentation, String commandDescription, CommandType commandType, ProgramOnCommandExecutable onCommandExecutable) {
            this.strRepresentation = strRepresentation;
            this.commandDescription = commandDescription;
            this.commandType = commandType;
            this.onCommandExecutable = onCommandExecutable;
        }
        public String getStrRepresentation() {
            return strRepresentation;
        }

        public String getCommandDescription() {
            return commandDescription;
        }

        public CommandType getCommandType() {
            return commandType;
        }

        public ProgramOnCommandExecutable getOnCommandExecutable() {
            return onCommandExecutable;
        }

        @Override
        public String toString() {
            return String.format("%s -- %s", this.strRepresentation, this.commandDescription);
        }
    }
    private enum CommandType {
        GENERAL,
        FOR_AUTHORIZED_ONLY,
        FOR_NOT_AUTHORIZED_ONLY
    }
    abstract static class ProgramState{
        private static boolean programIsQuitedFlag = false;
        private static User currentAuthorizedUser = null;

        static boolean getProgramIsQuitedFlag(){
            return ProgramState.programIsQuitedFlag;
        }
        private static void quitProgram(){
            ProgramState.programIsQuitedFlag = true;
        }
        private static void logoutFromCurrentAuthorization(){
            currentAuthorizedUser = null;
        }
        private static void setCurrentAuthorizedUser(User currentAuthorizedUser){
            ProgramState.currentAuthorizedUser = currentAuthorizedUser;
        }
        static boolean checkIfNoOneIsNotAuthorized(){
            return ProgramState.currentAuthorizedUser == null;
        }

    }
    private abstract static class OnCommandActions {
        private static void displayCommandsDescription(){
            System.out.println("<<<<<<<< Список доступных команд >>>>>>>>");
            displayCommandsByCommandType(CommandType.GENERAL);
            if(ProgramState.checkIfNoOneIsNotAuthorized()){
                displayCommandsByCommandType(CommandType.FOR_NOT_AUTHORIZED_ONLY);
            } else {
                displayCommandsByCommandType(CommandType.FOR_AUTHORIZED_ONLY);
            }
            System.out.println("<<<<<<<< Конец списка доступных команд >>>>>>>>");
        }
        private static void exitProgram(){
            ProgramStateAndOnCommandActionsManager.ProgramState.quitProgram();
        }
        private static void displayCommandsByCommandType(CommandType commandType){
            Map<String, Command> stringCommandMap = commandsMap.get(commandType);
            if(stringCommandMap != null) {
                stringCommandMap.entrySet()
                        .stream().
                        forEach(x -> System.out.println(x.getValue()));
            }
        }
        private static void newUserRegistration() {
            System.out.println("<<<<<<<< Регистрация нового пользователя >>>>>>>>");

            UserType newUserType = UserTypeInput.scanUserType();

            User newUser;
            if(newUserType == UserType.PERSON){
                newUser = personRegistrationModule.createNewUser();
            } else {
                newUser = organizationRegistrationModule.createNewUser();
            }

            System.out.println("Новый пользователь добавлен!");
            System.out.println(newUser);
            try {
                allUsersData.addUser(newUser);
            } catch (DuplicatedUserException | UserCredentialsException | InvalidFieldException e) {
                System.out.println("Ошибка: ");
                System.out.println(e.getMessage());
            }


            System.out.println("<<<<<<<< Конец регистрации пользователя >>>>>>>>");
        }
        private static void login(){
            System.out.println("<<<<<<<< Вход в систему >>>>>>>>");
            ProgramState.setCurrentAuthorizedUser(UserAuthorization.authorizeUser());
            System.out.println(
                    "<<<<< Добро пожаловать, " + ProgramState.currentAuthorizedUser.getUserShortInfo() + "! >>>>>"
            );
        }
        private static void logout(){
            System.out.println("До свидания, " + ProgramState.currentAuthorizedUser.getUserShortInfo() + "!");
            ProgramStateAndOnCommandActionsManager.ProgramState.logoutFromCurrentAuthorization();
        }
        private static void currentUserInfo(){
            System.out.println("<<<<<<<< Информация о пользователе >>>>>>>>");
            System.out.println(ProgramState.currentAuthorizedUser);
            System.out.println("<<<<<<<< Конец информации >>>>>>>>");
        }
        private static void usersInfoByLogin(){
            User userByLogin = allUsersData.getUserByLogin(LoginInput.searchExistingLogin());
            System.out.println("<<<<<<<< Информация о пользователе >>>>>>>>");
            System.out.println(userByLogin);
            System.out.println("<<<<<<<< Конец информации >>>>>>>>");
        }
        private static void allUsersInfo(){
            System.out.println("<<<<<<<< Информация о всех пользователях системы >>>>>>>>");
            System.out.println(allUsersData.printAllUsers());
            System.out.println("<<<<<<<< Конец информации >>>>>>>>");
        }
        private static void addPost(){
            System.out.println("<<<<<<<< Создание новой публикации >>>>>>>>");
            try {
                Publication publication = PublicationDataUtils.createNewPublication(ProgramState.currentAuthorizedUser);
                allPublicationsData.addPublication(publication);
            } catch (DuplicatedPublicationsException | InvalidFieldException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Создана новая публикация: ");
            Publication publication = PublicationDataUtils.getLatestUserPublication(
                    ProgramState.currentAuthorizedUser
            );
            if(publication != null){
                System.out.println(publication);
            }
            System.out.println("<<<<<<<< Конец создания публикации >>>>>>>>");
        }
        private static void printAllCurrentUserPublications(){
            System.out.println("<<<<<<<< Мои публикации >>>>>>>>");
            List<Publication> publications =
                     PublicationDataUtils.getAllUserPublication(ProgramState.currentAuthorizedUser);
            if(publications != null){
                publications.stream().forEach(System.out::println);
            }
            System.out.println("<<<<<<<< Конец публикаций >>>>>>>>");
        }
        private static void printAllPublications(){
            System.out.println("<<<<<<<< Все публикации >>>>>>>>");
            List<String> publications =
                    PublicationDataUtils.printAllPublications();
            if(publications != null){
                publications.stream().forEach(System.out::println);
            }
            System.out.println("<<<<<<<< Конец публикаций >>>>>>>>");
        }
        private static void printAllUserPublicationsByTag(){
            String tag = PublicationTagsInput.scanSinglePublicationTag();
            System.out.println("<<<<<<<< Все публикации по тегу {" + tag + "} >>>>>>>>");

            List<Publication> publications = PublicationDataUtils.getPublicationsByTag(tag);
            if (publications != null) {
                publications.stream().forEach(System.out::println);
            }

            System.out.println("<<<<<<<< Конец публикаций по тегу {" + tag + "} >>>>>>>>");
        }
        private static void printAllUserPublicationsByLogin(){
            String login = LoginInput.searchExistingLogin();
            System.out.println("<<<<<<<< Все публикации по логину {" + login + "} >>>>>>>>");

            List<Publication> publications = PublicationDataUtils.getUserPublicationsByLogin(login);
            if (publications != null) {
                publications.stream().forEach(System.out::println);
            }

            System.out.println("<<<<<<<< Конец публикаций по логину {" + login + "} >>>>>>>>");
        }
        private static void printAllUserPublicationsByUserType(){
            UserType userType = UserTypeInput.scanUserType();
            System.out.println("<<<<<<<< Все публикации по типу пользователя {" + userType + "} >>>>>>>>");

            List<Publication> publications = PublicationDataUtils.getPublicationsByUserType(userType);
            if (publications != null) {
                publications.stream().forEach(System.out::println);
            }

            System.out.println("<<<<<<<< Конец публикаций по типу пользователя {" + userType + "} >>>>>>>>");
        }
        private static void readPublicationsFromFile(){
            System.out.println("Введите файл для чтения: ");
            String fileName = ConsoleInputUtils.scanToFirstWhitespace();

            File file = new File(fileName);
            if (!file.exists() || file.isDirectory()) {
                System.out.println("Файл не найден или является дирректорией.");
                return;
            }
            if (!file.getName().contains(".txt")) {
                System.out.println("Файл должен быть формата *.txt.");
                return;
            }
            if (file.length() == 0) {
                System.out.println("Файл не должен быть пустым.");
                return;
            }

            publicationsFileUploader.setSource(file);
            new Thread(publicationsFileUploader).start();
        }
        private static void readUsersFromFile(){
            System.out.print("Файл для чтения: ");
            String fileName = ConsoleInputUtils.scanToFirstWhitespace();

            File file = new File(fileName);
            if (!file.exists() || file.isDirectory()) {
                System.out.println("Файл не найден или является дирректорией.");
                return;
            }
            if (!file.getName().contains(".txt")) {
                System.out.println("Файл должен быть формата *.txt.");
                return;
            }
            if (file.length() == 0) {
                System.out.println("Файл не должен быть пустым.");
                return;
            }

            usersFileUploader.setSource(file);
            new Thread(usersFileUploader).start();
        }
    }
}
