package ee.ivkhkdev.services;

import ee.ivkhkdev.helpers.Helper;
import ee.ivkhkdev.entity.Purchase;
import ee.ivkhkdev.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseAppService implements AppService<Purchase> {

    private final Helper<Purchase> purchaseHelper;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseAppService(Helper<Purchase> purchaseHelper, PurchaseRepository purchaseRepository) {
        this.purchaseHelper = purchaseHelper;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public boolean add() {
        Optional<Purchase> optionalPurchase = purchaseHelper.create();
        if (optionalPurchase.isEmpty()) {
            return false;
        }
        purchaseRepository.save(optionalPurchase.get());
        return true;
    }

    @Override
    public boolean print() {
        return purchaseHelper.printList(purchaseRepository.findAll(), true);
    }

    @Override
    public boolean edit() {
        return false;
    }
}
