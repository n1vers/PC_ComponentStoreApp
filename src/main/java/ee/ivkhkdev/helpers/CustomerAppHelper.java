package ee.ivkhkdev.helpers;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Customer;

import java.util.List;

public class CustomerAppHelper implements AppHelper<Customer> {
    private final Input input;

    public CustomerAppHelper(Input input) {
        this.input = input;
    }

    @Override
    public Customer create() {
        Customer customer = new Customer();
        try {
            System.out.print("Введите имя клиента: ");
            customer.setFirstName(input.nextLine());
            System.out.print("Введите фамилию клиента: ");
            customer.setLastName(input.nextLine());
            System.out.print("Введите email клиента: ");
            customer.setEmail(input.nextLine());
            return customer;
        } catch (Exception e) {
            System.out.println("Ошибка при создании клиента: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean printList(List<Customer> customers) {
        try {
            if (customers.size() == 0) return false;
            for (int i = 0; i < customers.size(); i++) {
                System.out.printf("%d. Имя: %s, Фамилия: %s, Email: %s%n",
                        i + 1,
                        customers.get(i).getFirstName(),
                        customers.get(i).getLastName(),
                        customers.get(i).getEmail());
            }
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при выводе списка клиентов: " + e.getMessage());
            return false;
        }
    }

    public Customer update(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("Список клиентов пуст");
            return null;
        }
        this.printList(customers);

        System.out.print("Введите номер клиента для редактирования: ");
        int index;
        try {
            index = Integer.parseInt(input.nextLine()) - 1;
            Customer customer = customers.get(index); // Получаем клиента для редактирования

            // Здесь обновляем поля клиента
            System.out.printf("Текущее имя: %s. Введите новое имя или нажмите Enter, чтобы оставить без изменений: ", customer.getFirstName());
            String firstName = input.nextLine();
            if (!firstName.isEmpty()) {
                customer.setFirstName(firstName);
            }

            // Аналогично для других полей
            System.out.printf("Текущая фамилия: %s. Введите новую фамилию или нажмите Enter, чтобы оставить без изменений: ", customer.getLastName());
            String lastName = input.nextLine();
            if (!lastName.isEmpty()) {
                customer.setLastName(lastName);
            }

            System.out.printf("Текущий email: %s. Введите новый email или нажмите Enter, чтобы оставить без изменений: ", customer.getEmail());
            String email = input.nextLine();
            if (!email.isEmpty()) {
                customer.setEmail(email);
            }

            return customer; // Возвращаем обновленный объект
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении данных клиента: " + e.getMessage());
            return null;
        }
    }
}
