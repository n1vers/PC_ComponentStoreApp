package ee.ivkhkdev.helpers;

import ee.ivkhkdev.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerHelper extends Helper<Customer>{
    Optional<Long> getIdModifyCustomer(List<Customer> customers, boolean enabled);
}
