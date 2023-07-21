package repository.publication.impl;

import data.UserData;
import entity.publication.Publication;
import exceptions.DuplicatedPublicationsException;
import exceptions.FileException;
import exceptions.IncorrectLoginException;
import mapper.publication.PublicationMapper;
import repository.publication.PublicationsRepository;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PublicationsRepositoryImpl implements PublicationsRepository {
    private final String pathToSource;
    private final File sourceFile;
    private final Set<Publication> publications;
    private final PublicationMapper publicationMapper;
    private final UserData allUsers;

    public PublicationsRepositoryImpl(String pathToSource, PublicationMapper publicationMapper, UserData allUsers)
            throws FileException, LoginException  {
        this.allUsers = allUsers;
        this.publications = new HashSet<>();
        this.pathToSource = pathToSource;
        this.sourceFile = new File(this.pathToSource);
        if(!this.sourceFile.exists()){
            try {
                sourceFile.createNewFile();
            } catch (IOException ex) {
                throw new FileException("Ошибка при инициализации файла. Работа системы будет остановлена.");
            }
        }
        this.publicationMapper = publicationMapper;
        this.init();
    }
    private void init() throws FileException, LoginException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile))) {
            List<String> postsAsStrings = bufferedReader.lines().collect(Collectors.toList());
            if (postsAsStrings.size() > 0) {
                for (String postString : postsAsStrings) {
                    if (!postString.isEmpty()) {
                        Publication publication
                                = publicationMapper.getOriginalPublicationFromFileLine(postString, allUsers);
                        this.publications.add(publication);
                    }
                }
            }
        } catch (IOException ex) {
            throw new FileException("Проблемы с настрокой чтения/записи в файл. Работа системы будет прервана.");
        } catch (IncorrectLoginException e) {
            throw new LoginException("Ошибка при инициализации публикаций. Пользователь не найден.");
        }
    }

    @Override
    public Set<Publication> getAllPublications() {
        return this.publications;
    }

    @Override
    public void savePublication(Publication publication)
            throws DuplicatedPublicationsException, FileException {
        List<Publication> filtered = this.publications
                .stream()
                .filter(x -> x.getId().equals(publication.getId())).collect(Collectors.toList());
        if (!filtered.isEmpty()) {
            throw new DuplicatedPublicationsException("Такая публикация уже есть в системе.");
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sourceFile, true))) {
            String publicationString = this.sourceFile.length() == 0 ? "" : "\n";
            publicationString += publicationMapper.convertPublicationToFileLine(publication);
            bufferedWriter.append(publicationString);
            this.publications.add(publication);
        } catch (IOException ex) {
            throw new FileException("Проблемы с настрокой чтения/записи в файл. Работа системы будет прервана.");
        }
    }
}
