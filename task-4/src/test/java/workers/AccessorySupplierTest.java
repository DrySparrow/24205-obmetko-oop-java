package workers;

import model.FactoryModel;
import model.objects.Accessory;
import model.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccessorySupplierTest {

    @Test
    void testCreateItemInSequence() {
        // Mock-объекты для зависимостей
        Storage<Accessory> mockStorage = mock(Storage.class);
        FactoryModel mockModel = mock(FactoryModel.class);

        // Создаем поставщика аксессуаров (например, колеса)
        AccessorySupplier supplier = new AccessorySupplier(mockStorage, "Wheel", 10, mockModel);

        // Так как метод createItem() protected, мы проверяем его работу через вызов
        Accessory firstItem = supplier.createItem();
        Accessory secondItem = supplier.createItem();

        assertNotNull(firstItem, "Первый элемент не должен быть null");
        assertEquals("Wheel", firstItem.getType(), "Тип должен соответствовать переданному");
        assertEquals(1, firstItem.getId(), "ID первого элемента должен быть 1");
        assertEquals(2, secondItem.getId(), "ID второго элемента должен быть 2 (счетчик работает)");
    }
}