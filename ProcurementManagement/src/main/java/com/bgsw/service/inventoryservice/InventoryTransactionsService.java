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

//    public InventoryTransactions saveTransaction(InventoryTransactions inputTransaction) {
//        // Extract only IDs from incoming products list
//        List<Product> attachedProducts = new ArrayList<>();
//        for (Product p : inputTransaction.getProducts()) {
//            productRepository.findById(p.getProductId()).ifPresent(attachedProducts::add);
//        }
//
//        inputTransaction.setProducts(attachedProducts);
//        return transactionsRepository.save(inputTransaction);
//    }
    
    public InventoryTransactions saveTransaction(InventoryTransactions inputTransaction) {
        List<Product> attachedProducts = new ArrayList<>();
        double totalUsedQty = 0;

        for (Product incoming : inputTransaction.getProducts()) {
            Product dbProduct = productRepository.findById(incoming.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + incoming.getProductId()));

            double usedQty = incoming.getTransactionQuantity();

            if (usedQty == 0) continue;

            if (dbProduct.getProductQuantity() < usedQty) {
                throw new RuntimeException("Insufficient stock for product: " + dbProduct.getProductName());
            }

            // Deduct stock
            dbProduct.setProductQuantity(dbProduct.getProductQuantity() - usedQty);
            productRepository.save(dbProduct);

            attachedProducts.add(dbProduct);
            totalUsedQty += usedQty;
        }

        inputTransaction.setProducts(attachedProducts);
        inputTransaction.setQuantity(totalUsedQty);

        return transactionsRepository.save(inputTransaction);
    }


    public List<InventoryTransactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }
}
