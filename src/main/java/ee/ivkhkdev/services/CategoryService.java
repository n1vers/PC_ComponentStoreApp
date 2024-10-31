package ee.ivkhkdev.services;

import ee.ivkhkdev.helpers.AppHelper;
import ee.ivkhkdev.model.Category;
import ee.ivkhkdev.repositories.Repository;

import java.util.List;

public class CategoryService  implements Service<Category> {

    private final List<Category> categories;
    private Repository<Category> repository;
    private AppHelper<Category> appHelperCategory;

    public CategoryService(List<Category> categories, Repository<Category> repository, AppHelper<Category> appHelperCategory) {
        this.categories = categories;
        this.repository = repository;
        this.appHelperCategory = appHelperCategory;
    }


    public boolean add(){
        Category category = appHelperCategory.create();
        if (category == null) return false;
        try {
            for (int i = 0; i <= categories.size(); i++){
                if(i == 0 ){
                    categories.add(category);
                    repository.save(category);
                    break;
                }else if(categories.get(i) == null){
                    categories.add(category);
                    repository.save(category);
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
        return appHelperCategory.printList(categories);
    }

    @Override
    public List<Category> list(){
        return categories;
    }
}
