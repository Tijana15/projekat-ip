package net.etfbl.ip.urban_motion.controller;

import net.etfbl.ip.urban_motion.dto.DailyRevenueDTO;
import net.etfbl.ip.urban_motion.dto.VehicleBreakdownDTO;
import net.etfbl.ip.urban_motion.dto.VehicleRevenueDTO;
import net.etfbl.ip.urban_motion.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // Prihod po danima za određeni mjesec
    @GetMapping("/daily-revenue")
    public ResponseEntity<List<DailyRevenueDTO>> getDailyRevenue(
            @RequestParam int year,
            @RequestParam int month) {

        List<DailyRevenueDTO> dailyRevenue = statisticsService.getDailyRevenueForMonth(year, month);
        return ResponseEntity.ok(dailyRevenue);
    }

    // Broj kvarova po vrsti vozila
    @GetMapping("/breakdowns-by-vehicle")
    public ResponseEntity<List<VehicleBreakdownDTO>> getBreakdownsByVehicleType() {
        List<VehicleBreakdownDTO> breakdowns = statisticsService.getBreakdownsByVehicleType();
        return ResponseEntity.ok(breakdowns);
    }

    // Ukupan prihod po vrsti vozila
    @GetMapping("/revenue-by-vehicle")
    public ResponseEntity<List<VehicleRevenueDTO>> getRevenueByVehicleType() {
        List<VehicleRevenueDTO> revenueByVehicle = statisticsService.getRevenueByVehicleType();
        return ResponseEntity.ok(revenueByVehicle);
    }
}
