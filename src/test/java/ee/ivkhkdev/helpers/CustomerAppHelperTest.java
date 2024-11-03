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
}
