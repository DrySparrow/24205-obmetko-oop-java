package workers;

import model.FactoryModel;
import model.objects.Body;
import model.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BodySupplierTest {

    @Test
    void testCreateItemReturnsNewBody() {
        Storage<Body> mockStorage = mock(Storage.class);
        FactoryModel mockModel = mock(FactoryModel.class);

        BodySupplier supplier = new BodySupplier(mockStorage, 10, mockModel);
        Body body = supplier.createItem();

        assertNotNull(body, "Метод должен возвращать созданный объект Кузова");
    }
}