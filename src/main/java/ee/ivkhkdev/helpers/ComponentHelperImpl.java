package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.entity.Category;
import ee.ivkhkdev.entity.Component;
import ee.ivkhkdev.repositories.CategoryRepository;
import ee.ivkhkdev.repositories.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@org.springframework.stereotype.Component
public class ComponentHelperImpl implements ComponentHelper {

    @Autowired
    private Input input;

    @Autowired
    private Helper<Category> categoryHelper;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

            System.out.print("Количество товара: ");
            component.setAmount(Integer.parseInt(input.nextLine()));

            return Optional.of(component);
        } catch (Exception e) {
            System.out.println("Ошибка при создании компонента: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Component> update(Component component) {
        try {
            // Редактирование бренда
            System.out.printf("Текущий бренд: %s. Введите новый бренд или нажмите Enter, чтобы оставить без изменений: ", component.getBrand());
            String brand = input.nextLine();
            if (!brand.isEmpty()) {
                component.setBrand(brand);
            }

            // Редактирование категории
            System.out.println("Текущие категории:");
            List<Category> categories = categoryRepository.findAll();
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, categories.get(i).getCategoryName());
            }

            System.out.print("Выберите новую категорию (или оставьте пустым для сохранения текущей): ");
            String categoryInput = input.nextLine();
            if (!categoryInput.isEmpty()) {
                try {
                    int categoryIndex = Integer.parseInt(categoryInput) - 1;
                    if (categoryIndex >= 0 && categoryIndex < categories.size()) {
                        component.setCategory(categories.get(categoryIndex));
                    } else {
                        System.out.println("Неверный выбор категории.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода категории: " + e.getMessage());
                }
            }

            // Редактирование модели
            System.out.printf("Текущая модель: %s. Введите новую модель или нажмите Enter, чтобы оставить без изменений: ", component.getModel());
            String model = input.nextLine();
            if (!model.isEmpty()) {
                component.setModel(model);
            }

            // Редактирование цены
            System.out.printf(Locale.ENGLISH, "Текущая цена: %.2f $. Введите новую цену или нажмите Enter, чтобы оставить без изменений: ", component.getPrice());
            String priceInput = input.nextLine();
            if (!priceInput.isEmpty()) {
                component.setPrice(Double.parseDouble(priceInput));
            }

            // Редактирование количества
            System.out.print("Текущее количество: " + component.getAmount() + ". Введите новое количество или нажмите Enter, чтобы оставить без изменений: ");
            String amountInput = input.nextLine();
            if (!amountInput.isEmpty()) {
                component.setAmount(Integer.parseInt(amountInput));
            }

            return Optional.of(component);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка: индекс вне допустимого диапазона.");
        }
        return Optional.empty();
    }

    @Override
    public boolean printList(List<Component> components, boolean enableAll) {
        try {
            if (components.isEmpty()) {
                System.out.println("Нет компонентов для отображения.");
                return false;
            }
            for (int i = 0; i < components.size(); i++) {
                Component component = components.get(i);
                String status = (enableAll || component.getCategory() != null) ? "" : "(выключен)";
                System.out.printf(Locale.ENGLISH,
                        "%d. Бренд: %s, Модель: %s, Категория: %s, Цена: %.2f $, Количество: %d шт. %s%n",
                        i + 1,
                        component.getBrand(),
                        component.getModel(),
                        component.getCategory() != null ? component.getCategory().getCategoryName() : "Без категории",
                        component.getPrice(),
                        component.getAmount(),
                        status
                );
            }
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при выводе списка компонентов: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Long> getIdModifyComponent(List<Component> components, boolean enableAll) {
        // Печать списка компонентов с использованием параметра enableAll
        if (!this.printList(components, enableAll)) {
            return Optional.empty();
        }

        // Запрос у пользователя ID компонента для модификации
        System.out.print("Выберите компонент для изменения (укажите номер): ");
        try {
            int id = Integer.parseInt(input.nextLine()) - 1;
            if (id >= 0 && id < components.size()) {
                return Optional.of((long) id);
            } else {
                System.out.println("Неверный выбор компонента.");
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
            return Optional.empty();
        }
    }
}
