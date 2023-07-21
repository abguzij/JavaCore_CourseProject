package mapper.publication.impl;

import data.UserData;
import entity.publication.Publication;
import entity.user.User;
import exceptions.ExceptionMessage;
import exceptions.IncorrectLoginException;
import mapper.TxtMappingUtils;
import mapper.publication.PublicationMapper;
import mapper.publication.PublicationTxtMappingUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PublicationMapperForTxt implements PublicationMapper {
    final static DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private static final Integer ID_FIELD_INDEX = 0;
    private static final Integer AUTHOR_LOGIN_FIELD_INDEX = 1;
    private static final Integer THEME_FIELD_INDEX = 2;
    private static final Integer MESSAGE_FIELD_INDEX = 3;
    private static final Integer TAGS_FIELD_INDEX = 4;
    private static final Integer CREATION_MOMENT_FIELD_INDEX = 5;
    @Override
    public String convertPublicationToFileLine(Publication publication) {
        StringBuilder builder = new StringBuilder();
        builder
                .append(TxtMappingUtils.wrapFieldInBrackets(publication.getId().toString()))
                .append(TxtMappingUtils.wrapFieldInBrackets(publication.getAuthor().getLogin()))
                .append(TxtMappingUtils.wrapFieldInBrackets(publication.getTheme()))
                .append(TxtMappingUtils.wrapFieldInBrackets(publication.getText()))
                .append(TxtMappingUtils.wrapFieldInBrackets(publication.tagsToString()))
                .append(TxtMappingUtils.wrapFieldInBrackets(
                        publication.getDateOfPublication().format(ISO_FORMATTER)
                ));
        return builder.toString();
    }

    @Override
    public Publication getOriginalPublicationFromFileLine(String fileLine, UserData users) throws IncorrectLoginException {
        List<String> fieldsStringRepresentation =
                TxtMappingUtils.extractFieldsStringRepresentationsFromRepresentationInFile(fileLine);

        User author = PublicationTxtMappingUtils.getPublicationAuthorByLogin(
                fieldsStringRepresentation.get(AUTHOR_LOGIN_FIELD_INDEX),
                users
        );

        if(!Objects.nonNull(author)){
            throw new IncorrectLoginException(ExceptionMessage.loginDoesntExistExceptionMessage);
        }
        return new Publication(
                Integer.valueOf(fieldsStringRepresentation.get(ID_FIELD_INDEX)),
                author,
                fieldsStringRepresentation.get(THEME_FIELD_INDEX),
                fieldsStringRepresentation.get(MESSAGE_FIELD_INDEX),
                Arrays.stream(
                        fieldsStringRepresentation.get(TAGS_FIELD_INDEX)
                                .split(","))
                        .collect(Collectors.toList()),
                LocalDateTime.parse(fieldsStringRepresentation.get(
                        CREATION_MOMENT_FIELD_INDEX),
                        ISO_FORMATTER
                )
        );
    }
}
