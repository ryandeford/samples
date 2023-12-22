package io.ryandeford.solutions;

class SolutionFindMaxNumber {
    public static long solution(long[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }

        long maxNumberValue = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > maxNumberValue) {
                maxNumberValue = numbers[i];
            }
        }

        return maxNumberValue;
    }
}