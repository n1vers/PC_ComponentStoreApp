package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.entity.Category;
import ee.ivkhkdev.entity.Component;
import ee.ivkhkdev.repositories.ComponentRepository;
import ee.ivkhkdev.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@org.springframework.stereotype.Component
public class ComponentHelper implements Helper<Component> {
    @Autowired
    private  Input input;
    @Autowired
    private  Helper<Category> categoryHelper;
    @Autowired
    private ComponentRepository componentRepository;

    @Override
    public Optional<Component> create() {
        try {
            Component component = new Component();
            System.out.print("Введите бренд компонента: ");
            component.setBrand(input.nextLine());
            Optional<Category> category = categoryHelper.create();
            if (category.isEmpty()) {
                return Optional.empty();
            }
            component.setCategory(category.get());
            System.out.print("Введите модель компонента: ");
            component.setModel(input.nextLine());
            System.out.print("Введите цену компонента: ");
            component.setPrice(Double.parseDouble(input.nextLine()));
            System.out.println("Количество товара: ");
            component.setAmount(Integer.parseInt(input.nextLine()));
            return Optional.of(component);
        } catch (Exception e) {
            System.out.println("Ошибка при создании компонента: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean printList() {
        List<Component> components = componentRepository.findAll();
        try {
            if (components.isEmpty()) return false;
            for (int i = 0; i < components.size(); i++) {
                Component component = components.get(i);
                System.out.printf(Locale.ENGLISH,
                        "%d. Бренд: %s, Модель: %s, Категория: %s, Цена: %.2f $ , Количество:%s шт. %n ",
                        i + 1,
                        component.getBrand(),
                        component.getModel(),
                        component.getCategory(),
                        component.getPrice(),
                        component.getAmount()
                );
            }
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при выводе списка компонентов: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Component component) {
        return false;
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
