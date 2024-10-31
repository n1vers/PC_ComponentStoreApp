package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.services.Service;

import java.util.List;

public class AppHelperComponent implements AppHelper<Component> {

    private final Input input;
    private final Service<Category> categoryService;

    public AppHelperComponent(Input input, Service<Category> categoryService) {
        this.input = input;
        this.categoryService = categoryService;
    }

    @Override
    public Component create() {
        Component component = new Component();
        try {
            System.out.print("Введите бренд компонента: ");
            component.setBrand(input.nextLine());

            System.out.println("Список категорий:");
            List<Category> categories = categoryService.list();
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, categories.get(i).getCategoryName());
            }
            System.out.printf("Выберите номер категории из списка: ");
            int numberCategory = Integer.parseInt(input.nextLine());
            component.getCategory().add(categoryService.list().get(numberCategory-1));


            System.out.print("Введите модель компонента: ");
            component.setModel(input.nextLine());
            System.out.print("Введите цену компонента: ");
            component.setPrice(Double.parseDouble(input.nextLine()));
            return component;
        }catch (Exception e) {
            System.out.println("Ошибка при создании компонента: " + e.getMessage());
            return null;
        }
    }
    @Override
    public boolean printList(List<Component> components) {
        try {
            if (components.size() == 0) return false;
            for(int i = 0; i<components.size(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < components.get(i).getCategory().size(); j++) {
                    sb.append(components.get(i).getCategory().get(j).getCategoryName());
                    sb.append(". ");
                }
                System.out.printf(
                        "%d. Бренд: %s, Модель: %s, Категория: %s, Цена: %.2f%n",
                        i + 1,
                        components.get(i).getBrand(),
                        components.get(i).getModel(),
                        sb.toString(),
                        components.get(i).getPrice()
                );
            }
            return true;
        }catch (Exception e) {
            System.out.println("Ошибка при выводе списка компонентов: " + e.getMessage());
            return false;
        }
    }
}
