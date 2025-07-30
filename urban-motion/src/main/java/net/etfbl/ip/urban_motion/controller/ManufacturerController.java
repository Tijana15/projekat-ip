package net.etfbl.ip.urban_motion.controller;

import net.etfbl.ip.urban_motion.model.Manufacturer;
import net.etfbl.ip.urban_motion.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Long id) {
        Optional<Manufacturer> manufacturer = manufacturerService.getManufacturerById(id);
        return manufacturer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Manufacturer createManufacturer(@RequestBody Manufacturer manufacturer) {
        return manufacturerService.saveManufacturer(manufacturer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(@PathVariable Long id,
                                                           @RequestBody Manufacturer manufacturerDetails) {
        try {
            Manufacturer updatedManufacturer = manufacturerService.updateManufacturer(id, manufacturerDetails);
            return ResponseEntity.ok(updatedManufacturer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }
}