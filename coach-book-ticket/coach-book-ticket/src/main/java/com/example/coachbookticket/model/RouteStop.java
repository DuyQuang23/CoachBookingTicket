package com.example.coachbookticket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "routestop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stop_id")
    private Integer stopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Route route;

    @Column(name = "stop_name")
    private String stopName;

    private String address;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    private String note;
}
