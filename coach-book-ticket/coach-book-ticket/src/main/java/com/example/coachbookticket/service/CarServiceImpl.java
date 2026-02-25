package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.CarDTO;
import com.example.coachbookticket.exception.ConflictException;
import com.example.coachbookticket.exception.ResourceNotFoundException;
import com.example.coachbookticket.model.Driver;
import com.example.coachbookticket.model.Car;
import com.example.coachbookticket.repository.CarRepository;
import com.example.coachbookticket.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DriverRepository driverRepository;

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarDTO getCarById(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));
        return mapToDTO(car);
    }

    @Override
    @Transactional
    public CarDTO createCar(CarDTO dto) {
        if (carRepository.existsByPlateNumber(dto.getPlateNumber())) {
            throw new ConflictException("Biển số xe đã tồn tại: " + dto.getPlateNumber());
        }

        // 1. Tạo đối tượng Car cơ bản (chưa gán driver)
        Car car = Car.builder()
                .plateNumber(dto.getPlateNumber())
                .model(dto.getCarType())
                .seatCapacity(dto.getSeatCapacity())
                .status(dto.getStatus() != null ? dto.getStatus() : "ACTIVE")
                // XÓA DÒNG .driverId(...) GÂY LỖI
                .build();

        // 2. Tìm và gán Driver nếu có
        if (dto.getDriverId() != null) {
            Driver driver = driverRepository.findById(dto.getDriverId())
                    .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", dto.getDriverId()));
            car.setDriver(driver);
        }

        return mapToDTO(carRepository.save(car));
    }

    @Override
    @Transactional
    public CarDTO updateCar(Integer id, CarDTO dto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));

        if (!car.getPlateNumber().equals(dto.getPlateNumber()) &&
                carRepository.existsByPlateNumber(dto.getPlateNumber())) {
            throw new ConflictException("Biển số xe đã tồn tại: " + dto.getPlateNumber());
        }

        car.setPlateNumber(dto.getPlateNumber());
        car.setModel(dto.getCarType());
        car.setSeatCapacity(dto.getSeatCapacity());

        if (dto.getStatus() != null) car.setStatus(dto.getStatus());

        if (dto.getDriverId() != null) {
            Driver driver = driverRepository.findById(dto.getDriverId())
                    .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", dto.getDriverId()));
            car.setDriver(driver);
        } else {
            car.setDriver(null);
        }

        return mapToDTO(carRepository.save(car));
    }

    @Override
    @Transactional
    public void deleteCar(Integer id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car", "id", id);
        }
        carRepository.deleteById(id);
    }

    private CarDTO mapToDTO(Car car) {
        return CarDTO.builder()
                .carId(car.getCarId())
                .plateNumber(car.getPlateNumber())
                .carType(car.getModel())
                .seatCapacity(car.getSeatCapacity())
                .status(car.getStatus())
                .driverId(car.getDriver() != null ? car.getDriver().getDriverId() : null)
                .driverName(car.getDriver() != null && car.getDriver().getUser() != null
                        ? car.getDriver().getUser().getFullName()
                        : "Chưa phân công")
                .build();
    }
}