package com.calculator.mathoperation;

import com.calculator.dto.DtoParsedExpression;

import java.math.BigDecimal;
import java.util.Map;

// Класс считает выражение записанное в обратной польской нотации
// Входные вданные в двух "Мапах" со сквозной нумерацией.
public class SimpleCalculate implements Calculate {
    @Override
    public BigDecimal calculateExpression(DtoParsedExpression expression) {
        Map<Integer, BigDecimal> digits = expression.getDigits();
        Map<Integer, Character> operators = expression.getOperators();
        int size = digits.size() + operators.size();
        int counter = 0;
        int firstDigitIndex = 0;
        int secondDigitIndex = 0;
        int operationIndex = 0;
        BigDecimal firstValue = null;
        BigDecimal secondValue = null;
        Character operation = null;

        while (digits.size() + operators.size() > 1) nextIteration:{
            // Ищется первая цифра, если здесь не цифра
            // изменяется начало поиска "+1"
            if (digits.get(counter) != null) {
                firstValue = digits.get(counter);
                firstDigitIndex = counter;
            } else {
                counter++;
                continue;
            }
            // ищется вторая цифра, если по следующему индексу
            // есть оператор, то весь поиск начинается сначала
            // если цифра есть - запоминаем её и её индекс.
            for (int i = counter + 1; i < size; i++) {
                if (operators.get(i) != null) {
                    counter++;
                    break nextIteration;
                } else if (digits.get(i) != null) {
                    secondValue = digits.get(i);
                    secondDigitIndex = i;
                    break;
                }
            }
            // ищется оператор после двух чисел в обратной польской записи
            // при нахожении производится расчёт, результат записывается
            // на ме
            for (int i = secondDigitIndex + 1; i < size; i++) {
                if (operators.get(i) == null && digits.get(i) == null) {
                    continue;
                }
                if (digits.get(i) != null) {
                    counter++;
                    break;
                }
                if (operators.get(i) != null) {
                    operation = operators.get(i);
                    operationIndex = i;
                    digits.put(firstDigitIndex,
                            calculateHelp(firstValue, secondValue, operation));
                    digits.remove(secondDigitIndex);
                    operators.remove(operationIndex);
                    counter = 0;
                    break;
                }
            }
        }
        return digits.get(0);
    }

    // Вспомогательный метод получает два BigDecimal и оператор в виде символа
    // Возвращает результат операции в виде BigDecimal
    private BigDecimal calculateHelp(BigDecimal a, BigDecimal b, char c) {
        BigDecimal calculateResult;
        switch (c) {
            case '+':
                calculateResult = a.add(b);
                break;
            case '-':
                calculateResult = a.subtract(b);
                break;
            case '*':
                calculateResult = a.multiply(b);
                break;
            case '/':
                if (b.compareTo(new BigDecimal(0)) == 0) {
                    throw new ArithmeticException("На ноль делить нельзя");
                }
                calculateResult = a.divide(b, BigDecimal.ROUND_HALF_UP);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный оператор");
        }
        return calculateResult;
    }
}
