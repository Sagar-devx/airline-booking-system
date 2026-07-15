package com.sagar.controller;

import com.sagar.payload.request.CityRequest;
import com.sagar.payload.response.ApiResponse;
import com.sagar.payload.response.CityResponse;
import com.sagar.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
@Slf4j
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest) throws Exception {
        log.info("Request to create city: {}", cityRequest);
        CityResponse response = cityService.createCity(cityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id) throws Exception {
        log.info("Request to get city for id: {}", id);
        CityResponse response = cityService.getCityById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ){
        log.info("Request to get all cities with page: {}, size: {}, sortBy: {}, sortDirection: {}", page, size, sortBy, sortDirection);
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(cityService.getAllCities(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(@PathVariable Long id, @Valid @RequestBody CityRequest cityRequest) throws Exception {
        log.info("Request to update city for id: {}, request: {}", id, cityRequest);
        CityResponse response = cityService.updateCity(id, cityRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCity(@PathVariable Long id) throws Exception {
        log.info("Request to delete city with id: {}", id);
        cityService.deleteCityById(id);
        return ResponseEntity.ok(new ApiResponse("City deleted successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        log.info("Request to search cities with keyword: {}, page: {}, size: {}", keyword, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cityService.searchCities(keyword, pageable));
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        log.info("Request to get cities by countryCode: {}, page: {}, size: {}", countryCode, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cityService.getCitiesByCountryCode(countryCode, pageable));
    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(@PathVariable String cityCode) {
        log.info("Request to check if city exists with code: {}", cityCode);
        return ResponseEntity.ok(cityService.cityExists(cityCode.toUpperCase()));
    }
}
