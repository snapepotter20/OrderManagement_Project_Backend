package com.bgsw.controller.inventorycontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bgsw.entity.inventoryentity.RawMaterials;
import com.bgsw.service.inventoryservice.RawMaterialsService;

@RestController
@RequestMapping("/api/rawmaterials")
@CrossOrigin(origins = "http://localhost:4200")
public class RawMaterialsController {

    @Autowired
    private RawMaterialsService rawMaterialsService;

    @PostMapping("/addrawmaterial")
    public ResponseEntity<RawMaterials> createRawMaterial(@RequestBody RawMaterials material) {
        return ResponseEntity.ok(rawMaterialsService.saveRawMaterial(material));
    }

    @GetMapping("/getallrawmaterials")
    public ResponseEntity<List<RawMaterials>> getAllRawMaterials() {
        return ResponseEntity.ok(rawMaterialsService.getAllRawMaterials());
    }

    @GetMapping("/getrawmaterial/{id}")
    public ResponseEntity<RawMaterials> getRawMaterialById(@PathVariable int id) {
        return rawMaterialsService.getRawMaterialById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/updaterawmaterial/{id}")
    public ResponseEntity<RawMaterials> updateRawMaterial(@PathVariable int id, @RequestBody RawMaterials updatedMaterial) {
        try {
            return ResponseEntity.ok(rawMaterialsService.updateRawMaterial(id, updatedMaterial));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleterawmaterial/{id}")
    public ResponseEntity<Void> deleteRawMaterial(@PathVariable int id) {
        rawMaterialsService.deleteRawMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
