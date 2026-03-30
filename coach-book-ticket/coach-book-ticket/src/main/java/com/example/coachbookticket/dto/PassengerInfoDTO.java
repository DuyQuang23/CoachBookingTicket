package com.example.coachbookticket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassengerInfoDTO {
    private String fullName;
    private String phone;
    private String seatNumber;
}