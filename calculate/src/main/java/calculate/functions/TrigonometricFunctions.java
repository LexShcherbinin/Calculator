package calculate.functions;

import calculate.StringException;

import java.util.function.Function;

class TrigonometricFunctions {
    private static final Double RAD = Math.acos(-1)/180;

    private static final Function<Double, Double> SIN = value -> Math.sin(value * RAD);
    private static final Function<Double, Double> COS = value -> Math.cos(value * RAD);
    private static final Function<Double, Double> TAN = value -> Math.tan(value * RAD);

    private static final Function<Double, Double> ARCSIN = value -> Math.asin(value) / RAD;
    private static final Function<Double, Double> ARCCOS = value -> Math.acos(value) / RAD;
    private static final Function<Double, Double> ARCTAN = value -> Math.atan(value) / RAD;

    private static final Function<Double, Double> SIN_HYPERBOLIC = value -> Math.sin(value * RAD);
    private static final Function<Double, Double> COS_HYPERBOLIC = value -> Math.cos(value * RAD);
    private static final Function<Double, Double> TAN_HYPERBOLIC = value -> Math.tan(value * RAD);

    private static final Function<Double, Double> NATURAL_LOGARITHM = TrigonometricFunctions::getLogarithm;
    private static final Function<Double, Double> EXP = Math::exp;
    private static final Function<Double, Double> ABS = Math::abs;
    private static final Function<Double, Double> SQRT = Math::sqrt;

    /**
     * Находит натуральный логарифм аргумента
     * @param argument - аргумент
     * @return - возвращает значение ln
     */
    private static Double getLogarithm(final Double argument) {
        if (argument > 0)
            return Math.log(argument);
        else
            throw new ArithmeticException("Аргумент логарифма должен быть положительным");
    }

    /**
     * Распознаёт строку с именем функции и возвращает соответствующую функцию
     * @param functionName - имя функции
     * @return - возвращает функцию для расчётов
     */
    static Function<Double, Double> getTrigonometricFunction(final String functionName) {
        switch (functionName) {
            case "sin":
                return SIN;

            case "cos":
                return COS;

            case "tan":
                return TAN;

            case "asin":
                return ARCSIN;

            case "acos":
                return ARCCOS;

            case "atan":
                return ARCTAN;

            case "sinh":
                return SIN_HYPERBOLIC;

            case "cosh":
                return COS_HYPERBOLIC;

            case "tanh":
                return TAN_HYPERBOLIC;

            case "ln":
                return NATURAL_LOGARITHM;

            case "exp":
                return EXP;

            case "abs":
                return ABS;

            case "sqrt":
                return SQRT;

            default:
                throw new StringException("Неизвестная функция");
        }
    }

}