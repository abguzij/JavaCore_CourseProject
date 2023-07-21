package mapper.user.impl.toTxtMapping;

import entity.user.Person;
import entity.user.UserType;
import mapper.TxtMappingUtils;
import mapper.user.UserMapper;
import mapper.user.UserTxtMappingUtils;

import java.util.List;

public class PersonMapperForTxt implements UserMapper<Person> {
    private static final Integer NAME_FIELD_INDEX = 5;
    private static final Integer SURNAME_FIELD_INDEX = 6;
    private static final Integer BIRTHDATE_FIELD_INDEX = 7;
    @Override
    public String convertUserToFileLine(Person user) {
        StringBuilder builder = new StringBuilder();
        builder.append(TxtMappingUtils.wrapFieldInBrackets(user.getId().toString()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getLogin()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getPassword()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getRegistrationDate().format(
                        UserTxtMappingUtils.formatter))
                )
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getUserType().toString()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getName()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getSurname()))
                .append(TxtMappingUtils.wrapFieldInBrackets(
                        user.getDateOfBirth().format(UserTxtMappingUtils.formatter))
                )
                .append(TxtMappingUtils.createEmptyValueInBrackets())
                .append(TxtMappingUtils.createEmptyValueInBrackets());

        return builder.toString();
    }

    @Override
    public Person getOriginalUserFromFileLine(String fileLine) {
        return getOriginalUserFromFileLine(
                TxtMappingUtils.extractFieldsStringRepresentationsFromRepresentationInFile(fileLine)
        );
    }

    @Override
    public Person getOriginalUserFromFileLine(List<String> fieldsRepresentations) {
        return new Person(
                Integer.valueOf(fieldsRepresentations.get(UserTxtMappingUtils.ID_FIELD_INDEX)),
                fieldsRepresentations.get(UserTxtMappingUtils.LOGIN_FIELD_INDEX),
                fieldsRepresentations.get(UserTxtMappingUtils.PASSWORD_FIELD_INDEX),
                UserTxtMappingUtils.convertStrToLocalDate(
                        fieldsRepresentations.get(UserTxtMappingUtils.REGISTRATION_DATE_FIELD_INDEX)
                ),
                UserType.PERSON,
                fieldsRepresentations.get(NAME_FIELD_INDEX),
                fieldsRepresentations.get(SURNAME_FIELD_INDEX),
                UserTxtMappingUtils.convertStrToLocalDate(
                        fieldsRepresentations.get(BIRTHDATE_FIELD_INDEX)
                )
        );
    }
}
