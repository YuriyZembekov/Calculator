package com.calculator.verification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Класс проверяет "валидность" введённого выражения
public class ComplexVerifier implements Verifier {

    // Массивы символов которые могут встречаться в строке
    // Цифры допускаются по-умолчанию
    // при добавлении дополнительных операторов - они добавляются
    // в массив operators
    private final char[] notOperators = {'.', '(', ')'};
    private final char[] operators = {'+','-','*','/'};
    private String expression;

    // Метод получает строку и проверяет её "валидность
    @Override
    public boolean isValid(String expression) {
        //Заменяем конструкцию вида "(-4)" на "1" - убираем таким образом все отрицательные
        //числа чтобы не мешали при проверках.
        this.expression=expression.replaceAll("\\(-\\d+(\\.\\d+)?\\)", "1");
        return isContainsOnlyValidSymbol()
                && isNotContainsDoubleOperatorsWithoutHooks()
                && isStartsAndEndsWithDigitsOrHooksAndContainsOneOperator()
                && isEqualsHooksCount()
                && isNotContainsOperatorsBetweenHooks()
                && isNotContainsHooksWithoutOperators()
                && isNotContainsSequenceDigitHookDigit()
                && isNotContainsSequenceHookDigitHook();
    }

    // Метод проверяет наличие некорректных символов во входящей строке
    // Некорректные символы - любые символы, которых нет в массивах символов
    // notOperators и operators
    // Цифры допускаются по-умолчанию
    private boolean isContainsOnlyValidSymbol() {
        String validChars = String.valueOf(notOperators)+String.valueOf(operators);
        String patternString = "[^" + Pattern.quote(validChars) + "\\d]";
        return getInvertMatcherFindResult(patternString, expression);
    }

    // Данный метод метод удаляет все скобки и проверяет наличие двух операторов
    // подряд
    private boolean isNotContainsDoubleOperatorsWithoutHooks(){
        String expressionWithoutHooks = expression.replaceAll("\\(", "")
                .replaceAll("\\)","");
        String operatorsAsString = String.valueOf(operators);
        String patternString = "[" + Pattern.quote(operatorsAsString)  + "]{2,}";
        return getInvertMatcherFindResult(patternString, expressionWithoutHooks);
    }

    // Метод проверяет наличие цифр или скобок по краям выражения
    // и наличие хотябы одного оператора между ними
    private boolean isStartsAndEndsWithDigitsOrHooksAndContainsOneOperator() {
        String operatorsAsString = String.valueOf(operators);
        String regularExpression = "^\\(*-?\\d+.*[" + Pattern.quote(operatorsAsString)
                + "].*\\d+\\)*$";
        return expression.matches(regularExpression);
    }

    // Метод проверяет наличие операторов между скобками, например ")+)"
    private boolean isNotContainsOperatorsBetweenHooks(){
        String operatorsAsString = Pattern.quote( String.valueOf(operators) );
        return getInvertMatcherFindResult("\\)["+operatorsAsString+"]\\)")
                && getInvertMatcherFindResult("\\(["+operatorsAsString+"]\\)")
                && getInvertMatcherFindResult("\\(["+operatorsAsString+"]\\(");
    }

    // Метод проверяет наличие скобок вида "()" и ")("
    private boolean isNotContainsHooksWithoutOperators(){
        return getInvertMatcherFindResult("(\\(\\))|(\\)\\()");
    }

    // Метод проверяет отсутствие последовательности цифра-скобки-цифра
    private boolean isNotContainsSequenceDigitHookDigit(){
        return getInvertMatcherFindResult("\\d[(|)]+\\d");
    }

    // Метод проверяет отсутствие последовательности скобки-число-скобки
    private boolean isNotContainsSequenceHookDigitHook(){
        return getInvertMatcherFindResult("[(|)]+\\d+(\\.\\d+)?[(|)]+");
    }

    // метод проверят, что количество открывваемых скобок равно кол-ву закрываемых
    // а также их правильную последовательность
    private boolean isEqualsHooksCount() {
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

    //Метод возвращает инверсный результат поиска по регулярному выражению и строке
    private boolean getInvertMatcherFindResult(String patternString, String expression){
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(expression);
        return !matcher.find();
    }

    //Метод возвращает инверсный результат поиска по регулярному выражению и строке
    // в качестве строки используется исходная проверяемая строка
    private  boolean getInvertMatcherFindResult(String patternString){
        return getInvertMatcherFindResult(patternString, expression);
    }
}