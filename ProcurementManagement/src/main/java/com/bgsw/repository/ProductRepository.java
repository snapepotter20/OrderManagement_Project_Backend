package com.bgsw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bgsw.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
