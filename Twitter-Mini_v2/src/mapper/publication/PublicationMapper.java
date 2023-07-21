package mapper.publication;

import data.UserData;
import entity.publication.Publication;
import exceptions.IncorrectLoginException;

public interface PublicationMapper {
    String convertPublicationToFileLine(Publication publication);
    Publication getOriginalPublicationFromFileLine(String fileLine, UserData users) throws IncorrectLoginException;
}
