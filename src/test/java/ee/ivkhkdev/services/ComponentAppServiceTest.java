package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.interfaces.AppRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ComponentAppServiceTest {

    private AppHelper<Component> appHelperComponent;
    private AppRepository<Component> appRepository;
    private ComponentAppService componentService;

    @BeforeEach
    void setUp() {
        appHelperComponent = Mockito.mock(AppHelper.class);
        appRepository = Mockito.mock(AppRepository.class);
        componentService = new ComponentAppService(appRepository, appHelperComponent);
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
        assertTrue(result,"Edit operation should be successful");
        verify(appRepository, times(1)).save(mockComponent); // Убедиться, что метод save был вызван один раз
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
        verify(appRepository, never()).save(any()); // Убедиться, что метод save не был вызван
    }

    @Test
    public void testAdd_ExceptionHandling() {
        // Подготовка: создать компонент и выбросить исключение при вызове save
        Component mockComponent = new Component();
        when(appHelperComponent.create()).thenReturn(mockComponent);
        doThrow(new RuntimeException("Save error")).when(appRepository).save(mockComponent);

        // Выполняем метод add
        boolean result = componentService.add();

        // Проверка
        assertFalse(result); // Ожидаем, что метод вернет false при возникновении исключения
    }

    @Test
    public void testPrint() {
        // Подготовка: создать список компонентов и настроить заглушки
        List<Component> mockComponentList = List.of(new Component(), new Component());
        when(appRepository.load()).thenReturn(mockComponentList);
        when(appHelperComponent.printList(mockComponentList)).thenReturn(true);

        // Выполняем метод print
        boolean result = componentService.print();

        // Проверка
        assertTrue(result);
        verify(appRepository, times(1)).load(); // Убедиться, что метод load был вызван один раз
        verify(appHelperComponent, times(1)).printList(mockComponentList); // Убедиться, что метод printList был вызван один раз
    }

    @Test
    public void testList() {
        // Подготовка: создать список компонентов и настроить заглушки
        List<Component> mockComponentList = List.of(new Component(), new Component());
        when(appRepository.load()).thenReturn(mockComponentList);

        // Выполняем метод list
        List<Component> result = componentService.list();

        // Проверка
        assertEquals(mockComponentList, result);
        verify(appRepository, times(1)).load(); // Убедиться, что метод load был вызван один раз
    }
    @Test
    void testEdit_SuccessfulUpdate() {
        // Подготовка: создание компонента
        Component existingComponent = new Component();
        existingComponent.setBrand("Old Brand");
        existingComponent.setModel("Old Model");
        existingComponent.setPrice(100.0);

        // Настройка заглушек
        when(appRepository.load()).thenReturn(List.of(existingComponent));

        // Настройка поведения appHelper для обновления компонента
        when(appHelperComponent.update(anyList())).thenAnswer(invocation -> {
            List<Component> components = invocation.getArgument(0);
            // Мы возвращаем существующий компонент, чтобы он был найден
            return components.get(0); // Возвращаем первый компонент для обновления
        });

        // Выполнение метода edit
        boolean result = componentService.edit();

        // Проверка
        assertTrue(result);
        // Убедитесь, что метод update был вызван с правильным компонентом
        verify(appRepository, times(1)).update(0, existingComponent); // Проверка вызова update
    }

    @Test
    void testEdit_ComponentNotFound() {
        // Подготовка: создать список компонентов и настроить заглушки
        Component existingComponent = new Component();
        existingComponent.setBrand("Old Brand");

        // Настройка заглушек
        when(appRepository.load()).thenReturn(List.of(existingComponent)); // Возвращаем существующий компонент
        when(appHelperComponent.update(anyList())).thenReturn(new Component()); // Возвращаем новый компонент, который не совпадает с существующим

        // Выполняем метод edit
        boolean result = componentService.edit();

        // Проверка
        assertFalse(result);
        verify(appRepository, never()).update(anyInt(), any()); // Убедиться, что метод update не был вызван
    }

    @Test
    void testEdit_UpdateReturnsNull() {
        // Подготовка: создать компонент и настроить заглушки
        Component existingComponent = new Component();
        existingComponent.setBrand("Old Brand");

        // Настройка заглушек
        when(appRepository.load()).thenReturn(List.of(existingComponent)); // Возвращаем существующий компонент
        when(appHelperComponent.update(anyList())).thenReturn(null); // Возвращаем null

        // Выполняем метод edit
        boolean result = componentService.edit();

        // Проверка
        assertFalse(result); // Ожидаем, что метод вернет false
        verify(appRepository, never()).update(anyInt(), any()); // Убедиться, что метод update не был вызван
    }
}
