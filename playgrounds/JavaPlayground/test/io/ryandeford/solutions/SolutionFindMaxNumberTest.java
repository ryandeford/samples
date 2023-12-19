package io.ryandeford.solutions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionFindMaxNumberTest {
    @Test
    public void testNull() {
        long result = SolutionFindMaxNumber.solution(null);
        assertEquals(0, result);
    }

    @Test
    public void testEmpty() {
        long result = SolutionFindMaxNumber.solution(new long[]{});
        assertEquals(0, result);
    }

    @Test
    public void testAllSingleZero() {
        long result = SolutionFindMaxNumber.solution(new long[]{0});
        assertEquals(0, result);
    }

    @Test
    public void testAllSinglePositive() {
        long result = SolutionFindMaxNumber.solution(new long[]{1});
        assertEquals(1, result);
    }

    @Test
    public void testAllSingleNegative() {
        long result = SolutionFindMaxNumber.solution(new long[]{-1});
        assertEquals(-1, result);
    }

    @Test
    public void testAllZeroes() {
        long result = SolutionFindMaxNumber.solution(new long[]{0, 0, 0});
        assertEquals(0, result);
    }

    @Test
    public void testAllPositives() {
        long result = SolutionFindMaxNumber.solution(new long[]{1, 2, 3});
        assertEquals(3, result);
    }

    @Test
    public void testAllNegatives() {
        long result = SolutionFindMaxNumber.solution(new long[]{-1, -2, -3});
        assertEquals(-1, result);
    }

    @Test
    public void testAllSorted() {
        long result = SolutionFindMaxNumber.solution(new long[]{-1, 0, 1});
        assertEquals(1, result);
    }

    @Test
    public void testAllRandom() {
        long result = SolutionFindMaxNumber.solution(new long[]{10, 0, -10, 100, -5, 0, 5, -100});
        assertEquals(100, result);
    }
}
