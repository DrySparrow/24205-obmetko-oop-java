package model.util; // или другой твой пакет

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties props = new Properties();

    public Config(String fileName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("Не удалось найти файл конфигурации: " + fileName);
            }
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBodyCapacity() {
        return Integer.parseInt(props.getProperty("Storage.body.capacity"));
    }

    public int getEngineCapacity() {
        return Integer.parseInt(props.getProperty("Storage.engine.capacity"));
    }

    public int getAccessoryCapacity() {
        return Integer.parseInt(props.getProperty("Storage.accessory.capacity"));
    }

    public int getCarCapacity() {
        return Integer.parseInt(props.getProperty("Storage.car.capacity"));
    }

    public int getBodySuppliersCount() {
        return Integer.parseInt(props.getProperty("BodySuppliers.count"));
    }

    public int getEngineSuppliersCount() {
        return Integer.parseInt(props.getProperty("EngineSuppliers.count"));
    }

    public int getAccessorySuppliersCount() { return Integer.parseInt(props.getProperty("AccessorySuppliers.count")); }

    public int getWorkersCount() { return Integer.parseInt(props.getProperty("Workers.count")); }

    public int getDealersCount() {
        return Integer.parseInt(props.getProperty("Dealers.count"));
    }

    public int getBodySuppliersDelay() { return Integer.parseInt(props.getProperty("BodySuppliers.delay")); }

    public int getEngineSuppliersDelay() { return Integer.parseInt(props.getProperty("EngineSuppliers.delay")); }

    public int getAccessorySuppliersDelay()  {return Integer.parseInt(props.getProperty("AccessorySuppliers.delay")); }

    public int getWorkersDelay() { return Integer.parseInt(props.getProperty("Workers.delay")); }

    public int getDealersDelay() { return Integer.parseInt(props.getProperty("Dealers.delay")); }
}