package io.ryandeford.solutions;

import java.util.Set;

public class SolutionStaircaseJumpingPossibilities {

    public static long solution(long n) {
        // If no stairs are present or a negative stair count is provided, return 0
        if (n <= 0) {
            return 0;
        }
        // 1
        else if (n == 1) {
            return 1;
        }
        // 1 + 1
        // 2
        else if (n == 2) {
            return 2;
        }
        // 1 + 1 + 1
        // 1 + 2
        // 2 + 1
        // 3
        else if (n == 3) {
            return 4;
        }

        return solution(n-1) + solution(n-2) + solution(n-3);
    }
}
