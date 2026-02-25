package com.example.coachbookticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverDTO {
    private Integer driverId;

    private Integer userId;
    private String fullName;
    private String phone;
    private String email;

    @JsonProperty("licenseNumber")
    private String licenseNumber;

    @JsonProperty("experienceYears")
    private Integer experienceYears;

    private String status;

    @JsonProperty("license_number")
    public void setLicenseNumberSnake(String val) { this.licenseNumber = val; }

    @JsonProperty("experience_years")
    public void setExperienceYearsSnake(Integer val) { this.experienceYears = val; }

    @JsonProperty("user_id")
    public void setUserIdSnake(Integer val) { this.userId = val; }
}