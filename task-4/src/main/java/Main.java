import controller.Controller;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Запуск Swing-интерфейса должен происходить в специальном потоке
        SwingUtilities.invokeLater(() -> {
            new Controller();
        });
    }
}