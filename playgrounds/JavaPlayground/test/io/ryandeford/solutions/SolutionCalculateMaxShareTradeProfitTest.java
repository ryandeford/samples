package io.ryandeford.solutions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionCalculateMaxShareTradeProfitTest {

    @Test
    public void testNull() {
        long result = SolutionCalculateMaxShareTradeProfit.solution(null);
        assertEquals(0, result);
    }

    @Test
    public void testEmpty() {
        long result = SolutionCalculateMaxShareTradeProfit.solution(new long[]{});
        assertEquals(0, result);
    }

    @Test
    public void testSingle() {
        long result = SolutionCalculateMaxShareTradeProfit.solution(new long[]{100});
        assertEquals(0, result);
    }

    @Test
    public void testTwo() {
        long result = SolutionCalculateMaxShareTradeProfit.solution(new long[]{10, 15});
        assertEquals(5, result);
    }
}
