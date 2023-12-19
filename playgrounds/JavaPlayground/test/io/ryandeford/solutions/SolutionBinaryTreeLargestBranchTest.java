package io.ryandeford.solutions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionBinaryTreeLargestBranchTest {

    @Test
    public void testNull() {
        String result = SolutionBinaryTreeLargestBranch.solution(null);
        assertEquals("", result);
    }

    @Test
    public void testEmpty() {
        String result = SolutionBinaryTreeLargestBranch.solution(new long[]{});
        assertEquals("", result);
    }

    @Test
    public void testSingleNode() {
        String result = SolutionBinaryTreeLargestBranch.solution(new long[]{0});
        assertEquals("", result);
    }

    @Test
    public void testLeftIsBigger() {
        String result = SolutionBinaryTreeLargestBranch.solution(new long[]{3, 6, 2, 9, -1, 10});
        assertEquals("Left", result);
    }

    @Test
    public void testRightIsBigger() {
        String result = SolutionBinaryTreeLargestBranch.solution(new long[]{1, 4, 100, 5});
        assertEquals("Right", result);
    }

    @Test
    public void testBothAreEqual() {
        String result = SolutionBinaryTreeLargestBranch.solution(new long[]{1, 10, 5, 1, 0, 6});
        assertEquals("", result);
    }
}
