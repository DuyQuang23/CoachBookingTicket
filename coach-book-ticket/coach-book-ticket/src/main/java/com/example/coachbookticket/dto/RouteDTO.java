package com.example.coachbookticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDTO {

    @JsonProperty("route_id")
    private Integer routeId;

    @JsonProperty("start_point")
    private String startPoint;

    @JsonProperty("end_point")
    private String endPoint;

    @JsonProperty("distance_km")
    private BigDecimal distanceKm;

    @JsonProperty("estimated_duration")
    private LocalTime estimatedDuration;
}
