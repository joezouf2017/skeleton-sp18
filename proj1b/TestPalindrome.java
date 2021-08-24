import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("cat"));
    }

    @Test
    public void testIsPalindromeCC() {
        CharacterComparator offbyone = new OffByOne();
        assertTrue(palindrome.isPalindrome("", offbyone));
        assertTrue(palindrome.isPalindrome("a", offbyone));
        assertTrue(palindrome.isPalindrome("flake", offbyone));
        assertFalse(palindrome.isPalindrome("cat", offbyone));
        assertFalse(palindrome.isPalindrome("noon", offbyone));
    }
}
