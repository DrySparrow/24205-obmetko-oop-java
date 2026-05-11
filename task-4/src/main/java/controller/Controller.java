package controller;

import model.FactoryModel;
import model.storage.StorageObserver;
import model.util.Config;

public class Controller {
    private final FactoryModel model;

    public Controller() {
        Config config = new Config("config.properties");

        this.model = new FactoryModel(config);

        model.getCarStorage().addObserver(size -> System.out.println("[INFO] Машин на складе: " + size));

        System.out.println("--- Завод запущен ---");
    }

    public class FileObserver implements StorageObserver {
        @Override
        public void onStorageSizeChanged(int currentSize) {
            // Здесь будет код записи в файл
        }
    }
}