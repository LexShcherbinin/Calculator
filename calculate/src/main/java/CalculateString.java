import java.util.LinkedList;
import java.util.List;

public class CalculateString {
    public static double calculateString(final String expression) {
        ExampleValidation check = new ExampleValidation();

        if (check.checkExpression(expression))
            return calculate(expression);

        else
            throw new StringException("Какая-то неведомая ошибка");
    }

    static double calculate(final String expression) {
        List<Double> numberList = getNumberList(expression);
        List<Character> signList = getSignList(expression);

        return CalculateExampleElement.calculateExpressionValue(numberList, signList);
    }

//    private static ExampleElement getNumberExampleElement(final String subExpression) {
//        int lastNumberIndex = 0;
//        String number;
//        ExampleElement exampleElement = new ExampleElement();
//
//        while (lastNumberIndex < subExpression.length() && SymbolValidation.getSymbolType(subExpression.charAt(lastNumberIndex)) == SymbolType.DIGIT) {
//            lastNumberIndex++;
//        }
//
//        number = subExpression.substring(0, lastNumberIndex);
//        exampleElement.setNumber(Double.parseDouble(number));
//        exampleElement.setLength(number.length());
//
//        return exampleElement;
//    }
//
//    private static ExampleElement getFunctionExampleElement(final String subExpression) {
//        ExampleElement exampleElement = new ExampleElement();
//
//        int lastFunctionIndex = getClosingBracketIndex(subExpression);
//        String exampleFunction = subExpression.substring(0, lastFunctionIndex+1);
//        double functionValue = CalculateFunction.getFunctionValue(exampleFunction);
//
//        exampleElement.setNumber(functionValue);
//        exampleElement.setLength(exampleFunction.length());
//
//        return exampleElement;
//    }
//
//    private static ExampleElement getSignExampleElement(final Character signChar) {
//        ExampleElement exampleElement = new ExampleElement();
//
//        exampleElement.setSign(signChar);
//        exampleElement.setLength(1);
//
//        return exampleElement;
//    }
//
//    private static ExampleElement getBracketExampleElement(final String subExpression) {
//        ExampleElement exampleElement = new ExampleElement();
//
//        int lastFunctionIndex = getClosingBracketIndex(subExpression);
//        String exampleBracket = subExpression.substring(1, lastFunctionIndex);
//        double bracketValue = calculate(exampleBracket);
//
//        exampleElement.setNumber(bracketValue);
//        exampleElement.setLength(exampleBracket.length() + 2);
//
//        return exampleElement;
//    }

    private static List<Character> getSignList(final String subExpression) {
        List<Character> signList = new LinkedList<>();
        int i = 0;
        char symbol;
        signList.add(null);

        while (i < subExpression.length()) {
            symbol = subExpression.charAt(i);

            SymbolType symbolType = SymbolValidation.getSymbolType(symbol);

            if (symbolType == SymbolType.SIGN) {
                signList.add(symbol);

            } else if (symbolType == SymbolType.BRACKET) {
                i += getClosingBracketIndex(subExpression.substring(i));
            }

            i++;
        }
        return signList;
    }

    private static List<Double> getNumberList(final String subExpression) {
        List<Double> numberList = new LinkedList<>();
        int i = 0;
        ExampleElement exampleElement = new ExampleElement();

        while (i < subExpression.length()) {
            char symbol = subExpression.charAt(i);
            SymbolType symbolType = SymbolValidation.getSymbolType(symbol);

            if (symbolType == SymbolType.DIGIT) {
                exampleElement = ExampleElement.getNumberExampleElement(subExpression.substring(i));

            } else if (symbolType == SymbolType.LETTER) {
                exampleElement = ExampleElement.getFunctionExampleElement(subExpression.substring(i));

            } else if (symbolType == SymbolType.BRACKET) {
                exampleElement = ExampleElement.getBracketExampleElement(subExpression.substring(i));

            } else if (symbolType == SymbolType.SIGN) {
                if (i == 0) {
                    numberList.add(0.0);
                }
                i++;
                continue;
            }

            numberList.add(exampleElement.getNumber());
            i += exampleElement.getLength();
        }
        return numberList;
    }

    static int getClosingBracketIndex(final String subExpression) {
        int bracketAmount = 0;
        int lastBracketIndex = 0;
        char currentChar;

        do {
            currentChar = subExpression.charAt(lastBracketIndex++);

            if (currentChar == '(') {
                bracketAmount++;

            } else if (currentChar == ')') {
                bracketAmount--;
            }
        } while (!(bracketAmount == 0 && currentChar == ')'));
        lastBracketIndex--;

        return lastBracketIndex;
    }

}