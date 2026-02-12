import java.util.*;

public class SecretNumber {
    private final String value;
    private final int length;

    public SecretNumber(int length) {   // generate a random number with the given length
        this.length = length;
        List<String> digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) digits.add(String.valueOf(i));
        Collections.shuffle(digits);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) sb.append(digits.get(i));
        this.value = sb.toString();
    }

    public String getValue() { return value; }
    public int getLength() { return length; }
}
