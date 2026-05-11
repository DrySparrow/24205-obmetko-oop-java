package model.objects;

import java.util.List;

public class Car {
    private final int id;
    private final Body body;
    private final Engine engine;
    private final List<Accessory> accessories; // Теперь это список

    public Car(int id, Body body, Engine engine, List<Accessory> accessories) {
        this.id = id;
        this.body = body;
        this.engine = engine;
        this.accessories = accessories;
    }
}