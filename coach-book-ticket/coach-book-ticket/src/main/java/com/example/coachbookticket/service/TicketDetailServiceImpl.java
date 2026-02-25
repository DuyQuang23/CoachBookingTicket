package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.TicketDetailCreateDTO;
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
public class TicketDetailServiceImpl implements TicketDetailService {

    private final TicketDetailRepository ticketRepo;
    private final TripRepository tripRepo;
    private final UserRepository userRepo;
    private final SeatRepository seatRepo;

    private final ModelMapper mapper = new ModelMapper();
    private final CarRepository carRepo;

    @Override
    public TicketDetailDTO create(TicketDetailCreateDTO dto) {
        Trip trip = tripRepo.findById(dto.getTripId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", dto.getTripId()));
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
        Seat seat = seatRepo.findByCarAndSeatNumber(dto.getCarId(),dto.getSeatNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "number", dto.getSeatNumber()));
        Car car = carRepo.findById(dto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", dto.getCarId()));

        TicketDetail ticket = TicketDetail.builder()
                .trip(trip)
                .user(user)
                .seat(seat)
                .car(car)
                .bookingDate(dto.getBookingDate())
                .paymentStatus(dto.getPaymentStatus())
                .cancelFlag(false)
                .build();

        TicketDetail saved = ticketRepo.save(ticket);
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

    private TicketDetailDTO convertToDTO(TicketDetail ticket) {
        return TicketDetailDTO.builder()
                .ticketId(ticket.getTicketId())
                .tripId(ticket.getTrip().getTripId())
                .userId(ticket.getUser().getUserId())
                .carId(ticket.getCar().getCarId())
                .fullName(ticket.getUser().getFullName())
                .routeStart(ticket.getTrip().getRoute().getStartPoint())
                .routeEnd(ticket.getTrip().getRoute().getEndPoint())
                .seatNumber(ticket.getSeat().getSeatNumber())
                .bookingDate(ticket.getBookingDate())
                .paymentStatus(ticket.getPaymentStatus())
                .cancelFlag(ticket.getCancelFlag())
                .build();
    }
}
