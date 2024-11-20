package ee.ivkhkdev.configuration;

import ee.ivkhkdev.helpers.CategoryAppHelper;
import ee.ivkhkdev.helpers.ComponentAppHelper;
import ee.ivkhkdev.helpers.CustomerAppHelper;
import ee.ivkhkdev.helpers.PurchaseAppHelper;
import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.AppRepository;
import ee.ivkhkdev.interfaces.AppService;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.model.Customer;
import ee.ivkhkdev.model.Purchase;
import ee.ivkhkdev.repositories.Storage;
import ee.ivkhkdev.services.CategoryAppService;
import ee.ivkhkdev.services.ComponentAppService;
import ee.ivkhkdev.services.CustomerAppService;
import ee.ivkhkdev.services.PurchaseAppService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class AppConfiguration {
    @Bean
    public Input input(){
        return new ConsoleInput(new Scanner(System.in));
    }
    @Bean
    public AppRepository<Customer> customerAppRepository() {
        return new Storage<Customer>("customers");
    }
    @Bean
    public AppRepository<Category> categoryAppRepository() {
        return new Storage<Category>("categories");
    }
    @Bean
    public AppRepository<Component> componentAppRepository() {
        return new Storage<Component>("components");
    }
    @Bean
    public AppRepository<Purchase> purchaseAppRepository() {
        return new Storage<Purchase>("purchases");
    }
    @Bean
    public AppHelper<Customer> customerAppHelper (){
        return new CustomerAppHelper(input());
    }
    @Bean
    public AppService<Customer> customerAppService(){
      return new CustomerAppService(customerAppRepository(),customerAppHelper());
    }
    @Bean
    public AppService<Category> categoryAppService(){
        return new CategoryAppService(categoryAppRepository(),categoryAppHelper());
    }
    @Bean
    public AppHelper<Category> categoryAppHelper (){
        return new CategoryAppHelper(input());
    }
    @Bean
    public AppService<Component> componentAppService(){
        return new ComponentAppService(componentAppRepository(),componentAppHelper());
    }
    @Bean
    public AppHelper<Component> componentAppHelper (){
        return new ComponentAppHelper(input(),categoryAppService());
    }
    @Bean
    public AppService<Purchase> purchaseAppService(){
        return new PurchaseAppService(purchaseAppHelper(),purchaseAppRepository());
    }
    @Bean
    public AppHelper<Purchase> purchaseAppHelper (){
        return new PurchaseAppHelper(input(),componentAppService(),customerAppService());
    }

}
