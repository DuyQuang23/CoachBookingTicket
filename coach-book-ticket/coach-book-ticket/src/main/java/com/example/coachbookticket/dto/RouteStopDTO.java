package com.example.coachbookticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteStopDTO {
    private Integer routeStopId;
    private String locationName;
    private Integer stopOrder;
    private Integer distanceFromStart;
    private Integer timeFromStart;
}