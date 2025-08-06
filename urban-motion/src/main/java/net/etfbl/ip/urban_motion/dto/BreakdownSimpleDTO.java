package net.etfbl.ip.urban_motion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.urban_motion.model.Breakdown;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreakdownSimpleDTO {
    private Long id;
    private String description;
    private LocalDateTime breakdownDateTime;
    private String vehicleId;

    public BreakdownSimpleDTO(Breakdown breakdown){
        this.id = breakdown.getId();
        this.description = breakdown.getDescription();
        this.breakdownDateTime = breakdown.getBreakdownDateTime();
        this.vehicleId = breakdown.getVehicle().getId();
    }
}
