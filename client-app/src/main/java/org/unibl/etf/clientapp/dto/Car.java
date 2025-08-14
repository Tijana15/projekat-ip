package org.unibl.etf.clientapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String id;
    private String model;
    private String manufacturer;
    private String picture;
    private String description;
}
