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
public class CategoryHelperImpl implements CategoryHelper {

    @Autowired
    private Input input;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> create() {
        try {
            Category category = new Category();
            System.out.print("Введите название категории: ");
            category.setCategoryName(input.nextLine());
            return Optional.of(category);
        } catch (Exception e) {
            System.out.println("Ошибка при создании категории: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Category> update(Category category) {
      return null;
    }

    @Override
    public boolean printList(List<Category> categories, boolean enableAll) {
        if (categories.isEmpty()) {
            System.out.println("Нет доступных категорий.");
            return false;
        }
        for (int i = 0; i < categories.size(); i++) {
            String isEnabled = "";
            if (enableAll) {
                if (!categories.get(i).isAvailable()) {
                    isEnabled = "(выключена)";
                }
            }
            System.out.printf(Locale.ENGLISH, "%d. %s %s%n", i + 1, categories.get(i).getCategoryName(), isEnabled);
        }
        return true;
    }
}
