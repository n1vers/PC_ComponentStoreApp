package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.helpers.CategoryAppHelper;
import ee.ivkhkdev.helpers.ComponentAppHelper;
import ee.ivkhkdev.helpers.CustomerAppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.interfaces.Repository;
import ee.ivkhkdev.model.Purchase;
import ee.ivkhkdev.services.CategoryService;
import ee.ivkhkdev.services.ComponentService;
import ee.ivkhkdev.services.CustomerService;
import ee.ivkhkdev.repositories.Storage;

import java.util.List;
import java.util.Scanner;

public class App {
    private final Input input;
    private final Service<Category> categoryService;
    private final Service<Component> componentService;
    private final Service<Customer> customerService;
    private final Service<Purchase> purchaseService;

    public App(Input input, Service<Category> categoryService, Service<Component> componentService,
               Service<Customer> customerService, Service<Purchase> purchaseService) {
        this.input = input;
        this.categoryService = categoryService;
        this.componentService = componentService;
        this.customerService = customerService;
        this.purchaseService = purchaseService;
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
            System.out.println("7. Купить товар");
            System.out.println("8. Список купленных товаров");
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
                case 7:
                    if (purchaseService.add()) {
                        System.out.println("Товар куплен");
                    } else {
                        System.out.println("Не удалось купить товар");
                    }
                    break;
                case 8:
                    if (purchaseService.print()) {
                        System.out.println("-----------Конец списка купленных товаров---------");
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