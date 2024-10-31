package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.model.Customer;

import java.util.List;

public class AppHelperCustomer implements AppHelper<Customer> {
    private final Input input;

    public AppHelperCustomer(Input input) {
        this.input = input;
    }

    @Override
    public Customer create(){
        Customer customer = new Customer();
        try {
            System.out.print("Введите имя клиента: ");
            customer.setFirstName(input.nextLine());
            System.out.print("Введите фамилию клиента: ");
            customer.setLastName(input.nextLine());
            System.out.print("Введите email клиента: ");
            customer.setEmail(input.nextLine());
            return customer;
        }catch (Exception e){
            System.out.println("Ошибка при создании клиента: " + e.getMessage());
            return null;
        }
    }
    @Override
    public boolean printList(List<Customer> customers) {
        try {
            if (customers.size() == 0) return false;
            for(int i = 0; i < customers.size(); i++){
                System.out.printf("%d. Имя: %s, Фамилия: %s, Email: %s%n",
                        i + 1,
                        customers.get(i).getFirstName(),
                        customers.get(i).getLastName(),
                        customers.get(i).getEmail());
            }
            return true;
        }catch (Exception e){
            System.out.println("Ошибка при выводе списка клиентов: " + e.getMessage());
            return false;
        }
    }
}
