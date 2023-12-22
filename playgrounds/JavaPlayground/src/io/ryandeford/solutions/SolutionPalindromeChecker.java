package io.ryandeford.solutions;

import java.util.List;

public class SolutionPalindromeChecker {
    public static boolean containsOnlyPalindromes(List<String> items) {
        if (items == null || items.isEmpty()) {
            return false;
        }

        for (String item : items) {
            if (!isPalindrome(item)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPalindrome(String item) {
        if (item == null || item.isBlank()) {
            return false;
        }
        /*if (item.length() == 1) {
            return true;
        }*/

        return item.equals(getReverse(item));
    }

    public static String getReverse(String item) {
        if (item == null) {
            return null;
        }
        if (item.length() <= 1) {
            return item;
        }

        StringBuilder reversedItem = new StringBuilder();

        for (int i = item.length()-1; i >= 0; i--) {
            reversedItem.append(item.charAt(i));
        }

        return reversedItem.toString();
    }
}
