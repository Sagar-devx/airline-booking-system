package com.sagar.service.impl;

import com.sagar.mapper.AirportMapper;
import com.sagar.model.Airport;
import com.sagar.model.City;
import com.sagar.payload.request.AirportRequest;
import com.sagar.payload.response.AirportResponse;
import com.sagar.repository.AirportRepository;
import com.sagar.repository.CityRepository;
import com.sagar.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception {

       if(airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
           throw new Exception("Airport with IATA code " + request.getIataCode() + " already exists");
       }
       City city = cityRepository.findById(request.getCityId())
               .orElseThrow(() -> new Exception("City with ID " + request.getCityId() + " not found"));

       Airport airport = AirportMapper.toEntity(request);
       airport.setCity(city);

       Airport savedAirport = airportRepository.save(airport);
       return AirportMapper.toResponse(savedAirport);
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport with ID " + id + " not found"));
        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {

       Airport existingAirport = airportRepository.findById(id)
               .orElseThrow(() -> new Exception("Airport with ID " + id + " not found"));

       if(request.getIataCode() != null
               && !existingAirport.getIataCode().equals(request.getIataCode())
               && airportRepository.findByIataCode(request.getIataCode()).isPresent()
       ) {
           throw new Exception("Airport with IATA code " + request.getIataCode() + " already exists");

       }
        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new Exception("City with ID " + request.getCityId() + " not found"));

       AirportMapper.updateEntity(request, existingAirport);
       existingAirport.setCity(city);
       Airport updatedAirport = airportRepository.save(existingAirport);
       return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public void deleteAirportById(Long id) throws Exception {

        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport with ID " + id + " not found"));

        airportRepository.delete(airport);

    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId){
        return airportRepository.findByCityId(cityId).stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
