package com.bgsw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bgsw.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
