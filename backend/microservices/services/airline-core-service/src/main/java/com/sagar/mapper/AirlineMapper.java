package com.sagar.mapper;

import com.sagar.embeddable.Support;
import com.sagar.model.Airline;
import com.sagar.payload.request.AirlineRequest;
import com.sagar.payload.response.AirlineResponse;

public class AirlineMapper {

    public static Airline toEntity(AirlineRequest airlineRequest, Long ownderId) {
        if (airlineRequest == null) return null;

        Airline airline = Airline.builder()
                .iataCode(airlineRequest.getIataCode())
                .icaoCode(airlineRequest.getIcaoCode())
                .name(airlineRequest.getName())
                .alias(airlineRequest.getAlias())
                .logoUrl(airlineRequest.getLogoUrl())
                .website(airlineRequest.getWebsite())
                .status(airlineRequest.getStatus())
                .alliance(airlineRequest.getAlliance())
                .headquartersCityId(airlineRequest.getHeadquartersCityId())
                .ownerId(ownderId)
                .build();

        if (airlineRequest.getSupportEmail() != null || airlineRequest.getSupportHours() != null || airlineRequest.getSupportPhone() != null) {
            Support support = Support.builder()
                    .email(airlineRequest.getSupportEmail())
                    .phone(airlineRequest.getSupportPhone())
                    .hours(airlineRequest.getSupportHours())
                    .build();
            airline.setSupport(support);
        }

        return airline;
    }

    public static AirlineResponse toResponse(Airline airline) {
        if (airline == null) return null;

        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .name(airline.getName())
                .alias(airline.getAlias())
                .logoUrl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .status(airline.getStatus())
                .alliance(airline.getAlliance())
                .ownerId(airline.getOwnerId())
                .updateById(airline.getUpdatedById())
                .support(airline.getSupport())
                .createdAt(airline.getCreatedAt())
                .updatedAt(airline.getUpdatedAt())
                .build();
    }

    public static void updateEntity(AirlineRequest request, Airline existingAirline) {
        if (request == null || existingAirline == null) return;

        if (request.getIataCode() != null) {
            existingAirline.setIataCode(request.getIataCode());
        }
        if (request.getIcaoCode() != null) {
            existingAirline.setIcaoCode(request.getIcaoCode());
        }
        if (request.getName() != null) {
            existingAirline.setName(request.getName());
        }
        if (request.getAlias() != null) {
            existingAirline.setAlias(request.getAlias());
        }
        if (request.getLogoUrl() != null) {
            existingAirline.setLogoUrl(request.getLogoUrl());
        }
        if (request.getWebsite() != null) {
            existingAirline.setWebsite(request.getWebsite());
        }
        if (request.getStatus() != null) {
            existingAirline.setStatus(request.getStatus());
        }
        if (request.getAlliance() != null) {
            existingAirline.setAlliance(request.getAlliance());
        }
        if (request.getHeadquartersCityId() != null) {
            existingAirline.setHeadquartersCityId(request.getHeadquartersCityId());
        }

        if (existingAirline.getSupport() == null) {
            existingAirline.setSupport(new Support());
        }
        if (request.getSupportPhone() != null) {
            existingAirline.getSupport().setPhone(request.getSupportPhone());
        }
        if (request.getSupportHours() != null) {
            existingAirline.getSupport().setHours(request.getSupportHours());
        }
        if (request.getSupportEmail() != null) {
            existingAirline.getSupport().setEmail(request.getSupportEmail());
        }

    }
}

