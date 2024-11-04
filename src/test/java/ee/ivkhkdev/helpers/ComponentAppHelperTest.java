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

import java.io.ByteArrayOutputStream;
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

        Category category = new Category();
        category.setCategoryName("OldCategory");
        component.setCategory(new ArrayList<>());
        component.getCategory().add(category);
        components.add(component);

        // Настройка для ввода данных
        // Измените порядок ввода, чтобы гарантировать, что вы получаете правильные значения
        when(inputMock.nextLine()).thenReturn("1", "NewBrand", "1", "1", "NewModel", "1500.00");

        // Настройка категорий
        Category newCategory = new Category();
        newCategory.setCategoryName("NewCategory");
        List<Category> categories = new ArrayList<>();
        categories.add(newCategory);
        when(categoryServiceMock.list()).thenReturn(categories);

        // Выполняем метод update
        Component updatedComponent = componentAppHelper.update(components);

        // Проверка, что компонент был обновлен
        assertNotNull(updatedComponent, "Updated component should not be null");
        assertEquals("NewBrand", updatedComponent.getBrand(), "Brand should be updated");
        assertEquals("NewModel", updatedComponent.getModel(), "Model should be updated");
        assertEquals(1500.00, updatedComponent.getPrice(), "Price should be updated");
        assertEquals(newCategory.getCategoryName(), updatedComponent.getCategory().get(0).getCategoryName(), "Category should be updated");
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
        assertNotNull(updatedComponent); // Компонент все еще существует
        assertEquals("Brand", updatedComponent.getBrand(), "Бренд не должен измениться");
        assertEquals("Model", updatedComponent.getModel(), "Модель не должна измениться");
        assertEquals(1000.00, updatedComponent.getPrice(), "Цена не должна измениться");
        assertTrue(outMock.toString().contains("Неверный номер компонента."));
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
        when(inputMock.nextLine()).thenReturn("1", "NewBrand", "n", "InvalidCategoryIndex");

        // Настройка категорий
        Category category = new Category();
        category.setCategoryName("Category");
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryServiceMock.list()).thenReturn(categories);

        // Выполняем метод update
        Component updatedComponent = componentAppHelper.update(components);

        // Проверка, что компонент не был обновлен
        assertNotNull(updatedComponent); // Компонент все еще существует
        assertEquals("Brand", updatedComponent.getBrand(), "Brand should not change"); // Бренд не должен измениться
        assertEquals("Model", updatedComponent.getModel(), "Model should not change"); // Модель не должна измениться
        assertEquals(1000.00, updatedComponent.getPrice(), "Price should not change"); // Цена не должна измениться
        assertEquals(1, updatedComponent.getCategory().size(), "There should still be one old category"); // Должен оставаться один старый категории
        assertTrue(outMock.toString().contains("Неверный номер категории. Категория не изменена."));
    }



}
