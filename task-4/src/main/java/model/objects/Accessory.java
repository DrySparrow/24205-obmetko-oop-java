package model.objects;

public class Accessory {
    private final String name;
    private final int id;

    public Accessory(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
