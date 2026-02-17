import java.util.*;

public class BullsAndCowsEvaluator {
    // evaluate the guess and return the BullsAndCowsResult(bulls, cows)
    public BullsAndCowsResult evaluate(String guess, String secret) {
        int bulls = 0, cows = 0;
        List<Character> left = new ArrayList<>();
        for (char c : secret.toCharArray()) left.add(c);
        int len = secret.length();

        for (int i = 0; i < len; i++) {
            if (guess.charAt(i) == secret.charAt(i)) {
                bulls++;
                int idx = left.indexOf(guess.charAt(i));
                left.set(idx, '-');
            }
        }
        for (int i = 0; i < len; i++) {
            if (guess.charAt(i) != secret.charAt(i)) {
                int idx = left.indexOf(guess.charAt(i));
                if (idx != -1) {
                    left.set(idx, '-');
                    cows++;
                }
            }
        }
        return new BullsAndCowsResult(bulls, cows);
    }
}
