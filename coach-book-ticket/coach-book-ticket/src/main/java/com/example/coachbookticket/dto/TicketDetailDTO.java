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
    private String routeStart;
    private String routeEnd;
    private Integer userId;
    private String fullName;
    private Integer seatNumber;
    private LocalDateTime bookingDate;
    private String paymentStatus;
    private Boolean cancelFlag;
}
