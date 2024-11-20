package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.interfaces.AppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
public class CategoryAppServiceTest {

    private CategoryAppService categoryService;
    private AppRepository<Category> mockAppRepository;
    private AppHelper<Category> mockAppHelperCategory;

    @BeforeEach
    void setUp() {
        // Создаем моки для зависимостей
        mockAppRepository = Mockito.mock(AppRepository.class);
        mockAppHelperCategory = Mockito.mock(AppHelper.class);

        // Инициализируем CategoryService с моками
        categoryService = new CategoryAppService(mockAppRepository, mockAppHelperCategory);
    }

    @Test
    void testAddCategorySuccess() {
        // Подготовка: создать категорию и настроить заглушки
        Category mockCategory = new Category(); // Предполагается, что у класса Category есть конструктор по умолчанию
        when(mockAppHelperCategory.create()).thenReturn(mockCategory);

        // Выполняем метод add
        boolean result = categoryService.add();

        // Проверка
        assertTrue(result);
        verify(mockAppRepository, times(1)).save(mockCategory); // Убедиться, что метод save был вызван один раз
    }

    @Test
    void testAddCategoryFailureWhenCategoryIsNull() {
        // Настроить заглушку, чтобы create возвращал null
        when(mockAppHelperCategory.create()).thenReturn(null);

        // Выполняем метод add
        boolean result = categoryService.add();

        // Проверка
        assertFalse(result);
        verify(mockAppRepository, never()).save(any()); // Убедиться, что метод save не был вызван
    }

    @Test
    void testAddCategoryExceptionHandling() {
        // Подготовка: создать категорию и выбросить исключение при вызове save
        Category mockCategory = new Category();
        when(mockAppHelperCategory.create()).thenReturn(mockCategory);
        doThrow(new RuntimeException("Save error")).when(mockAppRepository).save(mockCategory);

        // Выполняем метод add
        boolean result = categoryService.add();

        // Проверка
        assertFalse(result); // Ожидаем, что метод вернет false при возникновении исключения
    }

    @Test
    void testPrint() {
        // Подготовка: создать список категорий и настроить заглушки
        List<Category> mockCategoryList = List.of(new Category(), new Category());
        when(mockAppRepository.load()).thenReturn(mockCategoryList);
        when(mockAppHelperCategory.printList(mockCategoryList)).thenReturn(true);

        // Выполняем метод print
        boolean result = categoryService.print();

        // Проверка
        assertTrue(result);
        verify(mockAppRepository, times(1)).load(); // Убедиться, что метод load был вызван один раз
        verify(mockAppHelperCategory, times(1)).printList(mockCategoryList); // Убедиться, что метод printList был вызван один раз
    }

    @Test
    void testList() {
        // Подготовка: создать список категорий и настроить заглушки
        List<Category> mockCategoryList = List.of(new Category(), new Category());
        when(mockAppRepository.load()).thenReturn(mockCategoryList);

        // Выполняем метод list
        List<Category> result = categoryService.list();

        // Проверка
        assertEquals(mockCategoryList, result);
        verify(mockAppRepository, times(1)).load(); // Убедиться, что метод load был вызван один раз
    }
}
