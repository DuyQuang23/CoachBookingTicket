package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.RouteDTO;
import com.example.coachbookticket.model.Route;
import java.util.List;

public interface RouteService {
    RouteDTO create(RouteDTO dto);
    RouteDTO update(Integer id, RouteDTO dto);
    void delete(Integer id);
    RouteDTO getById(Integer id);
    List<RouteDTO> listAll();
    List<RouteDTO> search(String keyword);
    Route findEntityById(Integer id);
}
