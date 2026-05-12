package controller;

import model.FactoryModel;
import model.storage.StorageObserver;
import model.util.Config;
import model.workers.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    private final FactoryModel model;

    public Controller() {
        logger.info("Loading configuration...");
        Config config = new Config("config.properties");

        this.model = new FactoryModel(config);

        model.getCarStorage().addObserver(size ->
                logger.info("Storage Update - Cars available: {}", size)
        );
        logger.info("--- Factory System Initialized and Started ---");
    }
}