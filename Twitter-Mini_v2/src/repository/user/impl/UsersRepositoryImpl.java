package repository.user.impl;

import entity.user.User;
import exceptions.DuplicatedUserException;
import exceptions.FileException;
import mapper.user.impl.toTxtMapping.UserMapperToTxtFactory;
import repository.user.UsersRepository;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UsersRepositoryImpl implements UsersRepository {
    private final String pathToFile;
    private final Set<User> users;
    private final File sourceFile;

    public UsersRepositoryImpl(String pathToFile) throws FileException {
        this.pathToFile = pathToFile;
        this.users = new HashSet<>();
        this.sourceFile = new File(pathToFile);
        if(!sourceFile.exists()){
            try {
                sourceFile.createNewFile();
            } catch (IOException e) {
                throw new FileException("Ошибка при инициализации файла. Работа системы будет остановлена.");
            }
        }
        this.init();
    }

    private void init() throws FileException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile))) {
            List<String> usersAsStrings = bufferedReader.lines().collect(Collectors.toList());
            if (usersAsStrings.size() > 0) {
                for (String userString : usersAsStrings) {
                    if (!userString.isEmpty()) {
                        User user = UserMapperToTxtFactory.getSpecificUserByUserType(userString);
                        this.users.add(user);
                    }
                }
            }
        } catch (IOException ex) {
            throw new FileException("Проблемы с настрокой чтения/записи в файл. Работа системы будет прервана.");
        }
    }

    @Override
    public Set<User> getAllUsersFromRepository() {
        return this.users;
    }

    @Override
    public void saveUser(User user) throws DuplicatedUserException, FileException {
        List<User> filtered = this.users.stream().filter(x -> x.getId().equals(user.getId()) || x.getLogin().equals(user.getLogin())).collect(Collectors.toList());
        if (!filtered.isEmpty()) {
            throw new DuplicatedUserException("Такой пользователь уже есть в системе.");
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sourceFile, true))) {
            String userString = this.sourceFile.length() == 0 ? "" : "\n";
            userString += UserMapperToTxtFactory.getFileContentLineByUserType(user);
            bufferedWriter.append(userString);
            this.users.add(user);
        } catch (IOException ex) {
            throw new FileException("Проблемы с настрокой чтения/записи в файл. Работа системы будет прервана.");
        }
    }
}
