package net.etfbl.ip.urban_motion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.urban_motion.model.VehicleType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleBreakdownDTO {
    private String vehicleType;
    private Long breakdownCount;
}
