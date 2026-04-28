import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {
    @Test
    void emptyInput() {
        assertFalse(InputValidator.isValid("", 4), "'' is incorrect input");
    }

    @Test
    void nonDigit() {
        assertFalse(InputValidator.isValid("abcd", 4), "you can't recognize letters");
    }

    @Test
    void correctInput() {
        assertTrue(InputValidator.isValid("1234", 4), "1234 must be correct input");
    }
}
