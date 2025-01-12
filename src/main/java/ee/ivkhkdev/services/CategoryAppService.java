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
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private Helper<Category> helperCategory;

    @Override
    public boolean add(){
        try {
            Optional<Category> category = helperCategory.create();
            if (category.isPresent()) {
                categoryRepository.save(category.get());
                return true;
            }
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean print(){
        return helperCategory.printList();
    }

    @Override
    public List<Category> list(){
        return categoryRepository.findAll();
    }

    @Override
    public boolean edit(){
        return false;
    }
}
