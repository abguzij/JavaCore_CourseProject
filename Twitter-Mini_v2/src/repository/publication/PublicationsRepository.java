package repository.publication;

import entity.publication.Publication;
import exceptions.DuplicatedPublicationsException;
import exceptions.FileException;

import java.util.Set;

public interface PublicationsRepository {
    Set<Publication> getAllPublications();
    void savePublication(Publication publication) throws DuplicatedPublicationsException, FileException;
}
