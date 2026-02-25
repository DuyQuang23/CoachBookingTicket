package com.example.coachbookticket.repository;

import com.example.coachbookticket.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    List<Trip> findByRoute_RouteId(Integer routeId);

    List<Trip> findByCar_CarId(Integer carId);

    List<Trip> findByStatus(String status);
    List<Trip> findByRouteStartPointAndRouteEndPoint(String startPoint, String endPoint);


    @Query("""
        SELECT t FROM Trip t
        WHERE t.route.startPoint = :start
          AND t.route.endPoint = :end
          AND t.departureTime >= :from
    """)
    List<Trip> search(String start, String end, LocalDateTime from);

    @Query("""
        SELECT t FROM Trip t
        WHERE t.departureTime BETWEEN :now AND :nextDay
        ORDER BY t.departureTime ASC
    """)
    List<Trip> findUpcomingTrips(LocalDateTime now, LocalDateTime nextDay);
}
