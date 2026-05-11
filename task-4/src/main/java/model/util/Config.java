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
        return Integer.parseInt(props.getProperty("storage.body.capacity"));
    }

    public int getEngineCapacity() {
        return Integer.parseInt(props.getProperty("storage.engine.capacity"));
    }

    public int getAccessoryCapacity() {
        return Integer.parseInt(props.getProperty("storage.accessory.capacity"));
    }

    public int getCarCapacity() {
        return Integer.parseInt(props.getProperty("storage.car.capacity"));
    }

    public int getBodySuppliersCount() {
        return Integer.parseInt(props.getProperty("suppliers.body.count"));
    }

    public int getEngineSuppliersCount() {
        return Integer.parseInt(props.getProperty("suppliers.engine.count"));
    }

    public int getAccessorySuppliersCount() {
        return Integer.parseInt(props.getProperty("suppliers.accessory.count"));
    }
    public int getWorkersCount() {
        return Integer.parseInt(props.getProperty("workers.count"));
    }

    public int getDealersCount() {
        return Integer.parseInt(props.getProperty("dealers.count"));
    }
}