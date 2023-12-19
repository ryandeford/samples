package io.ryandeford.solutions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SolutionPalindromeCheckerTest {

    @Test
    void containsOnlyPalindromesNull() {
        List<String> input = null;
        boolean result = SolutionPalindromeChecker.containsOnlyPalindromes(input);
        assertFalse(result);
    }

    @Test
    void containsOnlyPalindromesNullItems() {
        List<String> input = new ArrayList<>();
        input.add(null);
        input.add(null);
        input.add(null);
        boolean result = SolutionPalindromeChecker.containsOnlyPalindromes(input);
        assertFalse(result);
    }

    @Test
    void containsOnlyPalindromesEmpty() {
        List<String> input = Collections.emptyList();
        boolean result = SolutionPalindromeChecker.containsOnlyPalindromes(input);
        assertFalse(result);
    }

    @Test
    void containsOnlyPalindromesEmptyItems() {
        List<String> input = new ArrayList<>();
        input.add("");
        input.add("");
        input.add("");
        boolean result = SolutionPalindromeChecker.containsOnlyPalindromes(input);
        assertFalse(result);
    }

    @Test
    void containsOnlyPalindromesBlankItems() {
        List<String> input = new ArrayList<>();
        input.add(" ");
        input.add("\t");
        input.add(" \r\n ");
        boolean result = SolutionPalindromeChecker.containsOnlyPalindromes(input);
        assertFalse(result);
    }

    @Test
    void containsOnlyPalindromesAllPalindromes() {
        List<String> input = List.of(
                "abccba",
                "123321",
                "10AA01",
                "hello! !olleh",
                "TEST 0000 TSET"
        );
        boolean result = SolutionPalindromeChecker.containsOnlyPalindromes(input);
        assertTrue(result);
    }

    @Test
    void containsOnlyPalindromesNoPalindromes() {
        List<String> input = List.of(
                "abccba!",
                "123321!",
                "10AA01!",
                "hello! !olleh!",
                "TEST 0000 TSET!"
        );
        boolean result = SolutionPalindromeChecker.containsOnlyPalindromes(input);
        assertFalse(result);
    }

    @Test
    void containsOnlyPalindromesSomePalindromes() {
        List<String> input = List.of(
                "abccba",
                "123321!",
                "10AA01!",
                "hello! !olleh!",
                "TEST 0000 TSET!"
        );
        boolean result = SolutionPalindromeChecker.containsOnlyPalindromes(input);
        assertFalse(result);
    }

    @Test
    void isPalindromeNull() {
        String input = null;
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertFalse(result);
    }

    @Test
    void isPalindromeEmpty() {
        String input = "";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertFalse(result);
    }

    @Test
    void isPalindromeBlank() {
        String input = " ";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertFalse(result);
    }

    @Test
    void isPalindromeBlankMultiple() {
        String input = "\t \t \t";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertFalse(result);
    }

    @Test
    void isPalindromeSingleChar() {
        String input = "a";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertTrue(result);
    }

    @Test
    void isPalindromeForNonPalindromeWord() {
        String input = "ab";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertFalse(result);
    }

    @Test
    void isPalindromeForPalindromeWord() {
        String input = "aba";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertTrue(result);
    }

    @Test
    void isPalindromeForNonPalindromeNumber() {
        String input = "12";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertFalse(result);
    }

    @Test
    void isPalindromeForPalindromeNumber() {
        String input = "121";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertTrue(result);
    }

    @Test
    void isPalindromeForNonPalindromeSymbols() {
        String input = ".$";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertFalse(result);
    }

    @Test
    void isPalindromeForPalindromeSymbols() {
        String input = ".$.";
        boolean result = SolutionPalindromeChecker.isPalindrome(input);
        assertTrue(result);
    }

    @Test
    void isPalindromeForMultipleRandomNonPalindromes() {
        Set.of(
                "test",
                "abc123",
                "a random set of characters!",
                "*?< - _ - >?*",
                "!@#$%^&*()"

        ).forEach(word -> {
            boolean result = SolutionPalindromeChecker.isPalindrome(word);
            assertFalse(result, String.format("Expected palindrome check to be false, but wasn't for input: %s", word));
        });
    }

    @Test
    void isPalindromeForMultipleRandomPalindromes() {
        Set.of(
                "noon",
                "racecar",
                "123aaa321",
                "...++...",
                "...+$+...",
                "*? - _ - ?*",
                "*99899*"

        ).forEach(word -> {
            boolean result = SolutionPalindromeChecker.isPalindrome(word);
            assertTrue(result, String.format("Expected palindrome check to be true, but wasn't for input: %s", word));
        });
    }

    @Test
    void getReverseNull() {
        String input = null;
        String result = SolutionPalindromeChecker.getReverse(input);
        assertNull(result);
    }

    @Test
    void getReverseEmpty() {
        String input = "";
        String result = SolutionPalindromeChecker.getReverse(input);
        assertEquals(input, result);
    }

    @Test
    void getReverseBlankSameChars() {
        String input = "   ";
        String result = SolutionPalindromeChecker.getReverse(input);
        assertEquals(input, result);
    }

    @Test
    void getReverseBlankDifferentChars() {
        String input = "\t \r";
        String result = SolutionPalindromeChecker.getReverse(input);
        assertEquals("\r \t", result);
    }

    @Test
    void getReverseSingleChar() {
        String input = "a";
        String result = SolutionPalindromeChecker.getReverse(input);
        assertEquals(input, result);
    }

    @Test
    void getReverseMultipleChars() {
        String input = "abc";
        String result = SolutionPalindromeChecker.getReverse(input);
        assertEquals("cba", result);
    }

    @Test
    void getReverseMixedChars() {
        String input = "abc 123\t ...";
        String result = SolutionPalindromeChecker.getReverse(input);
        assertEquals("... \t321 cba", result);
    }

    @Test
    void getReverseMultipleWordsComparedWithBuiltInStringReverse() {
        Set.of(
                "test",
                "abc123",
                "a random set of characters!",
                "? - _ - ?",
                "!@#$%^&*()"

        ).forEach(word -> {
            String expectedResult = new StringBuilder(word).reverse().toString();
            String result = SolutionPalindromeChecker.getReverse(word);
            assertEquals(expectedResult, result);
        });
    }
}
