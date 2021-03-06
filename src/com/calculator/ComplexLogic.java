package com.calculator;


import com.calculator.dto.DtoParsedExpression;
import com.calculator.input.Input;
import com.calculator.input.InputFromConsole;
import com.calculator.mathoperation.Calculate;
import com.calculator.mathoperation.SimpleCalculate;
import com.calculator.output.Output;
import com.calculator.output.OutputDataToConsole;
import com.calculator.parse.ComplexParserStringAsArithmeticExpression;
import com.calculator.parse.Parser;
import com.calculator.verification.ComplexVerifier;
import com.calculator.verification.Verifier;

import java.math.BigDecimal;

public class ComplexLogic {
    // Инициализация классов-реализаций
    private final Input input = new InputFromConsole();
    private final Output output = new OutputDataToConsole();
    private final Verifier verifier = new ComplexVerifier();
    private final Parser parser = new ComplexParserStringAsArithmeticExpression();
    private final Calculate calculate = new SimpleCalculate();

    public void start() {
        String inputData = input.getExpression();
        //Перед проверкой входного выражения удаляем из него все пробельные символы
        inputData = inputData.replaceAll("\\s", "");
        if (verifier.isValid(inputData)) {
            DtoParsedExpression dtoParsedExpression = parser.parse(inputData);
            BigDecimal resultExpression =
                    calculate.calculateExpression(dtoParsedExpression);
            output.outputData(resultExpression);
        } else {
            throw new IllegalArgumentException("Неверное выражение");
        }
    }
}
