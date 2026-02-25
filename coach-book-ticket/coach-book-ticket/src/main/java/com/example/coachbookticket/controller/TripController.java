package com.example.coachbookticket.controller;

import com.example.coachbookticket.dto.TripCreateDTO;
import com.example.coachbookticket.dto.TripDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.coachbookticket.service.TripService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;
    public TripController(TripService service) {
        this.tripService = service;
    }

    @PostMapping
    public ResponseEntity<TripDTO> create(@RequestBody TripCreateDTO dto) {
        return ResponseEntity.ok(tripService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDTO> update(@PathVariable Integer id, @RequestBody TripCreateDTO dto) {
        return ResponseEntity.ok(tripService.update(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<TripDTO>> list() {
        return ResponseEntity.ok(tripService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(tripService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTrips(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {

        return ResponseEntity.ok(tripService.search(start, end, from));
    }
}
