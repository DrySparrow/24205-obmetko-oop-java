import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SecretNumberTest {
    @Test
    void testLength() {
        SecretNumber sn = new SecretNumber(4);
        assertEquals(4, sn.getLength(), "Длина числа должна быть 4");
    }

    @Test
    void testIsDigit() {
        SecretNumber sn = new SecretNumber(4);
        assertTrue(sn.getValue().matches("\\d+"), "Должны быть только цифры");
    }
}
