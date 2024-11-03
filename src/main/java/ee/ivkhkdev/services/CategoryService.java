package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.interfaces.Repository;

import java.util.List;

public class CategoryService  implements Service<Category> {

    private Repository<Category> repository;
    private AppHelper<Category> appHelperCategory;

    public CategoryService( Repository<Category> repository, AppHelper<Category> appHelperCategory) {
        this.repository = repository;
        this.appHelperCategory = appHelperCategory;
    }


    public boolean add(){
        Category category = appHelperCategory.create();
        if (category == null) return false;
        try {
            repository.save(category);
            return true;
        }catch (Exception e) {
            System.out.println("Ошибка: " + e.toString());
            return false;
        }
    }

    @Override
    public boolean print(){
        return appHelperCategory.printList(repository.load());
    }

    @Override
    public List<Category> list(){
        return repository.load();
    }
}
