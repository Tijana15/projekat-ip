package org.unibl.etf.clientapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceData {
    private LocalDateTime startDateTime;
    private Integer durationSeconds;
    private Double endX;
    private Double endY;
    private Double startX;
    private Double startY;
    private Double price;
    private Long rentalId;
    private String clientFirstname;
    private String clientLastname;
    private String clientIdDocument;
    private String vehicleType;
    private String clientDriversLicence;
    private String vehicleModel;
}
