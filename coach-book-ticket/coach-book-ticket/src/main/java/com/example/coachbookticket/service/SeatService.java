package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.SeatDTO;
import com.example.coachbookticket.model.Seat;
import java.util.List;

public interface SeatService {
    SeatDTO create(SeatDTO dto);
    SeatDTO update(Integer id, SeatDTO dto);
    void delete(Integer id);
    SeatDTO getById(Integer id);
    List<SeatDTO> listAll();
    List<SeatDTO> findByCar(Integer carId);
    List<SeatDTO> findAvailableSeats(Integer carId);
    Seat findEntityById(Integer id);
}
