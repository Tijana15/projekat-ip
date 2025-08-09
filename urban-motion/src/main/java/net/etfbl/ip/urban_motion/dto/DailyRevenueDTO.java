package net.etfbl.ip.urban_motion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyRevenueDTO {
    private String date; // "2025-01-15"
    private Double revenue;
}