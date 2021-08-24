public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word.length() < 2) {
            return true;
        }
        Deque<Character> worddeque = wordToDeque(word);
        return palindromehelper(worddeque, worddeque.removeFirst(),
                worddeque.removeLast());
    }

    public boolean palindromehelper(Deque<Character> word,
                                    char first, char last) {
        if (first != last) {
            return false;
        } else if (word.size() < 2) {
            return true;
        }
        return palindromehelper(word, word.removeFirst(), word.removeLast());
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() < 2) {
            return true;
        }
        Deque<Character> worddeque = wordToDeque(word);
        return offbyonehelper(worddeque, worddeque.removeFirst(),
                worddeque.removeLast(), cc);
    }

    public boolean offbyonehelper(Deque<Character> word, char first,
                                  char last, CharacterComparator cc) {
        if (!cc.equalChars(first, last)) {
            return false;
        } else if (word.size() < 2) {
            return true;
        }
        return offbyonehelper(word, word.removeFirst(),
                word.removeLast(), cc);
    }
}
