package com.sagar.controller;

import com.sagar.enums.AirlineStatus;
import com.sagar.payload.request.AirlineRequest;
import com.sagar.payload.response.AirlineDropdownItem;
import com.sagar.payload.response.AirlineResponse;
import com.sagar.payload.response.ApiResponse;
import com.sagar.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
@Slf4j
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<AirlineResponse> createAirline(
            @RequestBody @Valid AirlineRequest airlineRequest,
            @RequestHeader("X-User-Id") Long userId) {
        log.info("Request to create airline for ownerId: {}, request: {}", userId, airlineRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                airlineService.createAirline(airlineRequest, userId)
        );
    }

    @GetMapping("/admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwnerId(
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        log.info("Request to get airline for ownerId: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                airlineService.getAirlineByOwner(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getAirlineById(
            @PathVariable Long id) throws Exception {
        log.info("Request to get airline for id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(
                airlineService.getAirlineById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(Pageable pageable) {
        log.info("Request to get all airlines with pageable: {}", pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                airlineService.getAllAirlines(pageable));
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAirlinesForDropdown() {
        log.info("Request to get airlines for dropdown");
        return ResponseEntity.status(HttpStatus.OK).body(
                airlineService.getAirlineDropdownItems());
    }

    @PutMapping
    public ResponseEntity<AirlineResponse> updateAirline(
            @RequestBody @Valid AirlineRequest airlineRequest,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        log.info("Request to update airline for ownerId: {}, request: {}", userId, airlineRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                airlineService.updateAirline(airlineRequest, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirline(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        log.info("Request to delete airline with id: {} for ownerId: {}", id, userId);
        airlineService.deleteAirline(id, userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("Airline deleted successfully"));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<AirlineResponse> approveAirline(@PathVariable Long id) throws Exception {
        log.info("Request to approve airline with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(airlineService.changeStatus(id, AirlineStatus.ACTIVE));
    }

    @PostMapping("/{id}/suspend")
    public ResponseEntity<AirlineResponse> suspendAirline(@PathVariable Long id) throws Exception {
        log.info("Request to suspend airline with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(airlineService.changeStatus(id, AirlineStatus.INACTIVE));
    }

    @PostMapping("/{id}/ban")
    public ResponseEntity<AirlineResponse> banAirline(@PathVariable Long id) throws Exception {
        log.info("Request to ban airline with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(airlineService.changeStatus(id, AirlineStatus.BANNED));
    }
}
