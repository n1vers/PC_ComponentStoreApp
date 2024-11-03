package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.interfaces.Repository;

import java.util.List;

public class CustomerService implements Service<Customer> {

    private Repository<Customer> repository;
    private AppHelper<Customer> appHelperCustomer;

    public CustomerService(Repository<Customer> repository, AppHelper<Customer> appHelperCustomer) {

        this.repository = repository;
        this.appHelperCustomer = appHelperCustomer;
    }

    @Override
    public boolean add() {
        Customer customer = appHelperCustomer.create();
        if (customer == null) return false;
        if (customer == null) return false;
        try {
            repository.save(customer);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean print() {
        return appHelperCustomer.printList(repository.load());
    }

    public List<Customer> list() {
        return repository.load();
    }
}
