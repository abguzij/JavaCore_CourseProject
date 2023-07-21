package uploader.impl;

import data.UserData;
import entity.user.User;
import exceptions.DuplicatedUserException;
import exceptions.FileException;
import exceptions.InvalidFieldException;
import exceptions.UserCredentialsException;
import mapper.user.impl.toTxtMapping.UserMapperToTxtFactory;
import uploader.FileUploader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UsersFileUploader extends FileUploader {
    private UserData allUsers;

    public UsersFileUploader(UserData allUsers) {
        this.allUsers = allUsers;
    }

    public UsersFileUploader(File source, UserData allUsers) {
        super(source);
        this.uploaded = 0;
        this.allUsers = allUsers;
    }

    private void uploadUsers() throws FileException, DuplicatedUserException, InvalidFieldException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.source))){
            List<String> usersAsStrings = bufferedReader.lines().collect(Collectors.toList());
            this.total = usersAsStrings.size();
            if(usersAsStrings.size() > 0){
                for (String userAsStr:
                     usersAsStrings) {
                    if(!userAsStr.isEmpty()){
                        User user = UserMapperToTxtFactory.getSpecificUserByUserType(userAsStr);
                        try{
                            this.allUsers.addUser(user);
                            this.uploaded++;
                        } catch (UserCredentialsException e) {
                            System.out.println(
                                    String.format("Пользователь {%s} не добавлен из-за ошибки: ", user.getLogin())
                            );
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    @Override
    public void run() {
        System.out.println("Считывание пользователей...");
        try {
            this.uploadUsers();
            System.out.printf("Всего пользователей в файле: %d, из них были добавлены: %d.%n", this.total, this.uploaded);
        } catch (DuplicatedUserException | InvalidFieldException | FileException ex) {
            System.out.println("Ошибка:");
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
