import org.junit.jupiter.api.Test;

import static examplecalculator.ExampleCalculator.calculateExample;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComplexChecks {
    @Test
    void complexChecks1() {
        String expression = "7+8+3*5-30/3+2^3";
        double value = calculateExample(expression);
        double result = 28;
        assertTrue(value == result, "Ожидалось " + result + ", а получилось " + value);
    }

    @Test
    void complexChecks2() {
        String expression = "-10+(1+2*2)!+(-20)";
        double value = calculateExample(expression);
        double result = 90;
        assertTrue(value == result, "Ожидалось " + result + ", а получилось " + value);
    }

}