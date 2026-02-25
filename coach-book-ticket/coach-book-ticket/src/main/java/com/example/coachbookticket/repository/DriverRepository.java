package com.example.coachbookticket.repository;

import com.example.coachbookticket.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    // Tìm tài xế theo User ID
    boolean existsByUser_UserId(Integer userId);
    // Tìm theo bằng lái
    boolean existsByLicenseNumber(String licenseNumber);
}