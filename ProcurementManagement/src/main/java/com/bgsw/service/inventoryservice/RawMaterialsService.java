package com.bgsw.service.inventoryservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bgsw.entity.inventoryentity.RawMaterials;
import com.bgsw.repository.inventoryrepository.RawMaterialsRepository;

@Service
public class RawMaterialsService {

    @Autowired
    private RawMaterialsRepository rawMaterialsRepository;

    public RawMaterials saveRawMaterial(RawMaterials material) {
        return rawMaterialsRepository.save(material);
    }

    public List<RawMaterials> getAllRawMaterials() {
        return rawMaterialsRepository.findAll();
    }

    public Optional<RawMaterials> getRawMaterialById(int id) {
        return rawMaterialsRepository.findById(id);
    }

    public RawMaterials updateRawMaterial(int id, RawMaterials updatedMaterial) {
        return rawMaterialsRepository.findById(id).map(material -> {
            material.setMaterial_name(updatedMaterial.getMaterial_name());
            material.setMaterial_desc(updatedMaterial.getMaterial_desc());
            material.setMaterial_price(updatedMaterial.getMaterial_price());
            return rawMaterialsRepository.save(material);
        }).orElseThrow(() -> new RuntimeException("Raw material not found with ID: " + id));
    }

    public void deleteRawMaterial(int id) {
        rawMaterialsRepository.deleteById(id);
    }
}
