package com.bgsw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bgsw.entity.Supplier;
import com.bgsw.repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        Supplier supplier = getSupplierById(id);
        supplier.setSupplierName(updatedSupplier.getSupplierName());
        supplier.setSupplierEmail(updatedSupplier.getSupplierEmail());
        supplier.setSupplierAddress(updatedSupplier.getSupplierAddress());
        supplier.setSupplierContactPerson(updatedSupplier.getSupplierContactPerson());
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}

