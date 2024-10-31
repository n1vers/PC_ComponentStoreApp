package ee.ivkhkdev;

import ee.ivkhkdev.helpers.AppHelper;
import ee.ivkhkdev.helpers.AppHelperCategory;
import ee.ivkhkdev.helpers.AppHelperComponent;
import ee.ivkhkdev.helpers.AppHelperCustomer;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.repositories.Repository;
import ee.ivkhkdev.services.CategoryService;
import ee.ivkhkdev.services.ComponentService;
import ee.ivkhkdev.services.CustomerService;
import ee.ivkhkdev.repositories.Storage;

import java.util.List;
import java.util.Scanner;

public class App {
    private Input input;
    public List<Customer> customers;
    public List<Component> components;
    public List<Category> categories;

    private AppHelper appHelperComponent;
    private AppHelper appHelperCustomer;
    private AppHelper appHelperCategory;

    private Repository<Component> componentRepository;
    private Repository<Customer> customerRepository;
    private Repository<Category> categoryRepository;

    private ComponentService componentService;
    private CustomerService customerService;
    private CategoryService categoryService;

    public App() {
        componentRepository = new Storage<>("components.dat");
        customerRepository = new Storage<>("customers.dat");
        categoryRepository = new Storage<>("categories.dat");

        this.components = this.componentRepository.load();
        this.customers = this.customerRepository.load();
        this.categories = this.categoryRepository.load();

        this.input = new ConsoleInput(new Scanner(System.in));
        this.categoryService = new CategoryService(categories, categoryRepository, new AppHelperCategory(input));

        appHelperComponent = new AppHelperComponent(input,categoryService);
        appHelperCustomer = new AppHelperCustomer(input);
        appHelperCategory = new AppHelperCategory(input);

        componentService = new ComponentService(components, componentRepository,appHelperComponent);
        customerService = new CustomerService(customers,customerRepository,appHelperCustomer);
        categoryService = new CategoryService(categories, categoryRepository, appHelperCategory);
    }

    public void run() {
        boolean repeat = true;
        System.out.println("======= PC Component Store =========");
        do {
            System.out.println("Список задач:");
            System.out.println("0. Выйти из программы");
            System.out.println("1. Добавить компонент");
            System.out.println("2. Список компонентов");
            System.out.println("3. Добавить клиента");
            System.out.println("4. Список клиентов");
            System.out.println("5. Добавить категорию");
            System.out.println("6. Список категорий");
            System.out.print("Введите номер задачи: ");
            int task = Integer.parseInt(input.nextLine());
            switch (task) {
                case 0:
                    System.out.println("Выход из программы");
                    repeat = false;
                    break;
                case 1:
                    if (componentService.add()) {
                        System.out.println("Компонент добавлен");
                    } else {
                        System.out.println("Не удалось добавить компонент");
                    }
                    break;
                case 2:
                    if (componentService.print()) {
                        System.out.println("-----------Конец списка---------");
                    }
                    break;
                case 3:
                    if (customerService.add()) {
                        System.out.println("Клиент добавлен");
                    } else {
                        System.out.println("Не удалось добавить клиента");
                    }
                    break;
                case 4:
                    if (customerService.print()) {
                        System.out.println("-----------Конец списка---------");
                    }
                    break;
                case 5:
                    if (categoryService.add()) {
                        System.out.println("Категория добавлена");
                    } else {
                        System.out.println("Не удалось добавить категорию");
                    }
                    break;
                case 6:
                    if (categoryService.print()) {
                        System.out.println("-----------Конец списка категорий---------");
                    }
                    break;
                default:
                    System.out.println("Выберите номер из списка задач!");
                    break;
            }
            System.out.println("==============================");
        } while (repeat);
        System.out.println("До свидания! :)");
    }
}