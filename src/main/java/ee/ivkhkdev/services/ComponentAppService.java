package ee.ivkhkdev.services;

import ee.ivkhkdev.helpers.ComponentHelper;
import ee.ivkhkdev.helpers.Helper;
import ee.ivkhkdev.entity.Component;
import ee.ivkhkdev.repositories.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ComponentAppService implements AppService<Component> {

    private final ComponentRepository componentRepository;
    private final ComponentHelper componentHelper;

    @Autowired
    public ComponentAppService(ComponentRepository componentRepository, ComponentHelper componentHelper) {
        this.componentRepository = componentRepository;
        this.componentHelper = componentHelper;
    }

    @Override
    public boolean add() {
        Optional<Component> optionalComponent = componentHelper.create();
        if (optionalComponent.isEmpty()) {
            return false;
        }
        componentRepository.save(optionalComponent.get());
        return true;
    }

    @Override
    public boolean print() {
        return componentHelper.printList(componentRepository.findAll(), true);
    }

    @Override
    public boolean edit() {
        Optional<Component> optionalModifyComponent = getComponentForModify();
        if (optionalModifyComponent.isEmpty()) {
            return false;
        }
        Optional<Component> optionalModifiedComponent = componentHelper.update(optionalModifyComponent.get());
        componentRepository.save(optionalModifiedComponent.get());
        return true;
    }

    private Optional<Component> getComponentForModify() {
        Optional<Long> optionalComponentId = componentHelper.getIdModifyComponent(componentRepository.findAll(), true);
        if (optionalComponentId.isEmpty()) {
            return Optional.empty();
        }
        return componentRepository.findById(optionalComponentId.get());
    }
}

