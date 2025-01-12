package ee.ivkhkdev.services;

import ee.ivkhkdev.helpers.Helper;
import ee.ivkhkdev.entity.Category;
import ee.ivkhkdev.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryAppService implements AppService<Category> {

    private final Helper<Category> helperCategory;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryAppService(Helper<Category> helperCategory, CategoryRepository categoryRepository) {
        this.helperCategory = helperCategory;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean add() {
        Optional<Category> optionalCategory = helperCategory.create();
        if (optionalCategory.isEmpty()) {
            return false;
        }
        categoryRepository.save(optionalCategory.get());
        return true;
    }

    @Override
    public boolean print() {
        return helperCategory.printList(categoryRepository.findAll(), true);
    }

    @Override
    public boolean edit() {
       return false;
    }
}
