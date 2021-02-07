package com.calculator.parse;

import com.calculator.dto.DtoParsedExpression;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// Класс распознаёт полученную строку, в которой содержится
// арифметическое выражение и записывает его в обратной польской
// нотации. Возвращает объект DtoParsedExpression.
// Предполагается что выражение не содержит ошибок.
// При ошибке бросает IllegalArgumentException.
public class ComplexParserStringAsArithmeticExpression implements Parser {

    private final Map<Integer, BigDecimal> digits = new HashMap<>();
    private final Map<Integer, Character> operators = new HashMap<>();
    private final LinkedList<Character> stack = new LinkedList<>();
    private int counter = 0;
    private boolean isNegativeNumber = false;

    @Override
    public DtoParsedExpression parse(String inputString) {
        //вспомогательная переменная
        char c;
        //accumulator for digit
        StringBuilder digitAsString = new StringBuilder();

        for (int i = 0, j = inputString.length(); i < j; i++) {
            c = inputString.charAt(i);
            // если символ цифра или точка, пишем её в аккумулятор
            // иначе работаем со стеком
            if (Character.isDigit(c) || c == '.') {
                digitAsString.append(c);
            } else {
                if (c == '(' && inputString.charAt(i+1)=='-'){
                    i++;
                    digitAsString.append('-');
                    isNegativeNumber=true;
                    continue;
                } else if (c == ')'&& isNegativeNumber){
                    isNegativeNumber=false;
                } else {
                    stackAdd(c);
                }
            }

            // если символ последний, или после него символ оператора
            // содержимое аккумулятора переводим в число и заносим его в Map'у
            // обнуляем аккумулятор
            if (( i == j - 1 || !Character.isDigit(inputString.charAt(i + 1))
                    && inputString.charAt(i + 1) != '.')
                    && digitAsString.length()>0) {
                digits.put(counter++, new BigDecimal(Double.parseDouble(digitAsString.toString()),
                        MathContext.DECIMAL32));
                digitAsString.delete(0, digitAsString.length());
            }
        }
        moveStackToExpressionAfterBaseParse();
        return new DtoParsedExpression(digits, operators);
    }

    // Метод принимает символ, проверяет его приоритет, если приоритет выше последнего символа в стеке
    // пишет его в стек, если меньше или равен - переносит символы из стека в Map operators
    // Отдельно обрабатываются скобки
    private void stackAdd(char ch){

        //a - если стек пуст, или символ - "открывающая скобка" то
        // символ из входной стpоки пеpеписывается в стек;
        if (stack.size()==0 || ch=='('){
            stack.add(ch);
        } else if(ch!=')') {
            //обрабатываем операторы
            while (stack.size() > 0
                    && getPriority(stack.getLast()) >= getPriority(ch)) {
                operators.put(counter++, stack.removeLast());
            }
            stack.add(ch);
        } else {
            //выталкиваем в Мар'у операторы до тех пор пока не встретится
            //открывающаяся скобка, после этого удаляем её
            while(stack.getLast()!='('){
                operators.put(counter++, stack.removeLast());
            }
            stack.removeLast();
        }
    }

    // Вспомогательный метод возвращает приоритет оператора, чем выше, тем ранее выполняется
    // операция, при неизвестном операторе бросает
    // IllegalArgumentException("Неизвестный оператор");
    private int getPriority(char c) {
        int priority;
        switch (c) {
            case '(':
                priority = 0;
                break;
            case ')':
                priority = 1;
                break;
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

    // если выражение закончено и стек не пустой
    // "Выдавливаем" содержимое стека в выходную строку
    private void moveStackToExpressionAfterBaseParse() {
        if (stack.size() > 0) {
            while (stack.size() > 0) {
                operators.put(counter++, stack.removeLast());
            }
        }
    }
}
