package com.example.coachbookticket.controller;

import com.example.coachbookticket.dto.TicketCreateDTO;
import com.example.coachbookticket.dto.TicketDetailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.coachbookticket.service.TicketService;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    public TicketController(TicketService service) {
        this.ticketService = service;
    }
    @PostMapping
    public ResponseEntity<TicketDetailDTO> create(@RequestBody TicketCreateDTO dto) {
        return ResponseEntity.ok(ticketService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ticketService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TicketDetailDTO>> listAll() {
        return ResponseEntity.ok(ticketService.listAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDetailDTO>> getAllTicketByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(ticketService.getAllTicketByUser(userId));
    }
}
