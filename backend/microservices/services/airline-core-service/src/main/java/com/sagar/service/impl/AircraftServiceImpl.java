package com.sagar.service.impl;

import com.sagar.mapper.AircraftMapper;
import com.sagar.model.Aircraft;
import com.sagar.model.Airline;
import com.sagar.payload.request.AircraftRequest;
import com.sagar.payload.response.AircraftResponse;
import com.sagar.repository.AircraftRepository;
import com.sagar.repository.AirlineRepository;
import com.sagar.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new Exception("airline not exists for this ownerId")
                );
        Aircraft aircraft = AircraftMapper.toEntity(request, airline);

        if (aircraftRepository.existsByCode(aircraft.getCode())) {
            throw new Exception("aircraft already exists for this code");
        }
        if (aircraft.getSeatingCapacity() < aircraft.getTotalSeats()) {
            throw new Exception("seating capacity can't exceed to total seat");
        }
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Aircraft doesnot exist with id")
                );
        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwnerId(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new Exception("owner don't have airline")
                );
        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toResponse).toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("owner don't have airline"));

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());

        if (aircraft == null) {
            throw new Exception("aircraft not exists for this id");
        }
        if (request.getCode() != null
                && !aircraft.getCode().equals(request.getCode())
                && aircraftRepository.existsByCode(request.getCode())) {
            throw new Exception("code already exist with another aircraft");
        }
        AircraftMapper.updateEntity(request, aircraft);
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new Exception("owner don't have airline")
                );

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());
        if (aircraft == null) {
            throw new Exception("aircraft not exists for this id");
        }
        aircraftRepository.delete(aircraft);
    }
}
