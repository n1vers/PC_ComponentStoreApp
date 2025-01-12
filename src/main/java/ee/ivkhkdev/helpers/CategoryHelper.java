package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.entity.Category;
import ee.ivkhkdev.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class CategoryHelper implements Helper<Category> {
    @Autowired
    private  Input input;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Optional<Category> create() {
        try {
            Category category = new Category();
            System.out.print("Введите название категории: ");
            category.setCategoryName(input.nextLine());
           return Optional.of(category);
        }catch (Exception e) {
            System.out.println("Ошибка при создании категории: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Category category) {
        return false;
    }

    @Override
    public boolean printList() {
        List<Category> categories = categoryRepository.findAll();
            if(categories.isEmpty()) {
                return false;
            }
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf(Locale.ENGLISH,"%d. %s%n", i+1,categories.get(i).getCategoryName());
            }
            return true;
    }

}