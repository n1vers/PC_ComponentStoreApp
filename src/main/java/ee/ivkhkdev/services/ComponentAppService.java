package ee.ivkhkdev.services;

import ee.ivkhkdev.helpers.Helper;
import ee.ivkhkdev.entity.Component;
import ee.ivkhkdev.repositories.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ComponentAppService implements AppService<Component> {
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private Helper<Component> helperComponent;

    @Override
    public boolean add(){
        try {
            Optional<Component> component = helperComponent.create();
            if (component.isPresent()) {
                componentRepository.save(component.get());
                return true;
            }
        }catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean print(){
        return helperComponent.printList();
    }

    @Override
    public List<Component> list() {
        return componentRepository.findAll();
    }

    @Override
    public boolean edit(){
        List<Component> components = appRepository.load();
        Component updateComponent = helperComponent.update(components);
        if(updateComponent == null) return false;
        try {
            int index = components.indexOf(updateComponent);
            if (index != -1) {
                appRepository.update(index, updateComponent);
                return true;
            } else {
                System.out.println("Компонент не найден в списке.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении компонента: " + e.getMessage());
            return false;
        }
    }
}
