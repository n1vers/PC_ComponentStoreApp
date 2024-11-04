package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Repository;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Purchase;

import java.util.List;

public class PurchaseService implements Service<Purchase> {
    private final AppHelper<Purchase> purchaseAppHelper;
    private final Repository<Purchase> repository;

    public PurchaseService(AppHelper<Purchase> purchaseAppHelper, Repository<Purchase> repository) {
        this.purchaseAppHelper = purchaseAppHelper;
        this.repository = repository;
    }

    @Override
    public boolean add() {
        Purchase purchase = purchaseAppHelper.create();
        if (purchase == null) return false;
        try {
            repository.save(purchase);
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении покупки: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean print() {
        return purchaseAppHelper.printList(repository.load());
    }

    @Override
    public List<Purchase> list() {
        return repository.load();
    }

    @Override
    public boolean edit(){
        return false;
    }
}
