package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.DriverDTO;
import java.util.List;

public interface DriverService {
    List<DriverDTO> getAllDrivers();
    DriverDTO getDriverById(Integer id);
    DriverDTO createDriver(DriverDTO dto);
    DriverDTO updateDriver(Integer id, DriverDTO dto);
    void deleteDriver(Integer id);
}