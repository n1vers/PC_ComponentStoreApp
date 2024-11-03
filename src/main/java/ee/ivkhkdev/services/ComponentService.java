package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.interfaces.Repository;

import java.util.List;

public class ComponentService implements Service<Component> {

    private Repository<Component> repository;
    private AppHelper<Component> appHelperComponent;

    public ComponentService(Repository<Component> repository, AppHelper<Component> apphelperComponent) {
        this.repository = repository;
        this.appHelperComponent = apphelperComponent;
    }

    @Override
    public boolean add(){
        Component component = appHelperComponent.create();
        if(component == null) return false;
        try {
            repository.save(component);
            return true;
        }catch (Exception e) {
            System.out.println("Ошибка: " + e.toString());
            return false;
        }
    }

    @Override
    public boolean print(){
        return appHelperComponent.printList(repository.load());
    }

    @Override
    public List<Component> list() {
        return repository.load();
    }
}
