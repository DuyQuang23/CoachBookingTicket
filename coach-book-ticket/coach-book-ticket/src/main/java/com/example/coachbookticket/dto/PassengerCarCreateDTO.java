package com.example.coachbookticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerCarCreateDTO {

    @JsonProperty("license_plate")
    private String licensePlate;

    @JsonProperty("car_type")
    private String carType;

    @JsonProperty("capacity")
    private Integer capacity;

    @JsonProperty("status")
    private String status;
}
