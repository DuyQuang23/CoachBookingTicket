package com.example.coachbookticket.repository;

import com.example.coachbookticket.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findByCar_CarId(Integer carId);

    List<Seat> findBySeatType(String seatType);

    List<Seat> findByCar_CarIdAndStatus(Integer carId, String status);

    @Query("SELECT s FROM Seat s WHERE s.car.carId = :carId AND s.seatNumber = :seatNumber")
    Optional<Seat> findByCarAndSeatNumber(@Param("carId") Integer carId, @Param("seatNumber") Integer seatNumber);

    @Query("SELECT COUNT(s) > 0 FROM Seat s WHERE s.car.carId = :carId AND s.seatNumber = :seatNumber")
    boolean exsistsByCarAndSeatNumber(Integer carId, Integer seatNumber);
}
