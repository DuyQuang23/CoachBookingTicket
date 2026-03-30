package com.example.coachbookticket.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDetailDTO {
    private Integer ticketId;
    private Integer tripId;
    private Integer carId;
    private Integer pickupStopOder;
    private Integer dropoffStopOrder;
    private Integer userId;
    private String fullName;
    private Integer seatId;
    private LocalDateTime bookingDate;
    private Double price;
    private String paymentStatus;
    private Boolean cancelFlag;
}
