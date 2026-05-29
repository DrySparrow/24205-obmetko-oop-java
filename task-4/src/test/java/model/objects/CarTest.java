package model.objects;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    @Test
    void testCarInitialisation() {
        Body body = new Body();
        Engine engine = new Engine();
        Accessory acs1 = new Accessory("acs1", 1);
        Accessory acs2 = new Accessory("acs2", 2);
        ArrayList<Accessory> accessories = new ArrayList<>();
        accessories.add(acs1);
        accessories.add(acs2);
        Car car = new Car(10, body, engine, accessories);

        assertEquals(10, car.getId());
    }
}