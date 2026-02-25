package com.example.coachbookticket.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticketdetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Trip trip;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Car car;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_number")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Seat seat;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "cancel_flag")
    private Boolean cancelFlag;
}
