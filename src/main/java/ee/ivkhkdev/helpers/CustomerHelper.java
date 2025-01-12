package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.entity.Customer;
import ee.ivkhkdev.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class CustomerHelper implements Helper<Customer> {
    @Autowired
    private  Input input;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Optional<Customer> create() {
        try {
            Customer customer = new Customer();
            System.out.print("Введите имя клиента: ");
            customer.setFirstName(input.nextLine());
            System.out.print("Введите фамилию клиента: ");
            customer.setLastName(input.nextLine());
            System.out.print("Введите email клиента: ");
            customer.setEmail(input.nextLine());
            System.out.print("Введите первоначальный баланс:");
            customer.setCash(Integer.parseInt(input.nextLine()));
            return Optional.of(customer);
        } catch (Exception e) {
            System.out.println("Ошибка при создании клиента: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean printList() {
        List<Customer> customers = customerRepository.findAll();
            if (customers.isEmpty()) return false;
            for (int i = 0; i < customers.size(); i++) {
                System.out.printf(Locale.ENGLISH,"%d. Имя: %s, Фамилия: %s, Email: %s, Баланс: %.2f $\n",
                        i + 1,
                        customers.get(i).getFirstName(),
                        customers.get(i).getLastName(),
                        customers.get(i).getEmail(),
                        customers.get(i).getCash()
                );
            }
            return true;
    }

    public boolean update(Customer customer) {
        try {
            System.out.print("Введите ID клиента для редактирования: ");
            Long id = Long.parseLong(input.nextLine());
            Optional<Customer> optionalCustomer = customerRepository.findById(id);
            if (optionalCustomer.isPresent()) {
                System.out.printf("Текущее имя: %s. Введите новое имя или нажмите Enter для пропуска: ", customer.getFirstName());
                String firstName = input.nextLine();
                if (!firstName.isEmpty()) {
                    customer.setFirstName(firstName);
                }
                System.out.printf("Текущая фамилия: %s. Введите новую фамилию или нажмите Enter для пропуска: ", customer.getLastName());
                String lastName = input.nextLine();
                if (!lastName.isEmpty()) {
                    customer.setLastName(lastName);
                }
                System.out.printf("Текущий email: %s. Введите новый email или нажмите Enter для пропуска: ", customer.getEmail());
                String email = input.nextLine();
                if (!email.isEmpty()) {
                    customer.setEmail(email);
                }
                System.out.printf(Locale.ENGLISH, "Текущий баланс: %.2f. Введите новый баланс или нажмите Enter для пропуска: ", customer.getCash());
                String cashInput = input.nextLine();
                if (!cashInput.isEmpty()) {
                    customer.setCash(Double.parseDouble(cashInput));
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении данных клиента: " + e.getMessage());
        }
        return false;
    }
}
