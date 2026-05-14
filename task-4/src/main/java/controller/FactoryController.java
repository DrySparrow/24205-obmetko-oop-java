package controller;

import model.FactoryModel;
import model.util.Config;
import view.MainFrame;
import javax.swing.SwingUtilities;

public class FactoryController {
    private final Config config;
    private final FactoryModel model;
    private final MainFrame view;

    public FactoryController(String configPath) {
        this.config = new Config(configPath);
        this.model = new FactoryModel(config);
        this.view = new MainFrame();

        model.setStatusListener((name, isBusy) -> {
            view.updateEntity(name, isBusy);
        });

        initStorageObservers();

        for (int i = 0; i < config.getCarCapacity(); i++) {
            model.sendTaskToWorkers(config);
        }
    }

    private void initStorageObservers() {
        // 1. Тела
        model.getBodyStorage().addObserver(size ->
                view.updateStorage("Body", size, config.getBodyCapacity()));
        // ПРИНУДИТЕЛЬНОЕ ОБНОВЛЕНИЕ ПРИ СТАРТЕ
        view.updateStorage("Body", model.getBodyStorage().getCurrentSize(), config.getBodyCapacity());

        // 2. Двигатели
        model.getEngineStorage().addObserver(size ->
                view.updateStorage("Engine", size, config.getEngineCapacity()));
        view.updateStorage("Engine", model.getEngineStorage().getCurrentSize(), config.getEngineCapacity());

        // 3. Аксессуары
        for (int i = 0; i < config.getAccessorySuppliersCount(); i++) {
            final int index = i;
            model.getAccessoryStorages().get(i).addObserver(size ->
                    view.updateStorage("Accessory-" + index, size, config.getAccessoryCapacity())
            );
            view.updateStorage("Accessory-" + index, model.getAccessoryStorages().get(index).getCurrentSize(), config.getAccessoryCapacity());
        }

        // 4. Машины
        model.getCarStorage().addObserver(size -> {
            view.updateStorage("Car", size, config.getCarCapacity());
            if (size < config.getCarCapacity()) {
                model.sendTaskToWorkers(config);
            }
        });
        view.updateStorage("Car", model.getCarStorage().getCurrentSize(), config.getCarCapacity());

        // Потоки (инициализация списка)
        for (int i = 0; i < config.getWorkersCount(); i++) view.updateEntity("Worker-" + i, false);
        for (int i = 0; i < config.getDealersCount(); i++) view.updateEntity("Dealer-" + i, false);
    }
}