package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.RouteDTO;
import com.example.coachbookticket.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.coachbookticket.model.Route;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.coachbookticket.repository.RouteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepo;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public RouteDTO create(RouteDTO dto) {
        Route route = mapper.map(dto, Route.class);
        Route saved = routeRepo.save(route);
        return mapper.map(saved, RouteDTO.class);
    }

    @Override
    public RouteDTO update(Integer id, RouteDTO dto) {
        Route existing = findEntityById(id);
        existing.setStartPoint(dto.getStartPoint());
        existing.setEndPoint(dto.getEndPoint());
        existing.setDistanceKm(dto.getDistanceKm());
        existing.setEstimatedDuration(dto.getEstimatedDuration());
        Route updated = routeRepo.save(existing);
        return mapper.map(updated, RouteDTO.class);
    }

    @Override
    public void delete(Integer id) {
        Route route = findEntityById(id);
        routeRepo.delete(route);
    }

    @Override
    public RouteDTO getById(Integer id) {
        return mapper.map(findEntityById(id), RouteDTO.class);
    }

    @Override
    public List<RouteDTO> listAll() {
        return routeRepo.findAll()
                .stream().map(r -> mapper.map(r, RouteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RouteDTO> search(String keyword) {
        return routeRepo.search(keyword)
                .stream().map(r -> mapper.map(r, RouteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Route findEntityById(Integer id) {
        return routeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "id", id));
    }
}
