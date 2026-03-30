package com.example.coachbookticket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TicketCreateDTO {
    @NotNull(message = "Trip ID is required")
    private Integer tripId;

    @NotNull(message = "Car ID is required")
    private Integer carId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Seat id")
    private Integer seatId;
    
    @NotNull(message = "Pickup stop is required")
    private Integer pickupStopId;

    @NotNull(message = "Drop-off stop is required")
    private Integer dropoffStopId;

    private Double price;

    private LocalDateTime bookingDate;

    private String paymentStatus;
}
