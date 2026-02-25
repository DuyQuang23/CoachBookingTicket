package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.DriverDTO;
import com.example.coachbookticket.exception.ConflictException;
import com.example.coachbookticket.exception.ResourceNotFoundException;
import com.example.coachbookticket.model.Driver;
import com.example.coachbookticket.model.User;
import com.example.coachbookticket.repository.DriverRepository;
import com.example.coachbookticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    @Override
    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DriverDTO getDriverById(Integer id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", id));
        return mapToDTO(driver);
    }

    @Override
    public DriverDTO createDriver(DriverDTO dto) {
        // 1. Kiểm tra User có tồn tại không
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));

        // 2. Kiểm tra User này đã là tài xế chưa
        if (driverRepository.existsByUser_UserId(dto.getUserId())) {
            throw new ConflictException("User này đã là tài xế.");
        }

        // 3. Kiểm tra bằng lái trùng
        if (driverRepository.existsByLicenseNumber(dto.getLicenseNumber())) {
            throw new ConflictException("Số bằng lái đã tồn tại.");
        }

        // 4. Tạo Driver
        Driver driver = Driver.builder()
                .user(user)
                .licenseNumber(dto.getLicenseNumber())
                .experienceYears(dto.getExperienceYears())
                .status(dto.getStatus() != null ? dto.getStatus() : "ACTIVE")
                .build();

        // 5. Cập nhật Role của User thành DRIVER (nếu chưa phải)
        if (user.getRole() != User.Role.DRIVER && user.getRole() != User.Role.ADMIN) {
            user.setRole(User.Role.DRIVER);
            userRepository.save(user);
        }

        return mapToDTO(driverRepository.save(driver));
    }

    @Override
    public DriverDTO updateDriver(Integer id, DriverDTO dto) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", id));

        // Kiểm tra trùng bằng lái nếu thay đổi
        if (!driver.getLicenseNumber().equals(dto.getLicenseNumber()) &&
                driverRepository.existsByLicenseNumber(dto.getLicenseNumber())) {
            throw new ConflictException("Số bằng lái đã tồn tại.");
        }

        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setExperienceYears(dto.getExperienceYears());
        if (dto.getStatus() != null) driver.setStatus(dto.getStatus());

        return mapToDTO(driverRepository.save(driver));
    }

    @Override
    public void deleteDriver(Integer id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException("Driver", "id", id);
        }
        driverRepository.deleteById(id);
    }

    private DriverDTO mapToDTO(Driver driver) {
        User u = driver.getUser();
        return DriverDTO.builder()
                .driverId(driver.getDriverId())
                .userId(u.getUserId())
                .fullName(u.getFullName()) // Lấy tên từ bảng User
                .phone(u.getPhone())       // Lấy sđt từ bảng User
                .email(u.getEmail())
                .licenseNumber(driver.getLicenseNumber())
                .experienceYears(driver.getExperienceYears())
                .status(driver.getStatus())
                .build();
    }
}