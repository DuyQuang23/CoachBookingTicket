package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.CarDTO;
import java.util.List;

public interface CarService {
    List<CarDTO> getAllCars();
    CarDTO getCarById(Integer id);
    CarDTO createCar(CarDTO carDTO);
    CarDTO updateCar(Integer id, CarDTO carDTO);
    void deleteCar(Integer id);
}