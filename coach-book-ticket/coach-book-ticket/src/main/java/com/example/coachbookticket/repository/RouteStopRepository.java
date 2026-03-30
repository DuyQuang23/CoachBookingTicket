package com.example.coachbookticket.repository;

import com.example.coachbookticket.model.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteStopRepository extends JpaRepository<RouteStop, Integer> {

    List<RouteStop> findByRoute_RouteIdOrderByStopOrderAsc(Integer routeId);
}