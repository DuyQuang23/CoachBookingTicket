package com.example.coachbookticket.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String status;
    private String message;
    private String paymentUrl;
}