package model.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AccessoryTest {

    @Test
    void testInitialisationAccessory() {
        Accessory acs = new Accessory("1", 10);
        String name = acs.getName();
        int id = acs.getId();

        assertEquals("1", name);
        assertEquals(10, id);
    }
}