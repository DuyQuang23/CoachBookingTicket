package com.example.coachbookticket.repository;

import com.example.coachbookticket.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    boolean existsByPlateNumber(String plateNumber);
}