package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.TicketDetailCreateDTO;
import com.example.coachbookticket.dto.TicketDetailDTO;
import java.util.List;

public interface TicketDetailService {
    TicketDetailDTO create(TicketDetailCreateDTO dto);
    TicketDetailDTO getById(Integer id);
    List<TicketDetailDTO> listAll();
    List<TicketDetailDTO> getAllTicketByUser(Integer id);
}
