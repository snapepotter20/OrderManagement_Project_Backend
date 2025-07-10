package com.bgsw.repository.inventoryrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bgsw.entity.inventoryentity.InventoryTransactions;

public interface InventoryTransactionsRepository extends JpaRepository<InventoryTransactions, Integer> {

}
