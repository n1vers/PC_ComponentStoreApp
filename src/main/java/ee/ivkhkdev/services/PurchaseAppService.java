package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.AppRepository;
import ee.ivkhkdev.interfaces.AppService;
import ee.ivkhkdev.model.Purchase;

import java.util.List;

public class PurchaseAppService implements AppService<Purchase> {
    private final AppHelper<Purchase> purchaseAppHelper;
    private final AppRepository<Purchase> appRepository;

    public PurchaseAppService(AppHelper<Purchase> purchaseAppHelper, AppRepository<Purchase> appRepository) {
        this.purchaseAppHelper = purchaseAppHelper;
        this.appRepository = appRepository;
    }

    @Override
    public boolean add() {
        Purchase purchase = purchaseAppHelper.create();
        if (purchase == null) return false;
        try {
            appRepository.save(purchase);
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении покупки: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean print() {
        return purchaseAppHelper.printList(appRepository.load());
    }

    @Override
    public List<Purchase> list() {
        return appRepository.load();
    }

    @Override
    public boolean edit(){
        return false;
    }
}
