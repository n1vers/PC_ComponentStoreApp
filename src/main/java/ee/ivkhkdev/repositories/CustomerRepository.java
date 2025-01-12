package ee.ivkhkdev.repositories;

import ee.ivkhkdev.entity.Category;
import ee.ivkhkdev.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}
