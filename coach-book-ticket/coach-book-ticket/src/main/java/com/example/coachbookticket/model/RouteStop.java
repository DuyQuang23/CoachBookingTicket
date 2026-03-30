package com.example.coachbookticket.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routestop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routestop_id")
    private Integer routeStopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "stop_order", nullable = false)
    private Integer stopOrder;

    @Column(name = "distance_from_start")
    private Integer distanceFromStart;

    @Column(name = "time_from_start")
    private Integer timeFromStart;
}