package io.ryandeford.solutions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionStaircaseJumpingPossibilitiesTest {

    @Test
    public void testStairCountNegative() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(-1);
        assertEquals(0, totalJumpPossibilities);
    }

    @Test
    public void testStairCountZero() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(0);
        assertEquals(0, totalJumpPossibilities);
    }

    @Test
    public void testStairCountOne() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(1);
        assertEquals(1, totalJumpPossibilities);
    }

    @Test
    public void testStairCountTwo() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(2);
        assertEquals(2, totalJumpPossibilities);
    }

    @Test
    public void testStairCountThree() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(3);
        assertEquals(4, totalJumpPossibilities);
    }

    @Test
    public void testStairCountFour() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(4);
        assertEquals(7, totalJumpPossibilities);
    }

    @Test
    public void testStairCountFive() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(5);
        assertEquals(13, totalJumpPossibilities);
    }

    @Test
    public void testStairCountSix() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(6);
        assertEquals(24, totalJumpPossibilities);
    }

    @Test
    public void testStairCountSeven() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(7);
        assertEquals(44, totalJumpPossibilities);
    }

    @Test
    public void testStairCountEight() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(8);
        assertEquals(81, totalJumpPossibilities);
    }

    @Test
    public void testStairCountNine() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(9);
        assertEquals(149, totalJumpPossibilities);
    }

    @Test
    public void testStairCountTen() {
        long totalJumpPossibilities = SolutionStaircaseJumpingPossibilities.solution(10);
        assertEquals(274, totalJumpPossibilities);
    }
}