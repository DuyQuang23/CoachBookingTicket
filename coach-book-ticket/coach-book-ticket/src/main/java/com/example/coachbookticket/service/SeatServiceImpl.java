package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.SeatDTO;
import com.example.coachbookticket.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.coachbookticket.model.Car;
import com.example.coachbookticket.model.Seat;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.coachbookticket.repository.CarRepository;
import com.example.coachbookticket.repository.SeatRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepo;
    private final CarRepository carRepo;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public SeatDTO create(SeatDTO dto) {
        Car car = carRepo.findById(dto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("PassengerCar", "id", dto.getCarId()));

        if (seatRepo.exsistsByCarAndSeatNumber(dto.getCarId(), dto.getSeatNumber()))
            throw new IllegalArgumentException("Seat number already exists in this car");

        Seat seat = Seat.builder()
                .car(car)
                .seatNumber(dto.getSeatNumber())
                .seatType(dto.getSeatType())
                .status(dto.getStatus())
                .build();

        Seat saved = seatRepo.save(seat);
        SeatDTO result = mapper.map(saved, SeatDTO.class);
        result.setCarId(car.getCarId());
        return result;
    }

    @Override
    public SeatDTO update(Integer id, SeatDTO dto) {
        Seat seat = findEntityById(id);

        if (dto.getSeatNumber() != null) seat.setSeatNumber(dto.getSeatNumber());
        if (dto.getSeatType() != null) seat.setSeatType(dto.getSeatType());
        if (dto.getStatus() != null) seat.setStatus(dto.getStatus());

        Seat updated = seatRepo.save(seat);
        SeatDTO result = mapper.map(updated, SeatDTO.class);
        result.setCarId(seat.getCar().getCarId());
        return result;
    }

    @Override
    public void delete(Integer id) {
        Seat s = findEntityById(id);
        seatRepo.delete(s);
    }

    @Override
    public SeatDTO getById(Integer id) {
        Seat s = findEntityById(id);
        SeatDTO dto = mapper.map(s, SeatDTO.class);
        dto.setCarId(s.getCar().getCarId());
        return dto;
    }

    @Override
    public List<SeatDTO> listAll() {
        return seatRepo.findAll().stream()
                .map(s -> {
                    SeatDTO dto = mapper.map(s, SeatDTO.class);
                    dto.setCarId(s.getCar().getCarId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatDTO> findByCar(Integer carId) {
        return seatRepo.findByCar_CarId(carId).stream()
                .map(s -> {
                    SeatDTO dto = mapper.map(s, SeatDTO.class);
                    dto.setCarId(s.getCar().getCarId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatDTO> findAvailableSeats(Integer carId) {
        return seatRepo.findByCar_CarIdAndStatus(carId, "AVAILABLE").stream()
                .map(s -> {
                    SeatDTO dto = mapper.map(s, SeatDTO.class);
                    dto.setCarId(s.getCar().getCarId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Seat findEntityById(Integer id) {
        return seatRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
    }
}
