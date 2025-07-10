package com.bgsw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bgsw.entity.DeliveryTracking;
import com.bgsw.entity.PurchaseOrder;

@Repository
public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, Long> {
	 DeliveryTracking findByPurchaseOrder(PurchaseOrder purchaseOrder);
}
