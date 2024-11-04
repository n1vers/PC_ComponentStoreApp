package ee.ivkhkdev.helpers;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.model.Purchase;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class PurchaseAppHelper implements AppHelper<Purchase> {
    private final Input input;
    private final Service<Component> componentService;
    private final Service<Customer> customerService;

    public PurchaseAppHelper(Input input, Service<Component> componentService, Service<Customer> customerService) {
        this.input = input;
        this.componentService = componentService;
        this.customerService = customerService;
    }

    @Override
    public Purchase create() {
        if (!componentService.print()) {
            return null;
        }
        System.out.print("Выберите номер компонента для покупки: ");
        int componentNumber = Integer.parseInt(input.nextLine());
        Component component = componentService.list().get(componentNumber - 1);

        if (!customerService.print()) {
            return null;
        }
        System.out.print("Выберите номер покупателя: ");
        int customerNumber = Integer.parseInt(input.nextLine());
        Customer customer = customerService.list().get(customerNumber - 1);

        Purchase purchase = new Purchase();
        purchase.setComponent(component);
        purchase.setCustomer(customer);
        purchase.setPurchaseDate(LocalDate.now());
        return purchase;
    }

    @Override
    public boolean printList(List<Purchase> purchases) {
        if (purchases.isEmpty()) {
            System.out.println("Нет покупок.");
            return false;
        }
        for (int i = 0; i < purchases.size(); i++) {
            Purchase purchase = purchases.get(i);
            System.out.printf(Locale.ENGLISH, "%d. Компонент: %s %s %.1f $, Покупатель: %s %s, Дата покупки: %s%n",
                    i + 1,
                    purchase.getComponent().getBrand(),
                    purchase.getComponent().getModel(),
                    purchase.getComponent().getPrice(),
                    purchase.getCustomer().getFirstName(),
                    purchase.getCustomer().getLastName(),
                    purchase.getPurchaseDate()
            );
        }
        return true;
    }
    @Override
    public Purchase update(List<Purchase> purchases) {
        return null;
    }

}
