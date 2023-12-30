import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SampleClassTest {
    SampleClass sampleClass = new SampleClass();
    Map<Integer, Integer> fibonacciExpectedValues = Map.ofEntries(
            Map.entry(0, 0),
            Map.entry(1, 1),
            Map.entry(2, 1),
            Map.entry(3, 2),
            Map.entry(4, 3),
            Map.entry(5, 5),
            Map.entry(6, 8),
            Map.entry(7, 13),
            Map.entry(8, 21),
            Map.entry(9, 34),
            Map.entry(10, 55),
            Map.entry(11, 89),
            Map.entry(12, 144),
            Map.entry(13, 233),
            Map.entry(14, 377),
            Map.entry(15, 610),
            Map.entry(16, 987),
            Map.entry(17, 1597),
            Map.entry(18, 2584),
            Map.entry(19, 4181),
            Map.entry(20, 6765)
    );

    @Test
    void sampleFutures() {
        sampleClass.sampleFutures();
    }

    @Test
    void fibonacciRecursive() {
        Exception exceptionForNegativeInput = assertThrows(
                IllegalArgumentException.class,
                () -> sampleClass.fibonacciRecursive(-1)
        );
        assertEquals("The input must be an integer greater than or equal to zero", exceptionForNegativeInput.getMessage());

        fibonacciExpectedValues.forEach((input, expectedOutput) -> {
            assertEquals(expectedOutput, sampleClass.fibonacciRecursive(input));
        });
    }

    @Test
    void fibonacciIterative() {
        Exception exceptionForNegativeInput = assertThrows(
                IllegalArgumentException.class,
                () -> sampleClass.fibonacciIterative(-1)
        );
        assertEquals("The input must be an integer greater than or equal to zero", exceptionForNegativeInput.getMessage());

        fibonacciExpectedValues.forEach((input, expectedOutput) -> {
            assertEquals(expectedOutput, sampleClass.fibonacciIterative(input));
        });
    }
}