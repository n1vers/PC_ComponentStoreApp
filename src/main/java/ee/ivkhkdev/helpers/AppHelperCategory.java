package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.model.Category;

import java.util.List;

public class AppHelperCategory implements AppHelper<Category> {

    private final Input input;

    public AppHelperCategory(Input input) {
        this.input = input;
    }

    @Override
    public Category create() {
        Category category = new Category();
        try {
            System.out.print("Введите название категории: ");
            category.setCategoryName(input.nextLine());
           return category;
        }catch (Exception e) {
            System.out.println("Ошибка при создании категории: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean printList(List<Category> categories) {
        try {
            if (categories.isEmpty()) return false;
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%d. %s%n", i+1,categories.get(i).getCategoryName());
            }
            return true;
        }catch (Exception e){
            System.out.println("Ошибка при выводе списка категорий: " + e.getMessage());
            return false;
        }
    }

}
