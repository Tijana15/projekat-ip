package net.etfbl.ip.urban_motion.controller;

import net.etfbl.ip.urban_motion.model.EScooter;
import net.etfbl.ip.urban_motion.service.EScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/escooters")
public class EScooterController {

    @Autowired
    private EScooterService eScooterService;

    @GetMapping
    public List<EScooter> getAllEScooters() {
        return eScooterService.getAllEScooters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EScooter> getEScooterById(@PathVariable String id) {
        Optional<EScooter> eScooter = eScooterService.getEScooterById(id);
        return eScooter.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EScooter createEScooter(@RequestBody EScooter eScooter) {
        return eScooterService.saveEScooter(eScooter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EScooter> updateEScooter(@PathVariable String id,
                                                   @RequestBody EScooter eScooterDetails) {
        try {
            EScooter updatedEScooter = eScooterService.updateEScooter(id, eScooterDetails);
            return ResponseEntity.ok(updatedEScooter);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEScooter(@PathVariable String id) {
        eScooterService.deleteEScooter(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/manufacturer/{manufacturerId}")
    public List<EScooter> getEScootersByManufacturer(@PathVariable Long manufacturerId) {
        return eScooterService.getEScootersByManufacturerId(manufacturerId);
    }

    @GetMapping("/state/{vehicleState}")
    public List<EScooter> getEScootersByState(@PathVariable String vehicleState) {
        return eScooterService.getEScootersByVehicleState(vehicleState);
    }

    @GetMapping("/speed/min/{minSpeed}")
    public List<EScooter> getEScootersByMinSpeed(@PathVariable Integer minSpeed) {
        return eScooterService.getEScootersByMaxSpeedGreaterThan(minSpeed);
    }

    @GetMapping("/speed/max/{maxSpeed}")
    public List<EScooter> getEScootersByMaxSpeed(@PathVariable Integer maxSpeed) {
        return eScooterService.getEScootersByMaxSpeedLessThan(maxSpeed);
    }

    @GetMapping("/speed/{minSpeed}/{maxSpeed}")
    public List<EScooter> getEScootersBySpeedRange(@PathVariable Integer minSpeed,
                                                   @PathVariable Integer maxSpeed) {
        return eScooterService.getEScootersByMaxSpeedBetween(minSpeed, maxSpeed);
    }
}