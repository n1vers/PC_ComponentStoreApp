package ee.ivkhkdev.helpers;

import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.AppService;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.model.Purchase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PurchaseAppHelperTest {

    private PurchaseAppHelper purchaseAppHelper;
    private Input mockInput;
    private AppService<Component> mockComponentAppService;
    private AppService<Customer> mockCustomerAppService;

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outMock;
    @BeforeEach
    void setUp() {
        // Создаем моки для зависимостей
        mockInput = Mockito.mock(Input.class);
        mockComponentAppService = Mockito.mock(AppService.class);
        mockCustomerAppService = Mockito.mock(AppService.class);

        // Инициализируем PurchaseAppHelper с моками
        purchaseAppHelper = new PurchaseAppHelper(mockInput, mockComponentAppService, mockCustomerAppService);
        outMock = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outMock));
    }
    @AfterEach
    void tearDown() {
        System.setOut(originalOut); // Восстанавливаем оригинальный вывод
        outMock = null;
    }
    @Test
    void testCreateSuccess() {
        // Настройка моков для успешного создания Purchase
        Component mockComponent = new Component();
        mockComponent.setBrand("ASUS");
        mockComponent.setModel("ROG Strix");
        mockComponent.setPrice(15000);

        Customer mockCustomer = new Customer();
        mockCustomer.setFirstName("Ivan");
        mockCustomer.setLastName("Ivanov");

        when(mockComponentAppService.print()).thenReturn(true);
        when(mockComponentAppService.list()).thenReturn(List.of(mockComponent));
        when(mockInput.nextLine()).thenReturn("1"); // Выбор компонента
        when(mockCustomerAppService.print()).thenReturn(true);
        when(mockCustomerAppService.list()).thenReturn(List.of(mockCustomer));
        when(mockInput.nextLine()).thenReturn("1"); // Выбор покупателя

        // Выполнение метода create
        Purchase result = purchaseAppHelper.create();

        // Проверка результата
        assertNotNull(result);
        assertEquals(mockComponent, result.getComponent());
        assertEquals(mockCustomer, result.getCustomer());
        assertEquals(LocalDate.now(), result.getPurchaseDate());
    }

    @Test
    void testCreateComponentServicePrintFails() {
        // Настройка моков, чтобы componentService.print() вернул false
        when(mockComponentAppService.print()).thenReturn(false);

        // Выполнение метода create
        Purchase result = purchaseAppHelper.create();

        // Проверка результата
        assertNull(result);
    }

    @Test
    void testCreateCustomerServicePrintFails() {
        // Настройка моков для успешного выбора компонента, но пользовательский сервис не удается
        Component mockComponent = new Component();
        mockComponent.setBrand("ASUS");
        mockComponent.setModel("ROG Strix");
        mockComponent.setPrice(15000);

        when(mockComponentAppService.print()).thenReturn(true);
        when(mockComponentAppService.list()).thenReturn(List.of(mockComponent));
        when(mockInput.nextLine()).thenReturn("1"); // Выбор компонента
        when(mockCustomerAppService.print()).thenReturn(false);

        // Выполнение метода create
        Purchase result = purchaseAppHelper.create();

        // Проверка результата
        assertNull(result);
    }


    @Test
    void testPrintListWithPurchases() {
        Component component = new Component();
        component.setBrand("ASUS");
        component.setModel("ROG Strix");
        component.setPrice(15000.00);

        Customer customer = new Customer();
        customer.setFirstName("Ivan");
        customer.setLastName("Ivanov");

        Purchase purchase = new Purchase();
        purchase.setComponent(component);
        purchase.setCustomer(customer);
        purchase.setPurchaseDate(LocalDate.now());

        List<Purchase> purchases = List.of(purchase);

        // Выполняем метод printList
        boolean result = purchaseAppHelper.printList(purchases);

        // Проверка
        assertTrue(result);
        String expectedString = String.format("1. Компонент: ASUS ROG Strix 15000.0 $, Покупатель: Ivan Ivanov, Дата покупки: %s", LocalDate.now());
        assertTrue(outMock.toString().contains(expectedString));
    }

    @Test
    void testPrintListWithNoPurchases() {
        // Подготовка: пустой список покупок
        List<Purchase> purchases = List.of();

        // Выполняем метод printList
        boolean result = purchaseAppHelper.printList(purchases);

        // Проверка
        assertFalse(result);
    }
}
