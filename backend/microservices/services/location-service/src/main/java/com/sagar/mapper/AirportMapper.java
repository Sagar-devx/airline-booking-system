package com.sagar.mapper;

import com.sagar.model.Airport;
import com.sagar.payload.request.AirportRequest;
import com.sagar.payload.response.AirportResponse;

import java.time.ZoneId;

public class AirportMapper {

    public static Airport toEntity(AirportRequest request)
    {
        if(request == null)
        {
            return null;
        }
        return Airport.builder()
                .iataCode(request.getIataCode())
                .name(request.getName())
                .timeZone(request.getTimeZone() != null ? request.getTimeZone().getId() : null)
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();

    }

    public static AirportResponse toResponse(Airport airport)
    {
        if(airport == null)
        {
            return null;
        }
        ZoneId zone = null;

        if (airport.getTimeZone() != null && !airport.getTimeZone().isBlank())
        {
            try
            {
                zone = ZoneId.of(airport.getTimeZone().trim());
            }
            catch (Exception e) {}
        }
        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getDetailName())
                .timeZone(zone)
                .address(airport.getAddress())
                .geoCode(airport.getGeoCode())
                .city(CityMapper.toResponse(airport.getCity()))
                .build();
    }

    public static void updateEntity(AirportRequest request , Airport existingAirport) {

        if(request == null || existingAirport == null) return;

        if (request.getIataCode() != null) {
            existingAirport.setIataCode(request.getIataCode().toUpperCase().trim());
        }
        if (request.getName() != null) {
            existingAirport.setName(request.getName().trim());
        }
        if (request.getAddress() != null) {
            existingAirport.setAddress(request.getAddress());
        }
        if (request.getGeoCode() != null) {
            existingAirport.setGeoCode(request.getGeoCode());
        }
        if (request.getTimeZone() != null) {
            existingAirport.setTimeZone(request.getTimeZone().getId());
        }

    }
}
