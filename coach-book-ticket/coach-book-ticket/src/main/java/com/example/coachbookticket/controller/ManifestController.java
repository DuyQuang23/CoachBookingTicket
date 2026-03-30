package com.example.coachbookticket.controller;

import com.example.coachbookticket.dto.StopManifestDTO;
import com.example.coachbookticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manifest")
@RequiredArgsConstructor
public class ManifestController {

    private final TicketService ticketService;

    // API: GET /api/manifest?tripId=1&carId=1
    @GetMapping
    public ResponseEntity<List<StopManifestDTO>> getManifest(
            @RequestParam Integer tripId,
            @RequestParam Integer carId) {

        List<StopManifestDTO> manifest = ticketService.getDriverManifest(tripId, carId);
        return ResponseEntity.ok(manifest);
    }
}