package org.unibl.etf.clientapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rental {
    private Long id;
    private LocalDateTime dateTime;
    private Integer durationSeconds;
    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;
    private Long clientId;
    private String vehicleId;
    private Double price;
    private boolean active;
    private String vehicleType;

}
