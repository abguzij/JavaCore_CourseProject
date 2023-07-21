package data;

import entity.publication.Publication;
import entity.user.UserType;
import exceptions.DuplicatedPublicationsException;
import exceptions.FileException;
import exceptions.InvalidFieldException;
import repository.publication.PublicationsRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class PublicationsData {

    private Set<Publication> allPublications;
    private PublicationsRepository publicationsRepository;

    public PublicationsData(PublicationsRepository publicationsRepository) {
        allPublications = new HashSet<>();
        this.publicationsRepository = publicationsRepository;
    }

    public void addPublication(Publication publication)
            throws DuplicatedPublicationsException, InvalidFieldException {
        allPublications = publicationsRepository.getAllPublications();
        Publication possibleDuplicate = allPublications.stream().filter(x -> x.getId().equals(publication.getId())).findFirst().orElse(null);
        if (!Objects.isNull(possibleDuplicate)) {
            throw new DuplicatedPublicationsException("Такая публикация уже имеется.");
        }
        if (publication.getTheme().length() < 5 || publication.getText().length() > 255) {
            throw new InvalidFieldException("Неверно указаны поля публикации.");
        }
        for (String tag : publication.getTags()) {
            if (tag.length() < 3 || tag.length() > 15) {
                throw new InvalidFieldException("Неверно указаны теги публикации.");
            }
        }
        publication.setId(allPublications.size());
        publication.setDateOfPublication(LocalDateTime.now());
        try {
            publicationsRepository.savePublication(publication);
        } catch (FileException | DuplicatedPublicationsException e) {
            System.out.println("Ошибка:");
            System.out.println(e.getLocalizedMessage());
        }
    }

    public Publication getLatestByIdPublicationByLogin(String login){
          return getPublicationsByLogin(login)
                .stream()
                .reduce(new Publication(), (partial, next) -> partial.compareById(next));
    }

    public Publication getLatestByDatePublicationByLogin(String login){
        return getPublicationsByLogin(login)
                .stream()
                .reduce(new Publication(), (partial, next) -> partial.compareByDate(next));
    }

    public List<Publication> getPublicationsByLogin(String login){
        allPublications = publicationsRepository.getAllPublications();
        return this.allPublications
                .stream()
                .filter(x -> x.getAuthor().getLogin().equals(login))
                .collect(Collectors.toCollection(ArrayList<Publication>::new));
    }

    public List<String> printAllPublications(){
        allPublications = publicationsRepository.getAllPublications();
        return this.allPublications
                .stream()
                .map(x->x.toString())
                .collect(Collectors.toCollection(LinkedList<String>::new));
    }

    public List<Publication> getPublicationsByTag(String tag) {
        allPublications = publicationsRepository.getAllPublications();
        return this.allPublications
                .stream()
                .filter(x -> x.getTags().contains(tag))
                .collect(Collectors.toCollection(LinkedList<Publication>::new));
    }

    public List<Publication> getPublicationsByUserType(UserType userType) {
        allPublications = publicationsRepository.getAllPublications();
        return this.allPublications
                .stream()
                .filter(x -> x.getAuthor().getUserType() == userType)
                .collect(Collectors.toCollection(LinkedList<Publication>::new));
    }

    public Integer getPublicationsNumber(){
        allPublications = publicationsRepository.getAllPublications();
        return this.allPublications.size();
    }
}
