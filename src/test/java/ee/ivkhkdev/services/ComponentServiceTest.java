package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.interfaces.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComponentServiceTest {

    private AppHelper<Component> appHelperComponent;
    private Repository<Component> repository;
    private ComponentService componentService;

    @BeforeEach
    void setUp() {
        appHelperComponent = Mockito.mock(AppHelper.class);
        repository = Mockito.mock(Repository.class);
        componentService = new ComponentService(repository, appHelperComponent);
    }

    @AfterEach
    void tearDown() {
        // Очистка после каждого теста, если необходимо
    }

    @Test
    void testAdd_SuccessfulAdd() {
        // Подготовка: создать компонент и настроить заглушки
        Component mockComponent = new Component(); // Предполагается, что у класса Component есть конструктор по умолчанию
        when(appHelperComponent.create()).thenReturn(mockComponent);

        // Выполняем метод add
        boolean result = componentService.add();

        // Проверка
        assertTrue(result);
        verify(repository, times(1)).save(mockComponent); // Убедиться, что метод save был вызван один раз
    }

    @Test
    public void testAdd_CreateReturnsNull() {
        // Настроить заглушку, чтобы create возвращал null
        when(appHelperComponent.create()).thenReturn(null);

        // Выполняем метод add
        boolean result = componentService.add();
        List<Component> resultListComponent = componentService.list();

        // Проверка
        assertFalse(result);
        assertTrue(resultListComponent.isEmpty()); // Проверяем, что список компонентов пуст
        verify(repository, never()).save(any()); // Убедиться, что метод save не был вызван
    }

    @Test
    public void testAdd_ExceptionHandling() {
        // Подготовка: создать компонент и выбросить исключение при вызове save
        Component mockComponent = new Component();
        when(appHelperComponent.create()).thenReturn(mockComponent);
        doThrow(new RuntimeException("Save error")).when(repository).save(mockComponent);

        // Выполняем метод add
        boolean result = componentService.add();

        // Проверка
        assertFalse(result); // Ожидаем, что метод вернет false при возникновении исключения
    }

    @Test
    public void testPrint() {
        // Подготовка: создать список компонентов и настроить заглушки
        List<Component> mockComponentList = List.of(new Component(), new Component());
        when(repository.load()).thenReturn(mockComponentList);
        when(appHelperComponent.printList(mockComponentList)).thenReturn(true);

        // Выполняем метод print
        boolean result = componentService.print();

        // Проверка
        assertTrue(result);
        verify(repository, times(1)).load(); // Убедиться, что метод load был вызван один раз
        verify(appHelperComponent, times(1)).printList(mockComponentList); // Убедиться, что метод printList был вызван один раз
    }

    @Test
    public void testList() {
        // Подготовка: создать список компонентов и настроить заглушки
        List<Component> mockComponentList = List.of(new Component(), new Component());
        when(repository.load()).thenReturn(mockComponentList);

        // Выполняем метод list
        List<Component> result = componentService.list();

        // Проверка
        assertEquals(mockComponentList, result);
        verify(repository, times(1)).load(); // Убедиться, что метод load был вызван один раз
    }
}
