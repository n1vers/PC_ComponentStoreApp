package ee.ivkhkdev.helpers;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.interfaces.Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ComponentAppHelperTest {
    Input inputMock;
    AppHelper<Component> componentAppHelper;
    Service<Category> categoryServiceMock;
    PrintStream defaultOut = System.out;
    ByteArrayOutputStream outMock;

    @BeforeEach
    void setUp() {
        inputMock = Mockito.mock(Input.class);
        categoryServiceMock = Mockito.mock(Service.class);
        componentAppHelper = new ComponentAppHelper(inputMock, categoryServiceMock);
        outMock = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outMock));
    }

    @AfterEach
    void tearDown() {
        inputMock = null;
        categoryServiceMock = null;
        componentAppHelper = null;
        System.setOut(defaultOut);
        outMock = null;
    }

    @Test
    void create() {
        // Настройка заглушек для ввода данных
        when(inputMock.nextLine()).thenReturn("ASUS", "1", "ROG Strix", "15000.00");

        // Настройка категории
        Category category = new Category();
        category.setCategoryName("Видеокарты");
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryServiceMock.list()).thenReturn(categories);

        // Выполняем метод create
        Component actual = componentAppHelper.create();

        // Ожидаемый результат
        Component expected = new Component();
        expected.setBrand("ASUS");
        expected.setModel("ROG Strix");
        expected.setPrice(15000.00);
        expected.getCategory().add(category);

        // Проверка, что созданный компонент соответствует ожиданиям
        assertEquals(expected.getBrand(), actual.getBrand());
        assertEquals(expected.getModel(), actual.getModel());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCategory().get(0).getCategoryName(), actual.getCategory().get(0).getCategoryName());
    }


    @Test
    void printList() {
        // Создаем компонент и задаем его свойства
        Component component = new Component();
        component.setBrand("ASUS");
        component.setModel("ROG Strix");
        component.setPrice(15000.00);

        // Создаем категорию и добавляем ее к компоненту
        Category category = new Category();
        category.setCategoryName("Видеокарты");
        component.getCategory().add(category);

        // Создаем список и добавляем компонент
        List<Component> components = new ArrayList<>();
        components.add(component);

        // Выполняем метод printList
        boolean result = componentAppHelper.printList(components);

        // Проверка, что результат верный
        assertTrue(result); // Ожидаем, что метод возвращает true

        // Ожидаемая строка вывода
        String expectedString = "1. Бренд: ASUS, Модель: ROG Strix, Категория: Видеокарты, Цена: 15000.00";

        // Сравниваем фактический вывод с ожидаемым
        String actualOutput = outMock.toString();
        System.out.println("Фактический вывод: " + actualOutput); // Добавьте это для отладки
        assertTrue(actualOutput.contains(expectedString), "Output should contain: " + expectedString);
    }

    @Test
    void printListEmpty() {
        // Проверка метода printList с пустым списком
        List<Component> components = new ArrayList<>();
        boolean result = componentAppHelper.printList(components);
        assertFalse(result); // Ожидаем, что метод вернет false для пустого списка
    }

    @Test
    void testUpdateWithValidInput() {
        // Создаем список компонентов
        List<Component> components = new ArrayList<>();
        Component component = new Component();
        component.setBrand("OldBrand");
        component.setModel("OldModel");
        component.setPrice(1000.00);
        component.setCategory(new ArrayList<>());

        components.add(component);

        // Создание и установка категорий
        Category category1 = new Category();
        category1.setCategoryName("Category 1");
        Category category2 = new Category();
        category2.setCategoryName("Category 2");

        // Настройка категорий
        when(categoryServiceMock.list()).thenReturn(List.of(category1, category2));

        // Настройка ввода
        when(inputMock.nextLine()).thenReturn("1") // Выбор компонента
                .thenReturn("NewBrand") // Новый бренд
                .thenReturn("n") // Отказ от создания новой категории
                .thenReturn("2") // Выбор существующей категории, теперь выбираем "Category 2"
                .thenReturn("NewModel") // Новая модель
                .thenReturn("2000.00"); // Новая цена

        // Выполняем метод update
        Component updatedComponent = componentAppHelper.update(components);

        // Проверяем, что компонент обновлен корректно
        assertNotNull(updatedComponent, "Updated component should not be null");
        assertEquals("NewBrand", updatedComponent.getBrand(), "Brand should be updated");
        assertEquals("NewModel", updatedComponent.getModel(), "Model should be updated");
        assertEquals(2000.00, updatedComponent.getPrice(), "Price should be updated");
        assertEquals(1, updatedComponent.getCategory().size(), "There should be one category");
        assertEquals("Category 2", updatedComponent.getCategory().get(0).getCategoryName(), "Category should be updated");
    }


    @Test
    void testUpdateEmptyComponentsList() {
        // Проверка на пустой список компонентов
        List<Component> components = new ArrayList<>();

        // Выполняем метод update
        Component updatedComponent = componentAppHelper.update(components);

        // Проверка, что обновленный компонент равен null
        assertNull(updatedComponent);
        assertTrue(outMock.toString().contains("Список компонентов пуст."));
    }

    @Test
    void testUpdateInvalidIndex() {
        // Создаем пустой список компонентов
        List<Component> components = new ArrayList<>();

        // Настройка для ввода данных с неверным индексом
        when(inputMock.nextLine()).thenReturn("2"); // Некорректный индекс

        // Выполняем метод update
        Component updatedComponent = componentAppHelper.update(components);

        // Проверка, что метод возвращает null для пустого списка
        assertNull(updatedComponent, "Метод должен вернуть null для пустого списка");

        // Добавление компонентов и повторный тест
        Component component = new Component();
        component.setBrand("Brand");
        component.setModel("Model");
        component.setPrice(1000.00);
        components.add(component); // Список теперь содержит один компонент

        // Настройка для ввода данных с неверным индексом
        when(inputMock.nextLine()).thenReturn("2"); // Некорректный индекс

        // Выполняем метод update
        updatedComponent = componentAppHelper.update(components);

        // Проверка, что компонент не был обновлен
        assertNull(updatedComponent, "Метод должен вернуть null при неверном индексе");
    }

    @Test
    void testUpdateWithException() {
        // Создаем список компонентов
        List<Component> components = new ArrayList<>();
        Component component = new Component();
        component.setBrand("Brand");
        component.setModel("Model");
        component.setPrice(1000.00);

        // Создание и установка категории
        Category defaultCategory = new Category();
        defaultCategory.setCategoryName("DefaultCategory");
        component.setCategory(new ArrayList<>());
        component.getCategory().add(defaultCategory);

        components.add(component);

        // Настройка для ввода данных
        when(inputMock.nextLine())
                .thenReturn("1") // Выбор компонента
                .thenReturn("NewBrand") // Новый бренд
                .thenReturn("n") // Отказ от создания новой категории
                .thenReturn("-1") // Неверный индекс категории
                .thenReturn("") // Пустая строка, чтобы оставить модель без изменений
                .thenReturn("2000.00"); // Новая цена

        // Настройка категорий
        Category category = new Category();
        category.setCategoryName("Category");
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryServiceMock.list()).thenReturn(categories);

        // Выполняем метод update
        Component updatedComponent = componentAppHelper.update(components);

        // Проверка, что компонент был обновлен частично
        assertNotNull(updatedComponent, "Updated component should not be null"); // Проверка на null
        assertEquals("NewBrand", updatedComponent.getBrand(), "Brand should be updated"); // Бренд должен измениться
        assertEquals("Model", updatedComponent.getModel(), "Model should not change"); // Модель не должна измениться
        assertEquals(2000.00, updatedComponent.getPrice(), "Price should be updated"); // Цена должна измениться на 2000.00
        assertEquals(1, updatedComponent.getCategory().size(), "There should still be one old category"); // Должна оставаться одна старая категория
        assertTrue(outMock.toString().contains("Неверный номер категории. Категория не изменена."), "Output should contain the error message for invalid category index"); // Проверка на сообщение об ошибке
    }
}
