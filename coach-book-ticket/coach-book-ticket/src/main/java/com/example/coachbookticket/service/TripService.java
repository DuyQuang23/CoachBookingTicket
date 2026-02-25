package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.TripCreateDTO;
import com.example.coachbookticket.dto.TripDTO;
import com.example.coachbookticket.model.Trip;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TripService {
    TripDTO create(TripCreateDTO dto);
    TripDTO update(Integer id, TripCreateDTO dto);
    void delete(Integer id);
    TripDTO getById(Integer id);
    List<TripDTO> listAll();
    List<TripDTO> search(String start, String end, LocalDateTime fromTime);
    Trip findEntityById(Integer id);
}
