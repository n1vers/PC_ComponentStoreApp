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
}
