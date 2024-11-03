package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Repository;
import ee.ivkhkdev.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseServiceTest {

    private PurchaseService purchaseService;
    private AppHelper<Purchase> mockPurchaseAppHelper;
    private Repository<Purchase> mockRepository;

    @BeforeEach
    void setUp() {
        // Создаем моки для зависимостей
        mockPurchaseAppHelper = Mockito.mock(AppHelper.class);
        mockRepository = Mockito.mock(Repository.class);

        // Инициализируем PurchaseService с моками
        purchaseService = new PurchaseService(mockPurchaseAppHelper, mockRepository);
    }

    @Test
    void testAddPurchaseSuccess() {
        // Подготовка: создать Purchase и настроить заглушки
        Purchase mockPurchase = new Purchase(); // Предполагается, что у класса Purchase есть конструктор по умолчанию
        when(mockPurchaseAppHelper.create()).thenReturn(mockPurchase);

        // Выполняем метод add
        boolean result = purchaseService.add();

        // Проверка
        assertTrue(result);
        verify(mockRepository, times(1)).save(mockPurchase); // Убедиться, что метод save был вызван один раз
    }

    @Test
    void testAddPurchaseFailureWhenPurchaseIsNull() {
        // Настроить заглушку, чтобы create возвращал null
        when(mockPurchaseAppHelper.create()).thenReturn(null);

        // Выполняем метод add
        boolean result = purchaseService.add();

        // Проверка
        assertFalse(result);
        verify(mockRepository, never()).save(any()); // Убедиться, что метод save не был вызван
    }

    @Test
    void testAddPurchaseExceptionHandling() {
        // Подготовка: создать Purchase и выбросить исключение при вызове save
        Purchase mockPurchase = new Purchase();
        when(mockPurchaseAppHelper.create()).thenReturn(mockPurchase);
        doThrow(new RuntimeException("Save error")).when(mockRepository).save(mockPurchase);

        // Выполняем метод add
        boolean result = purchaseService.add();

        // Проверка
        assertFalse(result); // Ожидаем, что метод вернет false при возникновении исключения
    }

    @Test
    void testPrint() {
        // Подготовка: создать список покупок и настроить заглушки
        List<Purchase> mockPurchaseList = List.of(new Purchase());
        when(mockRepository.load()).thenReturn(mockPurchaseList);
        when(mockPurchaseAppHelper.printList(mockPurchaseList)).thenReturn(true);

        // Выполняем метод print
        boolean result = purchaseService.print();

        // Проверка
        assertTrue(result);
        verify(mockRepository, times(1)).load(); // Убедиться, что метод load был вызван один раз
        verify(mockPurchaseAppHelper, times(1)).printList(mockPurchaseList); // Убедиться, что метод printList был вызван один раз
    }

    @Test
    void testList() {
        // Подготовка: создать список покупок и настроить заглушки
        List<Purchase> mockPurchaseList = List.of(new Purchase());
        when(mockRepository.load()).thenReturn(mockPurchaseList);

        // Выполняем метод list
        List<Purchase> result = purchaseService.list();

        // Проверка
        assertEquals(mockPurchaseList, result);
        verify(mockRepository, times(1)).load(); // Убедиться, что метод load был вызван один раз
    }
}
