package com.calculator.verification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Класс проверяет "валидность" введённого выражения
public class SimpleVerifier implements Verifier {

    // Массив символов которые могут встречаться в строке
    // Цифры допускаются по-умолчанию
    private final char[] validChars = {'.', '+', '-', '*', '/'};

    // Метод получает строку и проверяет её "валидность
    @Override
    public boolean isValid(String expression) {
        return isContainsOnlyValidSymbol(expression)
                && isNotContainsDoubleOperator(expression)
                && isStartsAndEndsWithDigitsAndContainsOneOperator(expression);
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
    // На данный момент 17.12.2020 метод проверяет наличие рядом стоящих двух символов
    // которые не являются цифрами, для версии калькулятора без скобок - это достаточная
    // проверка на "валидность"
    private boolean isNotContainsDoubleOperator(String expression) {
        Pattern pattern = Pattern.compile("\\D{2,}");
        Matcher matcher = pattern.matcher(expression);
        return !matcher.find();
    }

    // Метод проверяет наличие цифр по краям выражения и наличие хотябы одного оператора
    // между ними
    private boolean isStartsAndEndsWithDigitsAndContainsOneOperator(String expression) {
        return expression.matches("^\\d+.*[^.\\d].*\\d+$");
    }
}
