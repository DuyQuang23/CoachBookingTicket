package com.example.coachbookticket.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class StopManifestDTO {
    private Integer routeStopId;
    private String locationName;
    private Integer stopOrder;

    // danh sach hanh khach len xe
    private List<PassengerInfoDTO> pickups;

    // danh sach hanh khach xuong xe
    private List<PassengerInfoDTO> dropoffs;
}