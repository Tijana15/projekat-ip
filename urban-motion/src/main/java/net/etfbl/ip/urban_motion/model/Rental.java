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
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int durationSeconds;
    private Boolean active;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Rental(LocalDateTime dateTime, int startX, int starty, int endX, int endY, Integer durationSeconds, Boolean active, Double price, Client client, Vehicle vehicle) {
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
