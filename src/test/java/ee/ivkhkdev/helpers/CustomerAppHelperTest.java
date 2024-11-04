package ee.ivkhkdev.helpers;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerAppHelperTest {
    Input inputMock;
    AppHelper<Customer> appHelperCustomer;
    PrintStream defaultOut = System.out;
    ByteArrayOutputStream outMock;

    @BeforeEach
    void setUp() {
        inputMock = Mockito.mock(Input.class);
        appHelperCustomer = new CustomerAppHelper(inputMock);
        outMock = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outMock));
    }

    @AfterEach
    void tearDown() {
        inputMock = null;
        appHelperCustomer = null;
        System.setOut(defaultOut);
        outMock = null;
    }

    @Test
    void create() {
        when(inputMock.nextLine()).thenReturn("Ivan", "Ivanov", "ivanov@example.com");
        Customer actual = appHelperCustomer.create();
        Customer expected = new Customer("Ivan", "Ivanov", "ivanov@example.com");
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getEmail(), expected.getEmail());
    }

    @Test
    void printList() {
        Customer customer = new Customer("Ivan", "Ivanov", "ivanov@example.com");
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        boolean result = appHelperCustomer.printList(customers);
        assertTrue(result);
        String expectedString = "1. Имя: Ivan, Фамилия: Ivanov, Email: ivanov@example.com";
        assertTrue(outMock.toString().contains(expectedString));
    }

    @Test
    void testUpdateWithValidInput() {
        // Создаем список клиентов
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setFirstName("Ivan");
        customer.setLastName("Ivanov");
        customer.setEmail("ivanov@example.com");
        customers.add(customer);

        // Настройка для ввода данных
        when(inputMock.nextLine()).thenReturn("1", "NewFirstName", "NewLastName", "newemail@example.com");

        // Выполняем метод update
        Customer updatedCustomer = appHelperCustomer.update(customers);

        // Проверка, что клиент был обновлен
        assertNotNull(updatedCustomer, "Updated customer should not be null");
        assertEquals("NewFirstName", updatedCustomer.getFirstName(), "First name should be updated");
        assertEquals("NewLastName", updatedCustomer.getLastName(), "Last name should be updated");
        assertEquals("newemail@example.com", updatedCustomer.getEmail(), "Email should be updated");
    }

    @Test
    void testUpdateWithEmptyCustomerList() {
        // Создаем пустой список клиентов
        List<Customer> customers = new ArrayList<>();

        // Выполняем метод update
        Customer updatedCustomer = appHelperCustomer.update(customers);

        // Проверка, что метод вернул null
        assertNull(updatedCustomer, "Updated customer should be null for an empty list");
        assertTrue(outMock.toString().contains("Список клиентов пуст"), "Expected message for empty list should be printed");
    }

    @Test
    void testUpdateWithInvalidIndex() {
        // Создаем список клиентов
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setFirstName("Ivan");
        customer.setLastName("Ivanov");
        customer.setEmail("ivanov@example.com");
        customers.add(customer);

        // Настройка для ввода данных
        when(inputMock.nextLine()).thenReturn("2"); // Неверный индекс

        // Выполняем метод update
        Customer updatedCustomer = appHelperCustomer.update(customers);

        // Проверка, что метод вернул null
        assertNull(updatedCustomer, "Updated customer should be null for an invalid index");
        assertTrue(outMock.toString().contains("Ошибка при обновлении данных клиента"), "Expected error message should be printed");
    }

}
