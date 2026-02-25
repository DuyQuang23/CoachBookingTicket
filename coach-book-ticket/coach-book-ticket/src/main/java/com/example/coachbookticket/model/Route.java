package com.example.coachbookticket.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "route")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "start_point")
    private String startPoint;

    @Column(name = "end_point")
    private String endPoint;

    @Column(name = "distance_km")
    private BigDecimal distanceKm;

    @Column(name = "estimated_duration")
    private LocalTime estimatedDuration;


    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Trip> trips;
}
