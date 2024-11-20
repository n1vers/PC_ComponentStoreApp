package ee.ivkhkdev.helpers;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Input;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.interfaces.AppService;

import java.util.List;
import java.util.Locale;

public class ComponentAppHelper implements AppHelper<Component> {

    private final Input input;
    private final AppService<Category> categoryAppService;

    public ComponentAppHelper(Input input, AppService<Category> categoryAppService) {
        this.input = input;
        this.categoryAppService = categoryAppService;
    }

    @Override
    public Component create() {
        Component component = new Component();
        try {
            System.out.print("Введите бренд компонента: ");
            component.setBrand(input.nextLine());

            System.out.println("Список категорий:");
            List<Category> categories = categoryAppService.list();
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, categories.get(i).getCategoryName());
            }
            System.out.printf("Выберите номер категории из списка: ");
            int numberCategory = Integer.parseInt(input.nextLine());
            component.getCategory().add(categoryAppService.list().get(numberCategory - 1));

            System.out.print("Введите модель компонента: ");
            component.setModel(input.nextLine());
            System.out.print("Введите цену компонента: ");
            component.setPrice(Double.parseDouble(input.nextLine()));
            return component;
        } catch (Exception e) {
            System.out.println("Ошибка при создании компонента: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean printList(List<Component> components) {
        try {
            if (components.size() == 0) return false;
            for (int i = 0; i < components.size(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < components.get(i).getCategory().size(); j++) {
                    sb.append(components.get(i).getCategory().get(j).getCategoryName());
                    if (j < components.get(i).getCategory().size() - 1) {
                        sb.append(", ");
                    }
                }

                String outputLine = String.format(Locale.ENGLISH,
                        "%d. Бренд: %s, Модель: %s, Категория: %s, Цена: %.2f $%n ",
                        i + 1,
                        components.get(i).getBrand(),
                        components.get(i).getModel(),
                        sb.toString(),
                        components.get(i).getPrice()
                );

                System.out.print(outputLine);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при выводе списка компонентов: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Component update(List<Component> components) {
        if (components.isEmpty()) {
            System.out.println("Список компонентов пуст.");
            return null;
        }

        try {
            this.printList(components);
            System.out.print("Введите номер компонента для редактирования: ");
            int index = Integer.parseInt(input.nextLine()) - 1;

            if (index < 0 || index >= components.size()) {
                System.out.println("Неверный номер компонента.");
                return null;
            }

            Component component = components.get(index);
            System.out.printf("Текущий бренд: %s. Введите новый бренд или нажмите Enter, чтобы оставить без изменений: ", component.getBrand());
            String brand = input.nextLine();
            if (!brand.isEmpty()) {
                component.setBrand(brand);
            }

            System.out.println("Текущие категории:");
            List<Category> categories = categoryAppService.list();
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, categories.get(i).getCategoryName());
            }

            System.out.print("Хотите создать новую категорию? (y/n): ");
            String createNewCategory = input.nextLine();
            if (createNewCategory.equalsIgnoreCase("y")) {
                boolean success = categoryAppService.add();
                if (success) {
                    Category newCategory = categoryAppService.list().get(categoryAppService.list().size() - 1);
                    component.getCategory().clear();
                    component.getCategory().add(newCategory);
                } else {
                    System.out.println("Ошибка при создании новой категории.");
                }
            } else if (createNewCategory.equalsIgnoreCase("n")) {
                System.out.print("Выберите новую категорию (или нажмите Enter, чтобы оставить без изменений): ");
                String categoryInput = input.nextLine();
                if (!categoryInput.isEmpty()) {
                    try {
                        int categoryIndex = Integer.parseInt(categoryInput) - 1;
                        if (categoryIndex < 0 || categoryIndex >= categories.size()) {
                            System.out.println("Неверный номер категории. Категория не изменена.");
                        } else {
                            component.getCategory().clear();
                            component.getCategory().add(categories.get(categoryIndex));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: Неверный формат ввода.");
                    }
                }
            }

            System.out.printf("Текущая модель: %s. Введите новую модель или нажмите Enter, чтобы оставить без изменений: ", component.getModel());
            String model = input.nextLine();
            if (!model.isEmpty()) {
                component.setModel(model);
            }

            System.out.printf(Locale.ENGLISH,"Текущая цена: %.2f $. Введите новую цену или нажмите Enter, чтобы оставить без изменений: ", component.getPrice());
            String priceInput = input.nextLine();
            if (!priceInput.isEmpty()) {
                component.setPrice(Double.parseDouble(priceInput));
            }
            return component;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка: индекс вне допустимого диапазона.");
        }

        return null;
    }

}
