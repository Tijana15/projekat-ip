package net.etfbl.ip.urban_motion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;
    private Integer durationSeconds;
    private Boolean active;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Rental(LocalDateTime dateTime, Double startX, Double starty, Double endX, Double endY, Integer durationSeconds, Boolean active, Double price, Client client, Vehicle vehicle) {
        this.dateTime = dateTime;
        this.startX = startX;
        this.startY = starty;
        this.endX = endX;
        this.endY = endY;
        this.durationSeconds = durationSeconds;
        this.active = active;
        this.price = price;
        this.client = client;
        this.vehicle = vehicle;
    }
}
