package com.example.coachbookticket.repository;

import com.example.coachbookticket.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {

    List<Route> findByStartPoint(String startPoint);

    List<Route> findByEndPoint(String endPoint);

    Route findByStartPointAndEndPoint(String startPoint, String endPoint);

    @Query("""
        SELECT r FROM Route r 
        WHERE LOWER(r.startPoint) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(r.endPoint) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Route> search(String keyword);
}
