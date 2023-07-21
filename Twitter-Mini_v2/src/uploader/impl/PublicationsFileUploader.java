package uploader.impl;

import data.PublicationsData;
import data.UserData;
import entity.publication.Publication;
import exceptions.*;
import mapper.publication.PublicationMapper;
import uploader.FileUploader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PublicationsFileUploader extends FileUploader {
    private PublicationsData allPublications;
    private UserData allUsers;
    private PublicationMapper publicationMapper;

    public PublicationsFileUploader(PublicationsData allPublications, UserData allUsers, PublicationMapper publicationMapper) {
        this.allPublications = allPublications;
        this.allUsers = allUsers;
        this.publicationMapper = publicationMapper;
    }

    private void uploadPublications() throws FileException, DuplicatedPublicationsException, InvalidFieldException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.source))){
            List<String> publicationsAsStrings = bufferedReader.lines().collect(Collectors.toList());
            this.total = publicationsAsStrings.size();
            if(publicationsAsStrings.size() > 0){
                for (String publicationAsStr:
                        publicationsAsStrings) {
                    if(!publicationAsStr.isEmpty()){
                        Publication publication =
                                publicationMapper.getOriginalPublicationFromFileLine(
                                        publicationAsStr,
                                        allUsers
                                );
                        this.allPublications.addPublication(publication);
                        this.uploaded++;
                    }
                }
            }
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        } catch (IncorrectLoginException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        this.uploaded = 0;
        this.total = 0;
        System.out.println("Считывание публикаций...");
        try {
            this.uploadPublications();
            System.out.printf("Всего публикаций в файле: %d, из них были добавлены: %d.%n", this.total, this.uploaded);
        } catch (IncorrectLoginException | InvalidFieldException | DuplicatedPublicationsException | FileException ex) {
            System.out.println("Ошибка:");
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
