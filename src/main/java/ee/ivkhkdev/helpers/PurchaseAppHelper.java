package ee.ivkhkdev.helpers;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.interfaces.AppService;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.model.Purchase;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class PurchaseAppHelper implements AppHelper<Purchase> {
    private final Input input;
    private final AppService<Component> componentAppService;
    private final AppService<Customer> customerAppService;

    public PurchaseAppHelper(Input input, AppService<Component> componentAppService, AppService<Customer> customerAppService) {
        this.input = input;
        this.componentAppService = componentAppService;
        this.customerAppService = customerAppService;
    }

    @Override
    public Purchase create() {
        if (!componentAppService.print()) {
            return null;
        }
        System.out.print("Выберите номер компонента для покупки: ");
        int componentNumber = Integer.parseInt(input.nextLine());
        Component component = componentAppService.list().get(componentNumber - 1);

        if (!customerAppService.print()) {
            return null;
        }
        System.out.print("Выберите номер покупателя: ");
        int customerNumber = Integer.parseInt(input.nextLine());
        Customer customer = customerAppService.list().get(customerNumber - 1);

//        double newBalance = customer.getCash() - component.getPrice();
//        if (newBalance < 0) {
//            System.out.println("Недостаточно средств на балансе клиента для этой покупки.");
//            return null;
//        }
//        customer.setCash(newBalance);

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
