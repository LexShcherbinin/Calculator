package calculate;

import org.junit.jupiter.api.function.Executable;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Character.*;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class ExampleValidation {
    /**
     * Проверяет выражение на правильность записи
     */
    static void checkExample(final String expression) {

        List<Character> expressionCharList = expression
                .chars()
                .mapToObj(c -> (char) c)
                .collect(toList());

        List<Executable> executableList = new LinkedList<>();

        executableList.addAll(checkIncorrectSigns(expressionCharList));
        executableList.addAll(checkBracketAmount(expressionCharList));
        executableList.addAll(checkBracketOrder(expressionCharList));
        executableList.addAll(checkArgumentBracket(expressionCharList));
        executableList.addAll(checkExpressionInBracketIsCorrect(expressionCharList));
        executableList.addAll(checkSymbolBeforeFunction(expressionCharList));
        executableList.addAll(checkSymbolAfterFunction(expressionCharList));
        executableList.addAll(checkSeveralSignConsecutive(expressionCharList));
        executableList.addAll(checkFirstSymbol(expressionCharList));
        executableList.addAll(checkLastSymbol(expressionCharList));
        executableList.addAll(checkNoOnlyLetter(expressionCharList));

        assertAll(executableList);
    }

    /**
     * Проверяет наличие некорректных символов в выражении
     */
    private static List<Executable> checkIncorrectSigns(final List<Character> expressionCharList) {
        List<Character> signList = List.of('(', ')', '+', '-', '*', '/', '!', '^', '.', ' ', ',');

        return expressionCharList
                .stream()
                .map(symbol -> (Executable) () ->
                        assertTrue(isLetter(symbol) || isDigit(symbol) || signList.contains(symbol),
                        "Симол " + symbol + " является некорректным")
                )
                .collect(toList());
    }

    /**
     * Проверяет сопадение в количестве открывающий и закрыающих скобочек
     */
    private static List<Executable> checkBracketAmount(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();

        int openingBracket = (int) expressionCharList
                .stream()
                .filter(symbol -> symbol.equals('('))
                .count();

        int closingBracket = (int) expressionCharList
                .stream()
                .filter(symbol -> symbol.equals(')'))
                .count();

        executableList.add(() -> assertEquals(openingBracket, closingBracket, "Неверное количество скобочек"));

        return executableList;
    }

    /**
     * Проверяет наличие закрывающей скобочки перед открыающей
     */
    private static List<Executable> checkBracketOrder(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();
        int bracket = 0;

        for (int i = 0; i < expressionCharList.size(); i++) {
            int j = i;
            char symbol = expressionCharList.get(j);

            if (symbol == '(') {
                bracket++;

            } else if (symbol == ')') {
                bracket--;
            }

            if (bracket < 0) {
                executableList.add(() -> fail("Закрывающая скобочка стоит перед открывающей (позиция " + j + " )"));
                bracket = 0;
            }
        }

        return executableList;
    }

    /**
     * Проверяет наличие скобочки перед выражением аргумента функции
     */
    private static List<Executable> checkArgumentBracket(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();

        for (int i = 1; i < expressionCharList.size(); i++) {
            int j = i;

            executableList.add(() ->
                    assertFalse(isDigit(expressionCharList.get(j)) && isLetter(expressionCharList.get(j-1)),
                    "Отсутствует скобочка перед аргументом функции на позиции " + j)
            );
        }

        return executableList;
    }

    /**
     * Проверяет наличие в скобочках какого-либо выражения
     */
    private static List<Executable> checkExpressionInBracketIsCorrect(final List<Character> expressionCharList) {
        List<Character> signList = List.of('+', '-', '*', '/', '^', '.', '(');
        List<Executable> executableList = new LinkedList<>();

        for (int i = 1; i < expressionCharList.size(); i++) {
            int j = i;

            executableList.add(() ->
                    assertFalse(expressionCharList.get(j) == ')' && signList.contains(expressionCharList.get(j)),
                    "Отсутстует выражение перед скобочкой на позиции " + j)
            );
        }

        return executableList;
    }

    /**
     * Проверянт наличие перед функцией некорректных симолов
     */
    private static List<Executable> checkSymbolBeforeFunction(final List<Character> expressionCharList) {
        List<Character> signList = List.of(')', '.', '!');
        List<Executable> executableList = new LinkedList<>();

        for (int i = 1; i < expressionCharList.size(); i++) {
            char currentSymbol = expressionCharList.get(i);
            char prevSymbol = expressionCharList.get(i-1);

            executableList.add(() ->
                    assertFalse(isLetter(currentSymbol) && ((isDigit(prevSymbol) || signList.contains(prevSymbol))),
                    "Некорректный символ (" + prevSymbol + ") перед функцией")
            );
        }

        return executableList;
    }

    /**
     * Проверянт наличие после имени функции некорректных симолов
     */
    private static List<Executable> checkSymbolAfterFunction(final List<Character> expressionCharList) {
        List<Character> signList = List.of(')', '.', '!', '+', '-', '*', '/', '^');
        List<Executable> executableList = new LinkedList<>();

        for (int i = 0; i < expressionCharList.size()-1; i++) {
            char currentSymbol = expressionCharList.get(i);
            char nextSymbol = expressionCharList.get(i+1);

            executableList.add(() ->
                    assertFalse(isLetter(currentSymbol) && signList.contains(nextSymbol),
                    "Некорректный символ (" + nextSymbol + ") после имени функциии")
            );
        }

        return executableList;
    }

    /**
     * Проверяет наличие подряд стоящих знаков
     */
    private static List<Executable> checkSeveralSignConsecutive(final List<Character> expressionCharList) {
        List<Character> signList = List.of('+', '-', '*', '/', '^', '.');
        List<Executable> executableList = new LinkedList<>();

        for (int i = 0; i < expressionCharList.size()-1; i++) {
            int j = i;
            char currentSymbol = expressionCharList.get(j);
            char nextSymbol = expressionCharList.get(j+1);

            executableList.add(() ->
                    assertFalse(signList.contains(currentSymbol) && signList.contains(nextSymbol),
                    "Несколько знаков подряд начиная с позиции " + j)
            );
        }

        return executableList;
    }

    /**
     * Проверяет наличие некорректных символов в начале выражения
     */
    private static List<Executable> checkFirstSymbol(final List<Character> expressionCharList) {
        List<Character> signList = List.of('+', '*', '/', '!', '^', '.');
        List<Executable> executableList = new LinkedList<>();

        executableList.add(() ->
                assertFalse(signList.contains(expressionCharList.get(0)),
                "Некорректный первый символ выражения")
        );

        return executableList;
    }

    /**
     * Проверяет наличие некорректных символов в конце выражения
     */
    private static List<Executable> checkLastSymbol(final List<Character> expressionCharList) {
        List<Character> signList = List.of('+', '-', '*', '/', '^', '.');
        List<Executable> executableList = new LinkedList<>();

        executableList.add(() ->
                assertFalse(signList.contains(expressionCharList.get(expressionCharList.size()-1)),
                "Некорректный последний символ выражения")
        );

        return executableList;
    }

    /**
     * Проверяет наличие в выражении чисел
     */
    private static List<Executable> checkNoOnlyLetter(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();

        executableList.add(() ->
                assertFalse((int) expressionCharList.stream().filter(Character::isDigit).count() != 0,
                "В выражении отсутствуют числа")
        );

        return executableList;
    }

}