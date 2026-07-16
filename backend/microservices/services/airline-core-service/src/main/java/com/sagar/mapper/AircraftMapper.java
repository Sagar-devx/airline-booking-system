package com.sagar.mapper;

import com.sagar.enums.AircraftStatus;
import com.sagar.model.Aircraft;
import com.sagar.model.Airline;
import com.sagar.payload.request.AircraftRequest;
import com.sagar.payload.response.AircraftResponse;

public class AircraftMapper {

    public static Aircraft toEntity(AircraftRequest request, Airline airline) {
        if (request == null) return null;

        return Aircraft.builder()
                .code(request.getCode())
                .model(request.getModel())
                .manufacturer(request.getManufacturer())
                .seatingCapacity(request.getSeatingCapacity())
                .economySeats(request.getEconomySeats() != null ? request.getEconomySeats() : 0)
                .premiumEconomySeats(request.getPremiumEconomySeats() != null ? request.getPremiumEconomySeats() : 0)
                .businessSeats(request.getBusinessSeats() != null ? request.getBusinessSeats() : 0)
                .firstClassSeats(request.getFirstClassSeats() != null ? request.getFirstClassSeats() : 0)
                .rangeKm(request.getRangeKm())
                .cruisingSpeedKmh(request.getCruisingSpeedKmh())
                .maxAltitudeFt(request.getMaxAltitudeFt())
                .yearOfManufacture(request.getYearOfManufacture())
                .registrationDate(request.getRegistrationDate())
                .nextMaintenanceDate(request.getNextMaintenanceDate())
                .status(request.getStatus() != null ? request.getStatus() : AircraftStatus.ACTIVE)
                .isAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true)
                .airline(airline)
                .currentAirportId(request.getCurrentAirportId())
                .build();
    }

    public static AircraftResponse toResponse(Aircraft aircraft) {
        if (aircraft == null) return null;

        AircraftResponse.AircraftResponseBuilder builder = AircraftResponse.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .model(aircraft.getModel())
                .manufacturer(aircraft.getManufacturer())
                .seatingCapacity(aircraft.getSeatingCapacity())
                .economySeats(aircraft.getEconomySeats())
                .premiumEconomySeats(aircraft.getPremiumEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .rangeKm(aircraft.getRangeKm())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .maxAltitudeFt(aircraft.getMaxAltitudeFt())
                .yearOfManufacture(aircraft.getYearOfManufacture())
                .registrationDate(aircraft.getRegistrationDate())
                .nextMaintenanceDate(aircraft.getNextMaintenanceDate())
                .status(aircraft.getStatus())
                .isAvailable(aircraft.getIsAvailable())
                .currentAirportId(aircraft.getCurrentAirportId())
                .totalSeats(aircraft.getTotalSeats())
                .requiresMaintenance(aircraft.requiresMaintenance())
                .isOperational(aircraft.isOperational())
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt());

        if (aircraft.getAirline() != null) {
            builder.airlineId(aircraft.getAirline().getId())
                    .airlineName(aircraft.getAirline().getName())
                    .airlineIataCode(aircraft.getAirline().getIataCode());
        }

        return builder.build();
    }

    public static void updateEntity(AircraftRequest request, Aircraft existingAircraft) {
        if (request == null || existingAircraft == null) return;

        if (request.getCode() != null) {
            existingAircraft.setCode(request.getCode());
        }
        if (request.getModel() != null) {
            existingAircraft.setModel(request.getModel());
        }
        if (request.getManufacturer() != null) {
            existingAircraft.setManufacturer(request.getManufacturer());
        }
        if (request.getSeatingCapacity() != null) {
            existingAircraft.setSeatingCapacity(request.getSeatingCapacity());
        }
        if (request.getEconomySeats() != null) {
            existingAircraft.setEconomySeats(request.getEconomySeats());
        }
        if (request.getPremiumEconomySeats() != null) {
            existingAircraft.setPremiumEconomySeats(request.getPremiumEconomySeats());
        }
        if (request.getBusinessSeats() != null) {
            existingAircraft.setBusinessSeats(request.getBusinessSeats());
        }
        if (request.getFirstClassSeats() != null) {
            existingAircraft.setFirstClassSeats(request.getFirstClassSeats());
        }
        if (request.getRangeKm() != null) {
            existingAircraft.setRangeKm(request.getRangeKm());
        }
        if (request.getCruisingSpeedKmh() != null) {
            existingAircraft.setCruisingSpeedKmh(request.getCruisingSpeedKmh());
        }
        if (request.getMaxAltitudeFt() != null) {
            existingAircraft.setMaxAltitudeFt(request.getMaxAltitudeFt());
        }
        if (request.getYearOfManufacture() != null) {
            existingAircraft.setYearOfManufacture(request.getYearOfManufacture());
        }
        if (request.getRegistrationDate() != null) {
            existingAircraft.setRegistrationDate(request.getRegistrationDate());
        }
        if (request.getNextMaintenanceDate() != null) {
            existingAircraft.setNextMaintenanceDate(request.getNextMaintenanceDate());
        }
        if (request.getStatus() != null) {
            existingAircraft.setStatus(request.getStatus());
        }
        if (request.getIsAvailable() != null) {
            existingAircraft.setIsAvailable(request.getIsAvailable());
        }
        if (request.getCurrentAirportId() != null) {
            existingAircraft.setCurrentAirportId(request.getCurrentAirportId());
        }
    }
}
