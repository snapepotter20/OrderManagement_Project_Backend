package com.bgsw.repository.inventoryrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bgsw.entity.inventoryentity.RawMaterials;

public interface RawMaterialsRepository extends JpaRepository<RawMaterials, Integer> {

}
