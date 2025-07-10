package com.bgsw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bgsw.entity.DeliveryTracking;
import com.bgsw.repository.DeliveryTrackingRepository;
import com.bgsw.repository.PurchaseOrderRepository;

@Service
public class DeliveryTrackingService {

    @Autowired
    private DeliveryTrackingRepository deliveryRepo;
    
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepo;

    public DeliveryTracking trackOrder(Long orderId) {
        return purchaseOrderRepo.findById(orderId)
                .map(order -> deliveryRepo.findByPurchaseOrder(order))
                .orElse(null);
    }

    public DeliveryTracking updateTracking(DeliveryTracking tracking) {
        return deliveryRepo.save(tracking);
    }
}
