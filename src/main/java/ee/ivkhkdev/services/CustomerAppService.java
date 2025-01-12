package ee.ivkhkdev.services;

import ee.ivkhkdev.helpers.CustomerHelper;
import ee.ivkhkdev.helpers.Helper;
import ee.ivkhkdev.entity.Customer;
import ee.ivkhkdev.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerAppService implements AppService<Customer> {

    private final CustomerRepository customerRepository;
    private final CustomerHelper customerHelper;

    @Autowired
    public CustomerAppService(CustomerRepository customerRepository, CustomerHelper customerHelper) {
        this.customerRepository = customerRepository;
        this.customerHelper = customerHelper;
    }

    @Override
    public boolean add() {
        Optional<Customer> optionalCustomer = customerHelper.create();
        if (optionalCustomer.isEmpty()) {
            return false;
        }
        customerRepository.save(optionalCustomer.get());
        return true;
    }

    @Override
    public boolean print() {
        return customerHelper.printList(customerRepository.findAll(), true);
    }

    @Override
    public boolean edit() {
        Optional<Customer> optionalModifyCustomer = getCustomerForModify();
        if (optionalModifyCustomer.isEmpty()) {
            return false;
        }
        Optional<Customer> optionalModifiedCustomer = customerHelper.update(optionalModifyCustomer.get());
        customerRepository.save(optionalModifiedCustomer.get());
        System.out.println("Клиент успешно обновлен.");
        return true;
    }

    private Optional<Customer> getCustomerForModify() {
        Optional<Long> optionalCustomerId = customerHelper.getIdModifyCustomer(customerRepository.findAll(), true);
        if (optionalCustomerId.isEmpty()) {
            return Optional.empty();
        }
        return customerRepository.findById(optionalCustomerId.get());
    }
}
