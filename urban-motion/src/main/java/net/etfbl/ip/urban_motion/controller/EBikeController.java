package net.etfbl.ip.urban_motion.controller;

import net.etfbl.ip.urban_motion.model.EBike;
import net.etfbl.ip.urban_motion.service.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ebikes")
public class EBikeController {

    @Autowired
    private EBikeService eBikeService;

    @GetMapping
    public List<EBike> getAllEBikes() {
        return eBikeService.getAllEBikes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EBike> getEBikeById(@PathVariable String id) {
        Optional<EBike> eBike = eBikeService.getEBikeById(id);
        return eBike.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EBike createEBike(@RequestBody EBike eBike) {
        return eBikeService.saveEBike(eBike);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EBike> updateEBike(@PathVariable String id,
                                             @RequestBody EBike eBikeDetails) {
        try {
            EBike updatedEBike = eBikeService.updateEBike(id, eBikeDetails);
            return ResponseEntity.ok(updatedEBike);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEBike(@PathVariable String id) {
        eBikeService.deleteEBike(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/manufacturer/{manufacturerId}")
    public List<EBike> getEBikesByManufacturer(@PathVariable Long manufacturerId) {
        return eBikeService.getEBikesByManufacturerId(manufacturerId);
    }

    @GetMapping("/state/{vehicleState}")
    public List<EBike> getEBikesByState(@PathVariable String vehicleState) {
        return eBikeService.getEBikesByVehicleState(vehicleState);
    }

    @GetMapping("/range/min/{minRange}")
    public List<EBike> getEBikesByMinRange(@PathVariable Integer minRange) {
        return eBikeService.getEBikesByMaxRangeGreaterThan(minRange);
    }

    @GetMapping("/range/{minRange}/{maxRange}")
    public List<EBike> getEBikesByRangeRange(@PathVariable Integer minRange,
                                             @PathVariable Integer maxRange) {
        return eBikeService.getEBikesByMaxRangeBetween(minRange, maxRange);
    }
}