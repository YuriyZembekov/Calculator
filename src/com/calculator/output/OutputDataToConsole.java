package com.calculator.output;

// Класс выводит полученные объект в виде строки в консоль
public class OutputDataToConsole implements Output {
    @Override
    public void outputData(Object object) {
        System.out.println(object.toString());
    }
}
