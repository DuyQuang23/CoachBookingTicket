package com.example.coachbookticket.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "seat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Integer seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Car car;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "seat_type")
    private String seatType;

    private String status;

    @OneToMany(mappedBy = "seat", fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<TicketDetail> tickets;
}
