package com.calculator.verification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Класс проверяет "валидность" введённого выражения
public class ComplexVerifier implements Verifier {

    // Массив символов которые могут встречаться в строке
    // Цифры допускаются по-умолчанию
    // при добавлении дополнительных операторов - они добавляются в правую
    // часть массива.
    // при добавлении символов "не операторов" они добавляются в левую
    // часть массива и необходимо изменить startIndexOperators
    private final char[] validChars = {'.', '(', ')', '+', '-', '*', '/'};
    private final int startIndexOperators = 3;

    // Метод получает строку и проверяет её "валидность
    @Override
    public boolean isValid(String expression) {
        return isContainsOnlyValidSymbol(expression)
                && isNotContainsDoubleOperator(expression)
                && isStartsAndEndsWithDigitsOrHooksAndContainsOneOperator(expression)
                && isEqualsHooksCount(expression);
    }

    // Метод проверяет наличие некорректных символов во входящей строке
    // Некорректные символы - любые символы, которых нет во входном массиве символов
    // Цифры допускаются по-умолчанию
    private boolean isContainsOnlyValidSymbol(String expression) {
        String patternString = "[^" + Pattern.quote(String.valueOf(validChars)) + "\\d]";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(expression);
        return !matcher.find();
    }

    // Метод проверяет наличие двух операторов подряд
    private boolean isNotContainsDoubleOperator(String expression) {
        String operators = String.copyValueOf(validChars,
                startIndexOperators, validChars.length-startIndexOperators);
        String patternString = "[" + Pattern.quote(operators)  + "]{2,}";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(expression);
        return !matcher.find();
    }

    // Метод проверяет наличие цифр или скобок по краям выражения
    // и наличие хотябы одного оператора между ними
    private boolean isStartsAndEndsWithDigitsOrHooksAndContainsOneOperator(String expression) {
        return expression.matches("^(\\(*-?|\\d+).*[^.\\d].*(\\d|\\))+$");
    }

    // метод проверят, что количество открывваемых скобок равно кол-ву закрываемых
    // а также их правильную последовательность
    private boolean isEqualsHooksCount(String expression) {
        int hooksCount = 0;
        char symbolInString;
        for (int i = 0, j = expression.length(); i < j; i++) {
            symbolInString = expression.charAt(i);
            hooksCount += (symbolInString == '(') ? 1 : 0;
            hooksCount -= (symbolInString == ')') ? 1 : 0;
            if (hooksCount < 0) {
                return false;
            }
        }
        return hooksCount == 0;
    }
}
