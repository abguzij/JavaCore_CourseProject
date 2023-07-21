package mapper.user.impl.toTxtMapping;

import entity.user.Organisation;
import entity.user.UserType;
import mapper.TxtMappingUtils;
import mapper.user.UserMapper;
import mapper.user.UserTxtMappingUtils;

import java.util.List;

public class OrganisationMapperToTxt implements UserMapper<Organisation> {
    private static final Integer NAME_FIELD_INDEX = 5;
    private static final Integer TYPE_OF_ACTIVITY_FIELD_INDEX = 6;
    private static final Integer FOUNDATION_DATE_FIELD_INDEX = 7;
    @Override
    public String convertUserToFileLine(Organisation user) {
        StringBuilder builder = new StringBuilder();
        builder.append(TxtMappingUtils.wrapFieldInBrackets(user.getId().toString()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getLogin()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getPassword()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getRegistrationDate().format(
                        UserTxtMappingUtils.formatter))
                )
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getUserType().toString()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getUserType().toString()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getName()))
                .append(TxtMappingUtils.createEmptyValueInBrackets())
                .append(TxtMappingUtils.createEmptyValueInBrackets())
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getTypeOfActivity()))
                .append(TxtMappingUtils.wrapFieldInBrackets(user.getDateOfFoundation().format(
                        UserTxtMappingUtils.formatter))
                );
        return builder.toString();
    }

    @Override
    public Organisation getOriginalUserFromFileLine(String fileLine) {
        return getOriginalUserFromFileLine(
                TxtMappingUtils.extractFieldsStringRepresentationsFromRepresentationInFile(fileLine)
        );
    }

    @Override
    public Organisation getOriginalUserFromFileLine(List<String> fieldsRepresentations) {
        return new Organisation(
                Integer.valueOf(fieldsRepresentations.get(UserTxtMappingUtils.ID_FIELD_INDEX)),
                fieldsRepresentations.get(UserTxtMappingUtils.LOGIN_FIELD_INDEX),
                fieldsRepresentations.get(UserTxtMappingUtils.PASSWORD_FIELD_INDEX),
                UserTxtMappingUtils.convertStrToLocalDate(
                        fieldsRepresentations.get(UserTxtMappingUtils.REGISTRATION_DATE_FIELD_INDEX)
                ),
                UserType.ORGANISATION,
                fieldsRepresentations.get(NAME_FIELD_INDEX),
                fieldsRepresentations.get(TYPE_OF_ACTIVITY_FIELD_INDEX),
                UserTxtMappingUtils.convertStrToLocalDate(
                        fieldsRepresentations.get(FOUNDATION_DATE_FIELD_INDEX)
                )
        );
    }
}
