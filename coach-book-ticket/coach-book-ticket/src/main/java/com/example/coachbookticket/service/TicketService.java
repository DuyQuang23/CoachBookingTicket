package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.StopManifestDTO;
import com.example.coachbookticket.dto.TicketCreateDTO;
import com.example.coachbookticket.dto.TicketDetailDTO;
import java.util.List;

public interface TicketService {
    TicketDetailDTO create(TicketCreateDTO dto);
    TicketDetailDTO getById(Integer id);
    List<TicketDetailDTO> listAll();
    List<TicketDetailDTO> getAllTicketByUser(Integer id);
    List<Integer> getBookedSeatIds(Integer tripId, Integer carId, Integer newPickupOrder, Integer newDropoffOrder);
    List<StopManifestDTO> getDriverManifest(Integer tripId, Integer carId);
}
