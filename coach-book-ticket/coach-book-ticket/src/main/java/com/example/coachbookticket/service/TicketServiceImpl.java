package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.PassengerInfoDTO;
import com.example.coachbookticket.dto.StopManifestDTO;
import com.example.coachbookticket.dto.TicketCreateDTO;
import com.example.coachbookticket.dto.TicketDetailDTO;
import com.example.coachbookticket.exception.ResourceNotFoundException;
import com.example.coachbookticket.model.*;
import com.example.coachbookticket.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepo;
    private final TripRepository tripRepo;
    private final UserRepository userRepo;
    private final SeatRepository seatRepo;
    private final RouteStopRepository routeStopRepo;

    private final ModelMapper mapper = new ModelMapper();
    private final CarRepository carRepo;
    private final EmailService emailService;

    @Override
    public TicketDetailDTO create(TicketCreateDTO dto) {
        Trip trip = tripRepo.findById(dto.getTripId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", dto.getTripId()));
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
        Seat seat = seatRepo.findByCarAndSeatId(dto.getCarId(),dto.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "number", dto.getSeatId()));
        Car car = carRepo.findById(dto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", dto.getCarId()));

        RouteStop pickup = routeStopRepo.findById(dto.getPickupStopId())
                .orElseThrow(() -> new ResourceNotFoundException("RouteStop", "id", dto.getPickupStopId()));

        RouteStop dropoff = routeStopRepo.findById(dto.getDropoffStopId())
                .orElseThrow(() -> new ResourceNotFoundException("RouteStop", "id", dto.getDropoffStopId()));

        Ticket ticket = Ticket.builder()
                .trip(trip)
                .user(user)
                .seat(seat)
                .car(car)
                .pickupStop(pickup)
                .dropoffStop(dropoff)
                .price(dto.getPrice())
                .bookingDate(dto.getBookingDate())
                .paymentStatus(dto.getPaymentStatus())
                .cancelFlag(false)
                .build();

        Ticket saved = ticketRepo.save(ticket);

        saved.getTrip().getRoute().getStartPoint();
        saved.getTrip().getRoute().getEndPoint();

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            emailService.sendTicketConfirmation(user.getEmail(), saved);
        }
        return convertToDTO(saved);
    }

    @Override
    public TicketDetailDTO getById(Integer id) {
        return convertToDTO(ticketRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TicketDetail", "id", id)));
    }

    @Override
    public List<TicketDetailDTO> listAll() {
        return ticketRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDetailDTO> getAllTicketByUser(Integer id) {
        return ticketRepo.findByUser_UserId(id).stream().map(this::convertToDTO)
                                                        .collect(Collectors.toList());
    }

    private TicketDetailDTO convertToDTO(Ticket ticket) {
        return TicketDetailDTO.builder()
                .ticketId(ticket.getTicketId())
                .tripId(ticket.getTrip().getTripId())
                .userId(ticket.getUser().getUserId())
                .carId(ticket.getCar().getCarId())
                .fullName(ticket.getUser().getFullName())
                .pickupStopOder(ticket.getPickupStop().getStopOrder())
                .dropoffStopOrder(ticket.getDropoffStop().getStopOrder())
                .seatId(ticket.getSeat().getSeatId())
                .price(ticket.getPrice())
                .bookingDate(ticket.getBookingDate())
                .paymentStatus(ticket.getPaymentStatus())
                .cancelFlag(ticket.getCancelFlag())
                .build();
    }
    @Override
    public List<Integer> getBookedSeatIds(Integer tripId, Integer carId, Integer newPickupOrder, Integer newDropoffOrder) {
        return ticketRepo.findBookedSeatIdsForSegment(tripId, carId, newPickupOrder, newDropoffOrder);
    }

    @Override
    public List<StopManifestDTO> getDriverManifest(Integer tripId, Integer carId) {
        // lay thong tin route
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", tripId));
        Integer routeId = trip.getRoute().getRouteId();

        // lay cac diem dung , tu be den lon
        List<RouteStop> allStops = routeStopRepo.findByRoute_RouteIdOrderByStopOrderAsc(routeId);

        // lay toan bo ve cua xe
        List<Ticket> validTickets = ticketRepo.findValidTicketsForManifest(tripId, carId);

        return allStops.stream().map(stop -> {

            List<PassengerInfoDTO> pickups = validTickets.stream()
                    .filter(t -> t.getPickupStop().getRouteStopId().equals(stop.getRouteStopId()))
                    .map(t -> PassengerInfoDTO.builder()
                            .fullName(t.getUser().getFullName())
                            .phone(t.getUser().getPhone()) // Giả sử User entity có sđt
                            .seatNumber(t.getSeat().getSeatNumber())
                            .build())
                    .collect(Collectors.toList());

            List<PassengerInfoDTO> dropoffs = validTickets.stream()
                    .filter(t -> t.getDropoffStop().getRouteStopId().equals(stop.getRouteStopId()))
                    .map(t -> PassengerInfoDTO.builder()
                            .fullName(t.getUser().getFullName())
                            .phone(t.getUser().getPhone())
                            .seatNumber(t.getSeat().getSeatNumber())
                            .build())
                    .collect(Collectors.toList());

            return StopManifestDTO.builder()
                    .routeStopId(stop.getRouteStopId())
                    .locationName(stop.getLocationName())
                    .stopOrder(stop.getStopOrder())
                    .pickups(pickups)
                    .dropoffs(dropoffs)
                    .build();

        }).collect(Collectors.toList());
    }

}
