package ee.ivkhkdev;

import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.AppService;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {
    private final Input input;
    private final AppService<Category> categoryAppService;
    private final AppService<Component> componentAppService;
    private final AppService<Customer> customerAppService;
    private final AppService<Purchase> purchaseAppService;

    @Autowired
    public App(Input input, AppService<Category> categoryAppService, AppService<Component> componentAppService,
               AppService<Customer> customerAppService, AppService<Purchase> purchaseAppService) {
        this.input = input;
        this.categoryAppService = categoryAppService;
        this.componentAppService = componentAppService;
        this.customerAppService = customerAppService;
        this.purchaseAppService = purchaseAppService;
    }
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        boolean repeat = true;
        System.out.println("======= PC Component Store =========");
        do {
            System.out.println("Список задач:");
            System.out.println("0. Выйти из программы");
            System.out.println("1. Добавить компонент");
            System.out.println("2. Список компонентов");
            System.out.println("3. Редактировать компонент");
            System.out.println("4. Добавить клиента");
            System.out.println("5. Список клиентов");
            System.out.println("6. Редактировать клиента");
            System.out.println("7. Добавить категорию");
            System.out.println("8. Список категорий");
            System.out.println("9. Купить товар");
            System.out.println("10. Список купленных товаров");
            System.out.print("Введите номер задачи: ");
            int task = Integer.parseInt(input.nextLine());
            switch (task) {
                case 0:
                    System.out.println("Выход из программы");
                    repeat = false;
                    break;
                case 1:
                    if (componentAppService.add()) {
                        System.out.println("Компонент добавлен");
                    } else {
                        System.out.println("Не удалось добавить компонент");
                    }
                    break;
                case 2:
                    if (componentAppService.print()) {
                        System.out.println("-----------Конец списка---------");
                    }
                    break;
                case 3:
                    if (componentAppService.edit()){
                        System.out.println("Товар успешно изменен");
                    }  else{
                        System.out.println("Товар изменить не удалось");
                    }
                    break;
                case 4:
                    if (customerAppService.add()) {
                        System.out.println("Клиент добавлен");
                    } else {
                        System.out.println("Не удалось добавить клиента");
                    }
                    break;
                case 5:
                    if (customerAppService.print()) {
                        System.out.println("-----------Конец списка---------");
                    }
                    break;
                case 6:
                    if (customerAppService.edit()) {
                        System.out.println("Клиент успешно изменен");
                    } else {
                        System.out.println("Клиента изменить не удалось");
                    }
                    break;
                case 7:
                    if (categoryAppService.add()) {
                        System.out.println("Категория добавлена");
                    } else {
                        System.out.println("Не удалось добавить категорию");
                    }
                    break;
                case 8:
                    if (categoryAppService.print()) {
                        System.out.println("-----------Конец списка категорий---------");
                    }
                    break;
                case 9:
                    if (purchaseAppService.add()) {
                        System.out.println("Товар куплен");
                    } else {
                        System.out.println("Не удалось купить товар");
                    }
                    break;
                case 10:
                    if (purchaseAppService.print()) {
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