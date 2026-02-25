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
public class TripDTO {

    @JsonProperty("trip_id")
    private Integer tripId;

    @JsonProperty("route_id")
    private Integer routeId;

    @JsonProperty("car_id")
    private Integer carId;

    @JsonProperty("route_name")
    private String routeName;

    @JsonProperty("departure_time")
    private LocalDateTime departureTime;

    @JsonProperty("arrival_time")
    private LocalDateTime arrivalTime;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("available_seats")
    private Integer availableSeats;

    @JsonProperty("status")
    private String status;
}
