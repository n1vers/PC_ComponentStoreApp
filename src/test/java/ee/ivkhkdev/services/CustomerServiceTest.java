package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.interfaces.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerService customerService;
    private Repository<Customer> mockRepository;
    private AppHelper<Customer> mockAppHelperCustomer;

    @BeforeEach
    void setUp() {
        // Создаем моки для зависимостей
        mockRepository = Mockito.mock(Repository.class);
        mockAppHelperCustomer = Mockito.mock(AppHelper.class);

        // Инициализируем CustomerService с моками
        customerService = new CustomerService(mockRepository, mockAppHelperCustomer);
    }

    @Test
    void testAddCustomerSuccess() {
        // Подготовка: создать клиента и настроить заглушки
        Customer mockCustomer = new Customer(); // Предполагается, что у класса Customer есть конструктор по умолчанию
        when(mockAppHelperCustomer.create()).thenReturn(mockCustomer);

        // Выполняем метод add
        boolean result = customerService.add();

        // Проверка
        assertTrue(result);
        verify(mockRepository, times(1)).save(mockCustomer); // Убедиться, что метод save был вызван один раз
    }

    @Test
    void testAddCustomerFailureWhenCustomerIsNull() {
        // Настроить заглушку, чтобы create возвращал null
        when(mockAppHelperCustomer.create()).thenReturn(null);

        // Выполняем метод add
        boolean result = customerService.add();

        // Проверка
        assertFalse(result);
        verify(mockRepository, never()).save(any()); // Убедиться, что метод save не был вызван
    }

    @Test
    void testAddCustomerExceptionHandling() {
        // Подготовка: создать клиента и выбросить исключение при вызове save
        Customer mockCustomer = new Customer();
        when(mockAppHelperCustomer.create()).thenReturn(mockCustomer);
        doThrow(new RuntimeException("Save error")).when(mockRepository).save(mockCustomer);

        // Выполняем метод add
        boolean result = customerService.add();

        // Проверка
        assertFalse(result); // Ожидаем, что метод вернет false при возникновении исключения
    }

    @Test
    void testPrint() {
        // Подготовка: создать список клиентов и настроить заглушки
        List<Customer> mockCustomerList = List.of(new Customer(), new Customer());
        when(mockRepository.load()).thenReturn(mockCustomerList);
        when(mockAppHelperCustomer.printList(mockCustomerList)).thenReturn(true);

        // Выполняем метод print
        boolean result = customerService.print();

        // Проверка
        assertTrue(result);
        verify(mockRepository, times(1)).load(); // Убедиться, что метод load был вызван один раз
        verify(mockAppHelperCustomer, times(1)).printList(mockCustomerList); // Убедиться, что метод printList был вызван один раз
    }

    @Test
    void testList() {
        // Подготовка: создать список клиентов и настроить заглушки
        List<Customer> mockCustomerList = List.of(new Customer(), new Customer());
        when(mockRepository.load()).thenReturn(mockCustomerList);

        // Выполняем метод list
        List<Customer> result = customerService.list();

        // Проверка
        assertEquals(mockCustomerList, result);
        verify(mockRepository, times(1)).load(); // Убедиться, что метод load был вызван один раз
    }

    @Test
    void testEditCustomerSuccess() {
        // Подготовка: создаем существующего клиента
        Customer existingCustomer = new Customer();
        existingCustomer.setId(UUID.randomUUID()); // Задаем ID
        existingCustomer.setFirstName("John");
        existingCustomer.setLastName("Doe");
        existingCustomer.setEmail("john.doe@example.com");

        // Настройка: возвращаем список клиентов из репозитория
        List<Customer> mockCustomerList = new ArrayList<>();
        mockCustomerList.add(existingCustomer);
        when(mockRepository.load()).thenReturn(mockCustomerList);

        // Обновленный клиент, который вернется из хелпера
        // Используем тот же объект, чтобы гарантировать, что equals будет работать
        Customer updatedCustomer = existingCustomer;
        updatedCustomer.setEmail("john.updated@example.com"); // Обновляем email для теста

        // Настройка: возвращаем обновленного клиента
        when(mockAppHelperCustomer.update(mockCustomerList)).thenReturn(updatedCustomer);

        // Выполняем метод edit
        boolean result = customerService.edit();

        // Проверка: результат должен быть true
        assertTrue(result, "Expected edit to return true");

        // Проверка: метод update должен быть вызван с правильным индексом
        verify(mockRepository, times(1)).update(0, updatedCustomer);

        // Дополнительная проверка: убедиться, что обновленный клиент совпадает с ожидаемым
        List<Customer> updatedList = mockRepository.load();
        assertEquals(1, updatedList.size());
        assertEquals(updatedCustomer.getEmail(), updatedList.get(0).getEmail());
    }




    @Test
    void testEditCustomerFailureWhenUpdatedCustomerIsNull() {
        // Настроить заглушку, чтобы update возвращал null
        List<Customer> mockCustomerList = List.of(new Customer());
        when(mockRepository.load()).thenReturn(mockCustomerList);
        when(mockAppHelperCustomer.update(mockCustomerList)).thenReturn(null);

        // Выполняем метод edit
        boolean result = customerService.edit();

        // Проверка
        assertFalse(result);
        verify(mockRepository, never()).update(anyInt(), any()); // Убедиться, что метод update не был вызван
    }

    @Test
    void testEditCustomerNotFound() {
        // Подготовка: создать список клиентов и настроить заглушки
        Customer existingCustomer = new Customer();
        Customer updatedCustomer = new Customer(); // Этот клиент не будет найден

        List<Customer> mockCustomerList = List.of(existingCustomer);
        when(mockRepository.load()).thenReturn(mockCustomerList);
        when(mockAppHelperCustomer.update(mockCustomerList)).thenReturn(updatedCustomer);

        // Выполняем метод edit
        boolean result = customerService.edit();

        // Проверка
        assertFalse(result);
        verify(mockRepository, never()).update(anyInt(), any()); // Убедиться, что метод update не был вызван
    }

    @Test
    void testEditCustomerExceptionHandling() {
        // Подготовка: создать список клиентов и вызвать исключение при обновлении
        Customer existingCustomer = new Customer();
        Customer updatedCustomer = new Customer();

        List<Customer> mockCustomerList = List.of(existingCustomer);
        when(mockRepository.load()).thenReturn(mockCustomerList);
        when(mockAppHelperCustomer.update(mockCustomerList)).thenReturn(updatedCustomer);

        // Настройка: выбросить исключение при вызове update
        doThrow(new RuntimeException("Update error")).when(mockRepository).update(anyInt(), eq(updatedCustomer));

        // Выполняем метод edit
        boolean result = customerService.edit();

        // Проверка
        assertFalse(result); // Ожидаем, что метод вернет false при возникновении исключения
    }
}
