package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.AppService;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.interfaces.AppRepository;

import java.util.List;

public class CategoryAppService implements AppService<Category> {

    private AppRepository<Category> appRepository;
    private AppHelper<Category> appHelperCategory;

    public CategoryAppService(AppRepository<Category> appRepository, AppHelper<Category> appHelperCategory) {
        this.appRepository = appRepository;
        this.appHelperCategory = appHelperCategory;
    }


    public boolean add(){
        Category category = appHelperCategory.create();
        if (category == null) return false;
        try {
            appRepository.save(category);
            return true;
        }catch (Exception e) {
            System.out.println("Ошибка: " + e.toString());
            return false;
        }
    }

    @Override
    public boolean print(){
        return appHelperCategory.printList(appRepository.load());
    }

    @Override
    public List<Category> list(){
        return appRepository.load();
    }

    @Override
    public boolean edit(){
        return false;
    }
}
