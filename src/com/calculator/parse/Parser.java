package com.calculator.parse;


import com.calculator.dto.DtoParsedExpression;

// Интерфейс для парсинга арифметического выражения в виде строки
// в объект DtoParsedExpression, содержащий это же арифметическое
// выражение записанное в обратной польской нотации
public interface Parser {
    DtoParsedExpression parse(String inputString);
}
