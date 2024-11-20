package ee.ivkhkdev.helpers;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryAppHelperTest {
    Input inputMock;
    AppHelper<Category> categoryAppHelper;
    PrintStream defaultOut = System.out;
    ByteArrayOutputStream outMock;

    @BeforeEach
    void setUp() {
        inputMock = Mockito.mock(Input.class);
        categoryAppHelper = new CategoryAppHelper(inputMock);
        outMock = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outMock));
    }

    @AfterEach
    void tearDown() {
        inputMock = null;
        System.setOut(defaultOut);
        outMock = null;
    }

    @Test
    void create() {
        // Настройка заглушки, чтобы nextLine возвращал название категории
        when(inputMock.nextLine()).thenReturn("Электроника");

        // Выполняем метод create
        Category actual = categoryAppHelper.create();

        // Ожидаемый результат
        Category expected = new Category();
        expected.setCategoryName("Электроника");

        // Проверка, что созданная категория имеет ожидаемое название
        assertEquals(expected.getCategoryName(), actual.getCategoryName());
    }

    @Test
    void printList() {
        // Подготовка списка категорий
        Category category = new Category();
        category.setCategoryName("Электроника");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        // Выполняем метод printList
        boolean result = categoryAppHelper.printList(categories);

        // Проверка, что результат верный
        assertTrue(result);

        // Ожидаемая строка вывода
        String expectedString = "1. Электроника";
        assertTrue(outMock.toString().contains(expectedString));
    }

    @Test
    void printListEmpty() {
        // Проверка метода printList с пустым списком
        List<Category> categories = new ArrayList<>();
        boolean result = categoryAppHelper.printList(categories);
        assertFalse(result); // Ожидаем, что метод вернет false для пустого списка
    }
}
