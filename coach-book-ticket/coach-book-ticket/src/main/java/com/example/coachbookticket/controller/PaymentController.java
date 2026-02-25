package com.example.coachbookticket.controller;

import com.example.coachbookticket.dto.PaymentDTO;
import com.example.coachbookticket.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/create_payment")
    public ResponseEntity<PaymentDTO> createPayment(
            HttpServletRequest request,
            @RequestParam long amount,
            @RequestParam String orderInfo
    ) throws UnsupportedEncodingException {
        PaymentDTO paymentDTO = paymentService.createVnPayPayment(request, amount, orderInfo);
        return ResponseEntity.ok(paymentDTO);
    }
}