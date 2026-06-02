package controller;

import model.FactoryModel;
import model.util.Config;
import view.MainFrame;

public class FactoryController {
    private final Config config;
    private final FactoryModel model;
    private final MainFrame view;

    public FactoryController(String configPath) {
        this.config = new Config(configPath);
        this.model = new FactoryModel(config);
        this.view = new MainFrame();

        // Слушатель для обновления лампочек активности потоков в UI
        model.setStatusListener((name, isBusy) -> {
            view.updateEntity(name, isBusy);
        });

        initStorageObservers();

        // Запускаем стартовую сборку автомобилей
        for (int i = 0; i < config.getCarCapacity(); i++) {
            model.sendTaskToWorkers(config);
        }
    }

    private void initStorageObservers() {
        // 1. Тела
        model.getBodyStorage().addObserver(size ->
                view.updateStorage("Body", size, config.getBodyCapacity()));

        // 2. Двигатели
        model.getEngineStorage().addObserver(size ->
                view.updateStorage("Engine", size, config.getEngineCapacity()));

        // 3. Аксессуары
        for (int i = 0; i < config.getAccessorySuppliersCount(); i++) {
            int index = i;
            model.getAccessoryStorages().get(i).addObserver(size ->
                    view.updateStorage("Accessory-" + index, size, config.getAccessoryCapacity())
            );
        }

        // 4. Машины
        model.getCarStorage().addObserver(size -> {
            view.updateStorage("Car", size, config.getCarCapacity());
            if (size < config.getCarCapacity()) {
                model.sendTaskToWorkers(config);
            }
        });

        // === Инициализация списка активности потоков (Thread Activity) ===

        // Потоки сборщиков автомобиля
        for (int i = 0; i < config.getWorkersCount(); i++) {
            view.updateEntity("Worker-" + i, false);
        }

        // Потоки дилеров
        for (int i = 0; i < config.getDealersCount(); i++) {
            view.updateEntity("Dealer-" + i, false);
        }

        // Потоки основных поставщиков
        view.updateEntity("Supplier-Body", false);
        view.updateEntity("Supplier-Engine", false);

        // Потоки поставщиков аксессуаров (имена теперь строго совпадают с тем, что генерирует AccessorySupplier)
        for (int i = 0; i < config.getAccessorySuppliersCount(); i++) {
            view.updateEntity("Supplier-Accessory-Type_" + i, false);
        }
    }
}