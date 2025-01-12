package ee.ivkhkdev.repositories;

import ee.ivkhkdev.entity.Category;
import ee.ivkhkdev.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    
}
