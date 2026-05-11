package view;

import model.storage.StorageObserver;

import javax.swing.*;

public class StoragePanel extends JPanel implements StorageObserver {
    private final String itemType; // Название типа детали
    private final JProgressBar progressBar;
    private final JLabel countLabel;

    public StoragePanel(String itemType, int maxCapacity) {
        this.itemType = itemType; // Запоминаем: "Двигатели", "Кузова" и т.д.

        this.progressBar = new JProgressBar(0, maxCapacity);
        this.countLabel = new JLabel(itemType + ": 0"); // Начальный текст

        add(new JLabel("Склад: " + itemType));
        add(progressBar);
        add(countLabel);
    }

    @Override
    public void onStorageSizeChanged(int currentSize) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(currentSize);
            countLabel.setText(itemType + ": " + currentSize);
        });
    }
}