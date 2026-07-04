package com.sagar.service;

import com.sagar.payload.request.AirportRequest;
import com.sagar.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest request) throws Exception;
    AirportResponse getAirportById(Long id) throws Exception;
    List<AirportResponse> getAllAirports();

    AirportResponse updateAirport(Long id, AirportRequest request) throws Exception;
    void deleteAirportById(Long id) throws Exception;
    List<AirportResponse> getAirportByCityId(Long cityId);
}
