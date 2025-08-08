package net.etfbl.ip.urban_motion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalPriceConfig {
    @Id
    @Enumerated(EnumType.STRING)
    VehicleType vehicleType;
    Double price;
}
