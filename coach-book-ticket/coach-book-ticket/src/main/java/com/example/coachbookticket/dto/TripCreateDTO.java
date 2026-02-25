package com.example.coachbookticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripCreateDTO {

    @JsonProperty("route_id")
    private Integer routeId;

    @JsonProperty("car_id")
    private Integer carId;

    @JsonProperty("departure_time")
    private LocalDateTime departureTime;

    @JsonProperty("arrival_time")
    private LocalDateTime arrivalTime;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("available_seats")
    private Integer availableSeats;
}
