package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.repositories.PurchaseRepository;
import ee.ivkhkdev.services.AppService;
import ee.ivkhkdev.entity.Customer;
import ee.ivkhkdev.entity.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class PurchaseHelperImpl implements PurchaseHelper {

    @Autowired
    private Input input;

    @Autowired
    private AppService<Component> componentAppService;

    @Autowired
    private AppService<Customer> customerAppService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public Optional<Purchase> create() {
        try {
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
        } catch (Exception e) {
            System.out.println("Ошибка при создании покупки: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Purchase> update(Purchase purchase) {
        try {
            // Реализуем обновление информации о покупке, если требуется
            // Но пока метод не реализован в вашем примере, поэтому возвращаем Optional.empty()
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении покупки: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean printList(List<Purchase> purchases, boolean enableAll) {
        try {
            if (purchases.isEmpty()) {
                System.out.println("Нет покупок.");
                return false;
            }
            for (int i = 0; i < purchases.size(); i++) {
                Purchase purchase = purchases.get(i);
                System.out.printf(Locale.ENGLISH,
                        "%d. Компонент: %s %s %.1f $, Покупатель: %s %s, Дата покупки: %s%n",
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
        } catch (Exception e) {
            System.out.println("Ошибка при выводе списка покупок: " + e.getMessage());
            return false;
        }
    }
}

