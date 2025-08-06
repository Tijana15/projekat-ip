package net.etfbl.ip.urban_motion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(
        strategy = InheritanceType.JOINED
)
public abstract class Vehicle {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    private String model;
    private Double purchasePrice;
    @Enumerated(EnumType.STRING)
    private VehicleState vehicleState;
    private String picture;
    private Double mapX;
    private Double mapY;
}
