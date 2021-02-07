package com.calculator.dto;

import java.math.BigDecimal;
import java.util.Map;

// Класс DTO необходим для передачи данных классом, реализующим интерфейс Parse
// в реализацию интерфейса Calculate
public class DtoParsedExpression {
    private final Map<Integer, BigDecimal> digits;
    private final Map<Integer, Character> operators;

    public DtoParsedExpression(Map<Integer, BigDecimal> digits, Map<Integer, Character> operators) {
        this.digits = digits;
        this.operators = operators;
    }

    public Map<Integer, BigDecimal> getDigits() {
        return digits;
    }

    public Map<Integer, Character> getOperators() {
        return operators;
    }
}
