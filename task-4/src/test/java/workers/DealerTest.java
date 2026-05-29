package workers;

import model.FactoryModel;
import model.objects.Car;
import model.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DealerTest {

    @Test
    void testGetDealerId() {
        Storage<Car> mockStorage = mock(Storage.class);
        FactoryModel mockModel = mock(FactoryModel.class);

        Dealer dealer = new Dealer(42, mockStorage, 10, mockModel);

        assertEquals(42, dealer.getDealerId(), "getDealerId должен возвращать корректный ID");
    }

    @Test
    void testRunInterruption() throws InterruptedException {
        Storage<Car> mockStorage = mock(Storage.class);
        FactoryModel mockModel = mock(FactoryModel.class);

        Dealer dealer = new Dealer(1, mockStorage, 50, mockModel);

        // Запускаем Дилера в отдельном потоке, так как внутри run() бесконечный цикл
        Thread thread = new Thread(dealer);
        thread.start();

        // Даем ему чутка поработать
        Thread.sleep(20);

        // Прерываем поток
        thread.interrupt();
        thread.join(500); // Ждем завершения потока не более 500мс

        assertFalse(thread.isAlive(), "Поток дилера должен успешно завершиться после интерапта");
        verify(mockModel, atLeastOnce()).updateThreadStatus(eq("Dealer-1"), anyBoolean());
    }
}