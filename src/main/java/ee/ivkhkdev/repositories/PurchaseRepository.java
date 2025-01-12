package ee.ivkhkdev.repositories;

import ee.ivkhkdev.entity.Category;
import ee.ivkhkdev.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    
}
