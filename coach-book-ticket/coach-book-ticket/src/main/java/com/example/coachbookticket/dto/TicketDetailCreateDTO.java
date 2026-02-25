package com.example.coachbookticket.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TicketDetailCreateDTO {
    private Integer tripId;
    private Integer userId;
    private Integer seatNumber;
    private Integer carId;
    private LocalDateTime bookingDate;
    private String paymentStatus;
}
