package com.calculator.mathoperation;

import com.calculator.dto.DtoParsedExpression;

import java.math.BigDecimal;

// Интерфейс для получения результата в виде BigDecimal
// Из передаваемого объекта DtoParsedExpression
// в котором содержится арифметическое выражение
// записаное в обратной польской нотации
public interface Calculate {
    BigDecimal calculateExpression(DtoParsedExpression expression);
}
