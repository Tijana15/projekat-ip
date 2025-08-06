package net.etfbl.ip.urban_motion.controller;

import net.etfbl.ip.urban_motion.dto.BreakdownSimpleDTO;
import net.etfbl.ip.urban_motion.model.Breakdown;
import net.etfbl.ip.urban_motion.service.BreakdownService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/breakdowns")
public class BreakdownController {
    private final BreakdownService breakdownService;

    public BreakdownController(BreakdownService breakdownService) {
        this.breakdownService = breakdownService;
    }

    @GetMapping
    public ResponseEntity<List<Breakdown>> getBreakdowns() {
        return ResponseEntity.ok(breakdownService.getAllBreakdowns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Breakdown>> getBreakdownById(@PathVariable Long id) {
        return ResponseEntity.ok(breakdownService.getBreakdownById(id));
    }

    @GetMapping("/by-vehicle/{id}")
    public ResponseEntity<List<Breakdown>> getBreakdownByVehicle(@PathVariable String id) {
        return ResponseEntity.ok(breakdownService.getBreakdownsByVehicleId(id));
    }

    @PostMapping
    public ResponseEntity<BreakdownSimpleDTO> createBreakdown(@RequestBody BreakdownSimpleDTO breakdown) {
        return ResponseEntity.ok(breakdownService.createBreakdown(breakdown));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBreakdown(@PathVariable Long id) {
        boolean deleted = breakdownService.deleteBreakdownById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
