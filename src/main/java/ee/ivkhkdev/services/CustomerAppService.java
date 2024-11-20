package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.AppService;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.interfaces.AppRepository;

import java.util.List;

public class CustomerAppService implements AppService<Customer> {

    private AppRepository<Customer> appRepository;
    private AppHelper<Customer> appHelperCustomer;

    public CustomerAppService(AppRepository<Customer> appRepository, AppHelper<Customer> appHelperCustomer) {

        this.appRepository = appRepository;
        this.appHelperCustomer = appHelperCustomer;
    }

    @Override
    public boolean add() {
        Customer customer = appHelperCustomer.create();
        if (customer == null) return false;
        try {
            appRepository.save(customer);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean print() {
        return appHelperCustomer.printList(appRepository.load());
    }

    public List<Customer> list() {
        return appRepository.load();
    }

    @Override
    public boolean edit() {
        List<Customer> customers = appRepository.load();
        Customer updatedCustomer = appHelperCustomer.update(customers);
        if (updatedCustomer == null) {
            return false;
        }
        try {
            int index = customers.indexOf(updatedCustomer);
            if (index != -1) {
                appRepository.update(index, updatedCustomer);
                return true;
            } else {
                System.out.println("Клиент не найден в списке.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении клиента: " + e.getMessage());
            return false;
        }
    }

}
