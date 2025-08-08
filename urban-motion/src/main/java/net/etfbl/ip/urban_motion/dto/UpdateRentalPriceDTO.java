package net.etfbl.ip.urban_motion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.etfbl.ip.urban_motion.model.RentalPriceConfig;
import net.etfbl.ip.urban_motion.model.VehicleType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateRentalPriceDTO {
    private Double carPrice;
    private Double ebikePrice;
    private Double escooterPrice;

    public UpdateRentalPriceDTO(List<RentalPriceConfig> rentalPriceConfigs) {
        for (RentalPriceConfig r : rentalPriceConfigs) {
            if (r.getVehicleType().equals(VehicleType.CAR)) {
                this.carPrice = r.getPrice();
            } else if (r.getVehicleType().equals(VehicleType.EBIKE)) {
                this.ebikePrice = r.getPrice();
            } else {
                this.escooterPrice = r.getPrice();
            }
        }
    }
}
