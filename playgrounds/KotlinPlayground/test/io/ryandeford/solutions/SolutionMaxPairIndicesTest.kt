package io.ryandeford.solutions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SolutionMaxPairIndicesTest {

    @Test
    fun maxPair_InputNull() {
        val input = null

        // Make sure an IllegalArgumentException is thrown and confirm the exception message
        val exception = assertThrows<IllegalArgumentException> { SolutionMaxPairIndices.maxPair(input) }
        assertEquals("The input cannot be null", exception.message)

        // Make sure that if we call the method with null input again, the exact same exception object instance is returned
        val exceptionOther = assertThrows<Throwable> { SolutionMaxPairIndices.maxPair(input) }
        assertSame(exception, exceptionOther)
    }

    @Test
    fun maxPair_InputInvalidSizes() {
        // Invalid sizes include any input with a size less than two values (e.g. empty or a size of 1)
        arrayOf(
            intArrayOf(),
            intArrayOf(1)
        ).forEach { input ->
            // Make sure an IllegalArgumentException is thrown and confirm the exception message
            val exception = assertThrows<IllegalArgumentException> { SolutionMaxPairIndices.maxPair(input) }
            assertEquals("The input must contain at least two values", exception.message)

            // Make sure that if we call the method with null input again, the exact same exception object instance is returned
            val exceptionOther = assertThrows<Throwable> { SolutionMaxPairIndices.maxPair(input) }
            assertSame(exception, exceptionOther)
        }
    }

    @Test
    fun maxPair_InputSizeOfTwo() {
        val input = intArrayOf(1,2)

        // Make sure the result is correct
        val result = SolutionMaxPairIndices.maxPair(input)
        assertEquals(Pair(0,1), result)

        // Make sure that if we call the method with two-item input again, the exact same result object instance is returned
        val resultOther = SolutionMaxPairIndices.maxPair(input)
        assertSame(result, resultOther)
    }

    @Test
    fun maxPair_InputVarious() {
        // Create map of expected inputs => outputs and confirm all results match
        // Note: Discuss if we want to break these up into many different isolated tests, as some teams prefer that approach
        mapOf(
            intArrayOf(0,5,5,2) to Pair(1,2),
            intArrayOf(0,4,3,1,2,3,4,0) to Pair(1,2),
            intArrayOf(0,0,0) to Pair(0,1),
            intArrayOf(0,1,0) to Pair(0,1),
            intArrayOf(0,1,1) to Pair(1,2),
            intArrayOf(1,1,1) to Pair(0,1),
            intArrayOf(1,1,1,1) to Pair(0,1),
            intArrayOf(1,2,2,2) to Pair(1,2),
            intArrayOf(1,1,2,2) to Pair(2,3),
            intArrayOf(1,1,3,2) to Pair(2,3),
            intArrayOf(-1,-2,-3) to Pair(0,1),
            intArrayOf(-1,-2,3) to Pair(1,2),
            intArrayOf(-1,-2,-3,0) to Pair(0,1),
            intArrayOf(-1,-2,-3,1) to Pair(2,3),
            intArrayOf(5,2,8,-10,33,17,2,-100) to Pair(4,5),
            intArrayOf(5,2,8,-10,33,17,2,-100,200,-100,200) to Pair(7,8),
            intArrayOf(0,1,-1,2,-2,3,-3,4,-4,5,-5,0) to Pair(0,1),
            intArrayOf(1,-1,2,-2,3,-3,4,-4,5,-5,7) to Pair(9,10),
        ).forEach { (input, expectedOutput) ->
            val result = SolutionMaxPairIndices.maxPair(input)
            assertEquals(
                expectedOutput,
                result,
                "Result did not match expected output for: Input->${input.toList()}"
            )
        }
    }
}