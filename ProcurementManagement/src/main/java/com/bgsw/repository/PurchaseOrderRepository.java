package com.bgsw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bgsw.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

}
