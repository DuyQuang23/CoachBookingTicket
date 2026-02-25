package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.TripCreateDTO;
import com.example.coachbookticket.dto.TripDTO;
import com.example.coachbookticket.exception.ResourceNotFoundException;
import com.example.coachbookticket.model.Car;
import com.example.coachbookticket.model.Route;
import com.example.coachbookticket.model.Trip;
import com.example.coachbookticket.repository.CarRepository;
import com.example.coachbookticket.repository.RouteRepository;
import com.example.coachbookticket.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepo;
    private final RouteRepository routeRepo;
    private final CarRepository carRepo;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public TripDTO create(TripCreateDTO dto) {
        Route route = routeRepo.findById(dto.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route", "id", dto.getRouteId()));
        Car car = carRepo.findById(dto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("PassengerCar", "id", dto.getCarId()));

        Trip trip = Trip.builder()
                .route(route)
                .car(car)
                .departureTime(dto.getDepartureTime())
                .arrivalTime(dto.getArrivalTime())
                .price(dto.getPrice())
                .availableSeats(dto.getAvailableSeats())
                .status(Trip.Status.SCHEDULED)
                .build();

        Trip saved = tripRepo.save(trip);
        return convertToDTO(saved);
    }

    @Override
    public TripDTO update(Integer id, TripCreateDTO dto) {
        Trip existing = tripRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", id));

        Route route = routeRepo.findById(dto.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route", "id", dto.getRouteId()));
        Car car = carRepo.findById(dto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("PassengerCar", "id", dto.getCarId()));

        existing.setRoute(route);
        existing.setCar(car);
        existing.setDepartureTime(dto.getDepartureTime());
        existing.setArrivalTime(dto.getArrivalTime());
        existing.setPrice(dto.getPrice());
        existing.setAvailableSeats(dto.getAvailableSeats());

        Trip updated = tripRepo.save(existing);
        return convertToDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        Trip t = findEntityById(id);
        tripRepo.delete(t);
    }

    @Override
    public TripDTO getById(Integer id) {
        return convertToDTO(findEntityById(id));
    }

    @Override
    public List<TripDTO> listAll() {
        return tripRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> search(String start, String end, LocalDateTime fromDateTime) {

        LocalTime searchTime = fromDateTime.toLocalTime();


        List<Trip> allTrips = tripRepo.findByRouteStartPointAndRouteEndPoint(start, end);


        List<Trip> filteredTrips = allTrips.stream()
                .filter(trip -> {
                    LocalTime tripTime = trip.getDepartureTime().toLocalTime();
                    return tripTime.isAfter(searchTime) || tripTime.equals(searchTime);
                })
                .toList();

        return filteredTrips.stream()
                .map(trip -> mapper.map(trip, TripDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Trip findEntityById(Integer id) {
        return tripRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", id));
    }

    private TripDTO convertToDTO(Trip trip) {
        TripDTO dto = mapper.map(trip, TripDTO.class);
        dto.setRouteId(trip.getRoute().getRouteId());
        dto.setCarId(trip.getCar().getCarId());
        dto.setRouteName(trip.getRoute().getStartPoint() + " → " + trip.getRoute().getEndPoint());
        return dto;
    }
}
