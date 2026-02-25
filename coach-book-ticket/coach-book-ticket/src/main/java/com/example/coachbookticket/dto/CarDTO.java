package com.example.coachbookticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDTO {

    @JsonProperty("carId")
    private Integer carId;

    @JsonProperty("plateNumber")
    private String plateNumber;

    @JsonProperty("carType")
    private String carType;

    @JsonProperty("seatCapacity")
    private Integer seatCapacity;

    @JsonProperty("status")
    private String status;

    @JsonProperty("driverId")
    private Integer driverId;

    private String driverName;

    @JsonProperty("plate_number")
    public void setPlateNumberSnake(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @JsonProperty("car_type")
    public void setCarTypeSnake(String carType) {
        this.carType = carType;
    }
}