import java.util.*;

public class SecretNumber {
    private final String value;
    private final int length;

    public SecretNumber(int length) {   // generate a random number with the given length
        this.length = length;
        List<String> digits = Arrays.asList("0123456789".split(""));
        Collections.shuffle(digits);
        this.value = String.join("", digits.subList(0, length));
    }

    public String getValue() { return value; }
    public int getLength() { return length; }
}
