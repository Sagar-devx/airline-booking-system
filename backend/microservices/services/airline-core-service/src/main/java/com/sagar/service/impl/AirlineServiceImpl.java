package com.sagar.service.impl;

import com.sagar.enums.AirlineStatus;
import com.sagar.mapper.AirlineMapper;
import com.sagar.model.Airline;
import com.sagar.payload.request.AirlineRequest;
import com.sagar.payload.response.AirlineDropdownItem;
import com.sagar.payload.response.AirlineResponse;
import com.sagar.repository.AirlineRepository;
import com.sagar.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId) {

        Airline airline = AirlineMapper.toEntity(airlineRequest, ownerId);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {

        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("airline not found with ownerid " + ownerId)
        );
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest airlineRequest, Long ownerId) throws Exception {

        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("airline not found with ownerid " + ownerId)
        );

        AirlineMapper.updateEntity(airlineRequest, airline);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) throws Exception {

        Airline airline = airlineRepository.findById(id).orElseThrow(
                () -> new Exception("airline not found with id " + id)
        );
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(AirlineMapper::toResponse);
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) throws Exception {

        Airline airline = airlineRepository.findById(id).orElseThrow(
                () -> new Exception("airline not found with id " + id)
        );
        if (!airline.getOwnerId().equals(ownerId)) {
            throw new Exception("unauthorized: you do not own this airline");
        }
        airlineRepository.delete(airline);
    }

    @Override
    public AirlineResponse changeStatus(Long airlineId, AirlineStatus status) throws Exception {

        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new Exception("airline not found with that airlineId " + airlineId)
        );
        airline.setStatus(status);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdownItems() {

        return airlineRepository.findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(a -> AirlineDropdownItem.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .iataCode(a.getIataCode())
                        .logoUrl(a.getLogoUrl())
                        .build()
                ).toList();
    }
}
