package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.repositories.PurchaseRepository;
import ee.ivkhkdev.services.AppService;
import ee.ivkhkdev.entity.Component;
import ee.ivkhkdev.entity.Customer;
import ee.ivkhkdev.entity.Purchase;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@org.springframework.stereotype.Component
public class PurchaseHelper implements Helper<Purchase> {
    @Autowired
    private  Input input;
    @Autowired
    private  AppService<Component> componentAppService;
    @Autowired
    private  AppService<Customer> customerAppService;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public Optional<Purchase> create() {
        System.out.print("Выберите номер компонента для покупки: ");
        int componentNumber = Integer.parseInt(input.nextLine());
        Component component = componentAppService.list().get(componentNumber - 1);
        System.out.print("Выберите номер покупателя: ");
        int customerNumber = Integer.parseInt(input.nextLine());
        Customer customer = customerAppService.list().get(customerNumber - 1);

        Purchase purchase = new Purchase();

        purchase.setComponent(component);
        purchase.setCustomer(customer);
        purchase.setPurchaseDate(LocalDate.now());

        return Optional.of(purchase);
    }

    @Override
    public boolean update(Purchase purchase) {
        return false;
    }

    @Override
    public boolean printList() {
        List<Purchase> purchases = purchaseRepository.findAll();
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

}
