package com.example.coachbookticket.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "car")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer carId;

    @Column(name = "plate_number", unique = true, nullable = false)
    private String plateNumber;

    private String model;

    @Column(name = "seat_capacity")
    private Integer seatCapacity;

    // FK driver_id -> Driver
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Driver driver;

    private String status;

    // One car -> many seats
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Seat> seats;

    // One car -> many trips
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Trip> trips;
}
