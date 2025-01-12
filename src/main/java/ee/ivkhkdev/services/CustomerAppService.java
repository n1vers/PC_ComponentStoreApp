package ee.ivkhkdev.services;

import ee.ivkhkdev.helpers.Helper;
import ee.ivkhkdev.entity.Customer;
import ee.ivkhkdev.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerAppService implements AppService<Customer> {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private Helper<Customer> helperCustomer;

    @Override
    public boolean add() {
        try {
            Optional<Customer> customer = helperCustomer.create();
            if (customer.isPresent()) {
                customerRepository.save(customer.get());
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean print() {
        return helperCustomer.printList();
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @Override
    public boolean edit() {
        try {

            Customer customer = helperCustomer.update();
            if (helperCustomer.update(customer)) {
                customerRepository.save(customer);
                System.out.println("Клиент успешно обновлен.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Ошибка при редактировании клиента: " + e.getMessage());
        }
        return false;
    }

}
