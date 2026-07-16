package com.sagar.controller;

import com.sagar.payload.request.AircraftRequest;
import com.sagar.payload.response.AircraftResponse;
import com.sagar.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aircrafts")
public class AircraftController {

    public final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @RequestBody @Valid AircraftRequest request,
            @RequestHeader("X-User-Id") Long ownerId) throws Exception {

        AircraftResponse aircraft = aircraftService.createAircraft(request, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.getAircraftById(id));
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponse>> getAllAircrafts(
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.listAllAircraftByOwnerId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long ownerId,
            @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.updateAircraft(id, request, ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        aircraftService.deleteAircraft(id, ownerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
