package net.etfbl.ip.urban_motion.controller;

import net.etfbl.ip.urban_motion.dto.RentalDTO;
import net.etfbl.ip.urban_motion.dto.RentalSimpleDTO;
import net.etfbl.ip.urban_motion.model.Rental;
import net.etfbl.ip.urban_motion.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<RentalSimpleDTO>> getRentals() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @GetMapping("/vehicle/{id}")
    public ResponseEntity<List<RentalSimpleDTO>> getRentalByVehicleId(@PathVariable String id) {
        return ResponseEntity.ok(rentalService.findAllByVehicleId(id));
    }

    @PostMapping
    ResponseEntity<RentalDTO> add(@RequestBody RentalDTO rentalDTO) {
        RentalDTO temp = rentalService.add(rentalDTO);
        return ResponseEntity.ok(temp);
    }
}
