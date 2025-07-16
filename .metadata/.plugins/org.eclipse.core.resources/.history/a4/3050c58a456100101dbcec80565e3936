package com.bgsw.service.inventoryservice;
import com.bgsw.entity.Product;
import com.bgsw.entity.inventoryentity.InventoryTransactions;
import com.bgsw.repository.ProductRepository;
import com.bgsw.repository.inventoryrepository.InventoryTransactionsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryTransactionsService {

    @Autowired
    private InventoryTransactionsRepository transactionsRepository;

    @Autowired
    private ProductRepository productRepository;

    public InventoryTransactions saveTransaction(InventoryTransactions inputTransaction) {
        // Extract only IDs from incoming products list
        List<Product> attachedProducts = new ArrayList<>();
        for (Product p : inputTransaction.getProducts()) {
            productRepository.findById(p.getProductId()).ifPresent(attachedProducts::add);
        }

        inputTransaction.setProducts(attachedProducts);
        return transactionsRepository.save(inputTransaction);
    }

    public List<InventoryTransactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }
}
