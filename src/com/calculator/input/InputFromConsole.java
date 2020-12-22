package com.calculator.input;

import java.util.Scanner;

// Класс читает выражение из консоли
public class InputFromConsole implements Input {
    @Override
    public String getExpression() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
