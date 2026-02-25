package com.example.coachbookticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDTO {

    @JsonProperty("seat_id")
    private Integer seatId;

    @JsonProperty("car_id")
    private Integer carId;

    @JsonProperty("seat_number")
    private Integer seatNumber;

    @JsonProperty("seat_type")
    private String seatType;

    @JsonProperty("status")
    private String status;
}
