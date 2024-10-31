package ee.ivkhkdev.services;

import ee.ivkhkdev.helpers.AppHelper;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.repositories.Repository;

import java.util.List;

public class ComponentService implements Service<Component> {

    private final List<Component> components;
    private Repository<Component> repository;
    private AppHelper<Component> appHelperComponent;

    public ComponentService(List<Component> components, Repository<Component> repository, AppHelper<Component> apphelperComponent) {
        this.components = components;
        this.repository = repository;
        this.appHelperComponent = apphelperComponent;
    }

    @Override
    public boolean add(){
        Component component = appHelperComponent.create();
        if(component == null) return false;
        try {
            for (int i = 0; i <= components.size(); i++){
                if(i == 0 ){
                    components.add(component);
                    repository.save(component);
                    break;
                }else if(components.get(i) == null){
                    components.add(component);
                    repository.save(component);
                    break;
                }
            }
            return true;
        }catch (Exception e) {
            System.out.println("Ошибка: " + e.toString());
            return false;
        }
    }

    @Override
    public boolean print(){
        return appHelperComponent.printList(components);
    }

    @Override
    public List<Component> list() {
        return components;
    }
}
