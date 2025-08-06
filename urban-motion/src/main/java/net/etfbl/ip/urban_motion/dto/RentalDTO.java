package net.etfbl.ip.urban_motion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.urban_motion.model.Rental;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {
    private Long id;
    private LocalDateTime dateTime;

    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private Integer durationSeconds;
    private Double price;
    private Boolean active;
    private Long clientId;
    private String vehicleId;

    public RentalDTO(Rental rental) {
        this.id = rental.getId();
        this.dateTime = rental.getDateTime();
        this.startX=rental.getStartX();
        this.startY=rental.getStartY();
        this.endX=rental.getEndX();
        this.endY=rental.getEndY();
        this.durationSeconds=rental.getDurationSeconds();
        this.price = rental.getPrice();
        this.active = rental.getActive();
        this.clientId = rental.getClient().getId();
        this.vehicleId = rental.getVehicle().getId();
    }
}
