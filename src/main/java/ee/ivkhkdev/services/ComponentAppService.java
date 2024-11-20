package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.AppService;
import ee.ivkhkdev.model.Component;
import ee.ivkhkdev.interfaces.AppRepository;

import java.util.List;

public class ComponentAppService implements AppService<Component> {

    private AppRepository<Component> appRepository;
    private AppHelper<Component> appHelperComponent;

    public ComponentAppService(AppRepository<Component> appRepository, AppHelper<Component> apphelperComponent) {
        this.appRepository = appRepository;
        this.appHelperComponent = apphelperComponent;
    }

    @Override
    public boolean add(){
        Component component = appHelperComponent.create();
        if(component == null) return false;
        try {
            appRepository.save(component);
            return true;
        }catch (Exception e) {
            System.out.println("Ошибка: " + e.toString());
            return false;
        }
    }

    @Override
    public boolean print(){
        return appHelperComponent.printList(appRepository.load());
    }

    @Override
    public List<Component> list() {
        return appRepository.load();
    }

    @Override
    public boolean edit(){
        List<Component> components = appRepository.load();
        Component updateComponent = appHelperComponent.update(components);
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
