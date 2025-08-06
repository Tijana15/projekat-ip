package net.etfbl.ip.urban_motion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.urban_motion.model.Rental;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalSimpleDTO {
    private Long id;
    private LocalDateTime dateTime;
    private Integer durationSeconds;
    private String clientName;
    private String vehicleId;
    private Double price;

    public RentalSimpleDTO(Rental rental) {
        this.id = rental.getId();
        this.dateTime = rental.getDateTime();
        this.durationSeconds = rental.getDurationSeconds();
        this.clientName = rental.getClient().getFirstname() + " " + rental.getClient().getLastname();
        this.vehicleId = rental.getVehicle().getId();
        this.price = rental.getPrice();
    }
}
