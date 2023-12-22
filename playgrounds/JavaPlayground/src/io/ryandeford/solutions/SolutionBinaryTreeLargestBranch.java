package io.ryandeford.solutions;

class SolutionBinaryTreeLargestBranch {
    public static String solution(long[] arr) {
        if (arr == null || arr.length <= 1) {
            return "";
        }

        long leftBranchSum = 0;
        long rightBranchSum = 0;

        int currentTreeLevel = 1;
        int numberOfNodesExpectedInCurrentLevel = (int) Math.pow(2, currentTreeLevel);
        int numberOfNodesTraversedInCurrentLevel = 0;

        for (int i = 1; i < arr.length; i++) {
            if (numberOfNodesTraversedInCurrentLevel == numberOfNodesExpectedInCurrentLevel) {
                currentTreeLevel++;
                numberOfNodesTraversedInCurrentLevel = 0;
            }

            numberOfNodesExpectedInCurrentLevel = (int) Math.pow(2, currentTreeLevel);

            long currentNodeValue = arr[i];

            if (currentNodeValue != -1) {
                if (numberOfNodesTraversedInCurrentLevel < numberOfNodesExpectedInCurrentLevel / 2) {
                    leftBranchSum += currentNodeValue;
                } else {
                    rightBranchSum += currentNodeValue;
                }
            }

            numberOfNodesTraversedInCurrentLevel++;
        }

        String result;

        if (leftBranchSum > rightBranchSum) {
            result = "Left";
        }
        else if (rightBranchSum > leftBranchSum) {
            result = "Right";
        }
        else {
            result = "";
        }

        return result;
    }
}