package com.calculator.parse;

import com.calculator.dto.DtoParsedExpression;

import java.util.*;

public class ParseStringAsArithmeticExpression implements Parse {

    @Override
    public DtoParsedExpression parse(String inputString) {
        Map<Integer, Double> digits = new HashMap<>();
        Map<Integer, Character> operators = new HashMap<>();
        LinkedList<Character> stack = new LinkedList<>();

        //вспомогательные переменные
        char c;
        int counter = 0;

        //accumulator for digit
        StringBuilder digitAsString = new StringBuilder();

        for (int i = 0, j = inputString.length(); i < j; i++) {
            c = inputString.charAt(i);
            // если символ цифра или точка, пишем её в аккумулятор
            // иначе работаем со стеком
            if (Character.isDigit(c) || c == '.') {
                digitAsString.append(c);
            } else {
                while (stack.size()>0
                        && getPriority(stack.getLast()) >= getPriority(c)){
                    operators.put(counter++, stack.removeLast());
                }
                stack.add(c);
            }
            // если символ последний, или после него символ оператора
            // содержимое аккумулятора переводим в число и заносим его в Map'у
            // обнуляем аккумулятор
            if (i == j - 1 || (!Character.isDigit(inputString.charAt(i + 1)))
                    && inputString.charAt(i + 1) !='.') {
                digits.put(counter++, Double.parseDouble(digitAsString.toString()));
                digitAsString.delete(0, digitAsString.length());
            }
            // если выражение закончено и стек не пустой
            // "Выдавливаем содержимое стека в выходную строку
            if (i==j-1 && stack.size()>0){
                while (stack.size()>0){
                    operators.put(counter++, stack.removeLast());
                }
            }
        }
        return new DtoParsedExpression(digits, operators);
    }

    // Вспомогательный метод возвращает приоритет оператора, чем выше, тем ранее выполняется
    // операция, при неизвестном операторе бросает
    // IllegalArgumentException("Неизвестный оператор");
    private int getPriority(char c) {
        int priority;
        switch (c) {
            case '+':
            case '-':
                priority = 2;
                break;
            case '*':
            case '/':
                priority = 3;
                break;
            default:
                throw new IllegalArgumentException("Неизвестный оператор");
        }
        return priority;
    }
}
