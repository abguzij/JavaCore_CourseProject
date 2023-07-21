package validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringInputValidator {
    private String inputString;
    private char[] inputStringCharacters;

    private boolean[] symbolTypeFlags = new boolean[4];
    private static final int LOWER_CASE_LETTERS_FLAG = 0;
    private static final int UPPER_CASE_LETTERS_FLAG = 1;
    private static final int NUMBERS_FLAG = 2;
    private static final int SYMBOLS_FLAG = 3;

    private static final String symbolsRegex = "[`~!@#$%^&*()_\\-+={}\\[\\]|:;\"'<>,.?/]";
    private static final Pattern specialSymbolsPattern = Pattern.compile(symbolsRegex);
    private Matcher specialSymbolsMatcher;

    public StringInputValidator(String inputString) {
        this.inputString = inputString;
        inputStringCharacters = inputString.toCharArray();
        specialSymbolsMatcher = specialSymbolsPattern.matcher(inputString);
    }

    public boolean checkIfNullOrEmpty(){
        return inputString == null || inputString.isEmpty();
    }

    public boolean checkIfContainsWhitespaces(){
        return inputString.contains(" ");
    }

    public boolean checkIfContainsOnlyLatinSymbols(){
        for (char symbol:
             inputStringCharacters) {
            if(!StringInputValidator.checkIfSymbolIsFromBasicLatinUnicodeBlock(symbol)){
                return false;
            }
        }
        return true;
    }
    private static boolean checkIfSymbolIsFromBasicLatinUnicodeBlock(char symbol){
        return Character.UnicodeBlock.of(symbol) == Character.UnicodeBlock.BASIC_LATIN;
    }

    public boolean checkIfContainsAllTypesOfSymbols(){
        for (char symbol:
                inputStringCharacters) {
            determineSymbolTypeAndRaiseFlag(symbol);
        }

        return symbolTypeFlags[LOWER_CASE_LETTERS_FLAG] &&
                symbolTypeFlags[UPPER_CASE_LETTERS_FLAG] &&
                symbolTypeFlags[NUMBERS_FLAG] &&
                symbolTypeFlags[SYMBOLS_FLAG];
    }
    private void determineSymbolTypeAndRaiseFlag(char symbol){
        if(Character.isLetter(symbol)){
            if(Character.isLowerCase(symbol)){
                symbolTypeFlags[LOWER_CASE_LETTERS_FLAG] = true;
            } else {
                symbolTypeFlags[UPPER_CASE_LETTERS_FLAG] = true;
            }
            return;
        }
        if (Character.isDigit(symbol)){
            symbolTypeFlags[NUMBERS_FLAG] = true;
            return;
        }
        if (specialSymbolsMatcher.find()) {
            symbolTypeFlags[SYMBOLS_FLAG] = true;
        }
    }

    public boolean checkIfContainsOnlyLetters(){
        for (char ch:
                inputStringCharacters) {
            if(!Character.isLetter(ch)){
                return false;
            }
        }
        return true;
    }

    public boolean checkIfContainsOnlyLatinLetters(){
        return checkIfContainsOnlyLetters() && checkIfContainsOnlyLatinSymbols();
    }

    public boolean checkSize(int minValue){
        return inputString.length() >= minValue;
    }
    public boolean checkMaxSize(int maxValue){
        return inputString.length() <= maxValue;
    }
    public boolean checkSize(int minValue, int maxValue){
        return checkSize(minValue) && checkMaxSize(maxValue);
    }
}
