package ee.ivkhkdev;

import ee.ivkhkdev.helpers.CategoryAppHelper;
import ee.ivkhkdev.helpers.ComponentAppHelper;
import ee.ivkhkdev.helpers.CustomerAppHelper;
import ee.ivkhkdev.helpers.PurchaseAppHelper;
import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.Repository;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.model.Purchase;
import ee.ivkhkdev.repositories.Storage;
import ee.ivkhkdev.services.CategoryService;
import ee.ivkhkdev.services.ComponentService;
import ee.ivkhkdev.services.CustomerService;
import ee.ivkhkdev.services.PurchaseService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Repository<Category> categoryRepository = new Storage<>("categories");
        Repository<Component> componentRepository = new Storage<>("components");
        Repository<Customer> customerRepository = new Storage<>("customers");
        Repository<Purchase> purchaseRepository = new Storage<>("purchases");
        Input input = new ConsoleInput(new Scanner(System.in));
        AppHelper<Category> categoryAppHelper = new CategoryAppHelper(input);
        Service<Category> categoryService = new CategoryService(categoryRepository, categoryAppHelper);
        AppHelper<Component> componentAppHelper = new ComponentAppHelper(input, categoryService);
        AppHelper<Customer> customerAppHelper = new CustomerAppHelper(input);
        Service<Component> componentService = new ComponentService(componentRepository, componentAppHelper);
        Service<Customer> customerService = new CustomerService(customerRepository, customerAppHelper);
        AppHelper<Purchase> purchaseAppHelper = new PurchaseAppHelper(input, componentService, customerService);
        Service<Purchase> purchaseService = new PurchaseService(purchaseAppHelper, purchaseRepository);

        App app = new App(input, categoryService, componentService, customerService, purchaseService);
        app.run();
    }
}