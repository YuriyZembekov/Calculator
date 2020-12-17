package com.calculator.verification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Класс проверяет "валидность" введённого выражения
public class SimpleVerifier implements Verifier {

    // Метод получает строку и массив символов которые могут встречаться в строке
    // Цифры и пробельные символы допускаются по-умолчанию

    @Override
    public boolean isValid(String expression, char[] validChars) {
        //Перед проверкой входного выражения удаляем из него все пробельные
        expression = expression.replaceAll("\\s", "");
        return isContainsOnlyValidSymbol(expression, validChars)
                && isNotContainsDoubleOperator(expression)
                && isStartsAndEndsWithDigits(expression);
    }

    // Метод проверяет наличие некорректных символов во входящей строке
    // Некорректные символы - любые символы, которых нет во входном массиве символов
    // Цифры допускаются по-умолчанию
    private boolean isContainsOnlyValidSymbol(String expression, char[] validChars){
        String patternString = "[^" + Pattern.quote(String.valueOf(validChars)) + "\\d]";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(expression);
        return !matcher.find();
    }

    // Метод проверяет наличие двух операторов подряд
    // На данный момент 17.12.2020 метод проверяет наличие рядом стоящих двух символов
    // которые не являются цифрами, для версии калькулятора без скобок это правильно
    // TODO
    private boolean isNotContainsDoubleOperator(String expression){
        Pattern pattern = Pattern.compile("\\D{2,}");
        Matcher matcher = pattern.matcher(expression);
        return !matcher.find();
    }

    private boolean isStartsAndEndsWithDigits(String expression){
        return expression.matches("\\d.*\\d");
    }
}
