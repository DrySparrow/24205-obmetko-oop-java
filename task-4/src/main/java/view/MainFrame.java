package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    // Метки складов
    private final JLabel bodyLabel = new JLabel();
    private final JLabel engineLabel = new JLabel();
    private final JLabel carLabel = new JLabel();
    private final JPanel accessoryPanel = new JPanel(new GridLayout(0, 1));
    private final Map<String, JLabel> accessoryLabels = new HashMap<>();

    // Списки сущностей
    private final DefaultListModel<EntityStatus> workerModel = new DefaultListModel<>();
    private final JList<EntityStatus> workerList = new JList<>(workerModel);

    public MainFrame() {
        setTitle("Factory Live Monitor");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Левая панель: Склады
        JPanel storagePanel = new JPanel(new GridLayout(0, 1));
        storagePanel.setBorder(BorderFactory.createTitledBorder("Storages"));
        storagePanel.add(bodyLabel);
        storagePanel.add(engineLabel);
        storagePanel.add(carLabel);
        add(storagePanel, BorderLayout.WEST);
        storagePanel.add(new JLabel("--- Accessories ---"));
        storagePanel.add(accessoryPanel);

        // Центральная панель: Список потоков
        JPanel threadPanel = new JPanel(new BorderLayout());
        threadPanel.setBorder(BorderFactory.createTitledBorder("Thread Activity"));
        workerList.setCellRenderer(new StatusCellRenderer());
        threadPanel.add(new JScrollPane(workerList), BorderLayout.CENTER);
        add(threadPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Метод для обновления статуса конкретного рабочего/поставщика
    public void updateEntity(String name, boolean isBusy) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < workerModel.size(); i++) {
                if (workerModel.get(i).name.equals(name)) {
                    workerModel.get(i).isBusy = isBusy;
                    workerList.repaint();
                    return;
                }
            }
            workerModel.addElement(new EntityStatus(name, isBusy));
        });
    }

    public void updateStorage(String type, int curr, int max) {
        SwingUtilities.invokeLater(() -> {
            String text = type + ": " + curr + " / " + max;

            if (type.startsWith("Accessory")) {
                // Если метки для этого типа еще нет — создаем её
                if (!accessoryLabels.containsKey(type)) {
                    JLabel newLabel = new JLabel(text);
                    accessoryLabels.put(type, newLabel);
                    accessoryPanel.add(newLabel);
                    accessoryPanel.revalidate(); // Пересчитать макет
                    accessoryPanel.repaint();
                } else {
                    accessoryLabels.get(type).setText(text);
                }
            } else {
                // Для обычных складов
                if (type.equals("Body")) bodyLabel.setText(text);
                if (type.equals("Engine")) engineLabel.setText(text);
                if (type.equals("Car")) carLabel.setText(text);
            }
        });
    }

    // Вспомогательный класс для хранения данных строки
    private static class EntityStatus {
        String name;
        boolean isBusy;
        EntityStatus(String name, boolean isBusy) { this.name = name; this.isBusy = isBusy; }
    }

    // Отрисовщик кружочков
    private static class StatusCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            EntityStatus status = (EntityStatus) value;
            label.setText(status.name);
            label.setIcon(new CircleIcon(status.isBusy ? Color.GREEN : Color.RED));
            return label;
        }
    }

    // Класс для рисования кружка
    private static class CircleIcon implements Icon {
        private final Color color;
        CircleIcon(Color color) { this.color = color; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillOval(x, y, 10, 10);
        }
        public int getIconWidth() { return 12; }
        public int getIconHeight() { return 12; }
    }
}