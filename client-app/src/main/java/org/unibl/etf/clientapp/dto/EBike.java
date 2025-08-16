package org.unibl.etf.clientapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EBike {
    private String id;
    private String manufacturer;
    private String model;
    private String picture;
    private int maxRange;
    private Double mapX;
    private Double mapY;
}
