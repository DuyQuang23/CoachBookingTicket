package com.example.coachbookticket.controller;

import com.example.coachbookticket.dto.SeatDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.coachbookticket.service.SeatService;
import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService service;
    public SeatController(SeatService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SeatDTO> create(@RequestBody SeatDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatDTO> update(@PathVariable Integer id, @RequestBody SeatDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SeatDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<SeatDTO>> getByCar(@PathVariable Integer carId) {
        return ResponseEntity.ok(service.findByCar(carId));
    }

    @GetMapping("/car/{carId}/available")
    public ResponseEntity<List<SeatDTO>> getAvailable(@PathVariable Integer carId) {
        return ResponseEntity.ok(service.findAvailableSeats(carId));
    }
}
