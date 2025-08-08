package net.etfbl.ip.urban_motion.controller;

import net.etfbl.ip.urban_motion.dto.UpdateRentalPriceDTO;
import net.etfbl.ip.urban_motion.model.RentalPriceConfig;
import net.etfbl.ip.urban_motion.service.RentalPriceConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rental-prices")
public class RentalPriceConfigController {
    private final RentalPriceConfigService rentalPriceConfigService;

    public RentalPriceConfigController(RentalPriceConfigService rentalPriceConfigService) {
        this.rentalPriceConfigService = rentalPriceConfigService;
    }

    @GetMapping
    public ResponseEntity<UpdateRentalPriceDTO> getAll() {
        return ResponseEntity.ok(this.rentalPriceConfigService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<List<RentalPriceConfig>> updateRentalPrices(@RequestBody UpdateRentalPriceDTO dto) {
        List<RentalPriceConfig> updatedConfigs = rentalPriceConfigService.update(dto);
        return ResponseEntity.ok(updatedConfigs);
    }
}
