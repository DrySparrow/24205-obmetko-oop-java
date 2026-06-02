package model.objects;

public class Accessory {
    private final String name;
    private final int id;

    public Accessory(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public String getType() {
        return this.name;
    }
}
