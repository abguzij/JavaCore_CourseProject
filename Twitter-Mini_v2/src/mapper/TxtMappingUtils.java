package mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TxtMappingUtils {
    public static String wrapFieldInBrackets(String stringFieldValue) {
        return String.format("{%s}", stringFieldValue);
    }

    public static String createEmptyValueInBrackets(){
        return "{}";
    }

    public static String concatResultingTxtFileEntityRepresentation(List<String> fieldsStringRepresentation){
        return fieldsStringRepresentation.stream().collect(Collectors.joining());
    }

    public static List<String> extractFieldsStringRepresentationsFromRepresentationInFile(String input) {
        input = convertInputStringToSplittableFormat(input);
        return Stream.of(input.split("}\\{"))
                .filter(str -> Objects.nonNull(str) && !str.isEmpty())
                .collect(Collectors.toList());
    }

    private static String convertInputStringToSplittableFormat(String input){
        StringBuilder builder = new StringBuilder(input);

        if(builder.toString().contains("{") && builder.toString().contains("}")){
            int firstBracketIndex = builder.indexOf("{");
            int lastBracketIndex = builder.lastIndexOf("}");

            return input.substring(firstBracketIndex + 1, lastBracketIndex - 1);
        }

        return builder.toString();
    }
}
