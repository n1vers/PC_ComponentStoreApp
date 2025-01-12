package ee.ivkhkdev.services;

import ee.ivkhkdev.helpers.Helper;
import ee.ivkhkdev.entity.Purchase;
import ee.ivkhkdev.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseAppService implements AppService<Purchase> {
    @Autowired
    private  Helper<Purchase> purchaseHelper;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public boolean add() {
        try {
            Optional<Purchase> purchase = purchaseHelper.create();
            if (purchase.isPresent()) {
                purchaseRepository.save(purchase.get());
                return true;
            }
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении покупки: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean print() {
        return purchaseHelper.printList();
    }

    @Override
    public List<Purchase> list() {
        return purchaseRepository.findAll();
    }

    @Override
    public boolean edit(){
        return false;
    }
}
