package io.ryandeford.solutions

/***
 * This is a sample solution to the coding challenge presented by Block Party, implemented in Kotlin: https://kotlinlang.org
 *
 * In a language of your choice, please write a function that will output the indices of the two consecutive elements
 * that have the highest max sum in an input array of integers (e.g., maxPair([0, 5, 5, 2]) will return (1, 2)). In the
 * case of a tie, the method should return the leftmost pair (e.g., maxPair([0, 4, 3, 1, 2, 3, 4, 0]) will return (1, 2)).
 */
class SolutionMaxPairIndices {
    // We'll use this to store static constants/configurations, predefined results, and a map to cache results
    // Note: We're also making the sample maxPair method static so that it's easy to access without object instantiation
    // Note: In real life, we'd generally avoid statics and utilize dependency injection frameworks to wire in configurations/implementations
    companion object {
        private val ERROR_FOR_NULL_INPUT = IllegalArgumentException("The input cannot be null")
        private val ERROR_FOR_INPUT_OF_INVALID_SIZE = IllegalArgumentException("The input must contain at least two values")

        private val RESPONSE_FOR_INPUT_OF_SIZE_2 = Pair(0,1)

        // Note: Discuss with team if caching is desired/necessary, and consider adding better configs/controls to disable caching, limit size, etc.
        private const val CACHING_ENABLED = true
        private val INPUT_TO_RESPONSE_CACHE = mutableMapOf<String, Pair<Int,Int>>()

        // Note: Kotlin has neat built-in features for null safety, and if we wanted, we could force this method to not accept null inputs
        // Note: Discuss method contracts with the team to see what the best option is, for now we're allowing null inputs
        fun maxPair(input: IntArray?): Pair<Int,Int> {
            // If the input is null, throw an exception
            // Note: Discuss how we want to handle this with the team, another option would be to return null, [-1,-1], etc.
            // Note: Discuss if we really want to always return the same exact exception instance, another option would be to construct a new object
            if (input == null) {
                throw ERROR_FOR_NULL_INPUT
            }
            // If the input has less than 2 values, throw an exception
            // Note: Discuss how we want to handle this with the team, another option would be to return null, [-1,-1], etc.
            // Note: Discuss if we really want to always return the same exact exception instance, another option would be to construct a new object
            if (input.size < 2) {
                throw ERROR_FOR_INPUT_OF_INVALID_SIZE
            }
            // If the input only contains two values, we'll always return a constant [0,1] result
            // Note: Discuss if we really want to always return the same exact result instance, another option would be to construct a new object
            if (input.size == 2) {
                return RESPONSE_FOR_INPUT_OF_SIZE_2
            }
            // If caching is enabled and the input has been seen before with the result already cached, return the cached result
            // WARNING: This could potentially be dangerous if there are massive amounts of input/output pairs being cached, so again, discuss with team
            if (CACHING_ENABLED && INPUT_TO_RESPONSE_CACHE.contains(getHashKey(input))) {
                return INPUT_TO_RESPONSE_CACHE.getValue(getHashKey(input));
            }

            // Setup mutable variables to store the highest sum of consecutive values along with the indices
            // Note: At this point, we already know there are at least 2 input values present, so we'll set the initial condition for max sum/indices directly
            var currentMaxConsecutiveSum = input[0] + input[1]
            var currentMaxConsecutiveSumIndices = Pair(0,1)

            // Iterate through the input value and determine which index pairs maps to the highest sum of consecutive values
            for (i in 2..<input.size) {
                // Get current sum of consecutive inputs (previous + current value)
                val sum = input[i-1] + input[i]

                // If the current sum is greater than the max consecutive sum we've seen in the past, update stored max sum/indices
                if (sum > currentMaxConsecutiveSum) {
                    currentMaxConsecutiveSum = sum
                    currentMaxConsecutiveSumIndices = Pair(i-1, i)
                }
            }

            if (CACHING_ENABLED) {
                INPUT_TO_RESPONSE_CACHE[getHashKey(input)] = currentMaxConsecutiveSumIndices
            }

            return currentMaxConsecutiveSumIndices;
        }

        // This is a simple way to get an easy-to-use hash code for IntArray instances
        fun getHashKey(input: IntArray?): String {
            return when(input) {
                // If the input is null, just return the string "null"
                null -> "null"
                // Otherwise return all the items of the array joined with a comma
                else -> input.joinToString(separator = ",")
            }
        }
    }
}