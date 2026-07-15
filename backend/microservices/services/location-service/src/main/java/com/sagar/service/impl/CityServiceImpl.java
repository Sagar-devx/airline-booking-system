package com.sagar.service.impl;

import com.sagar.mapper.CityMapper;
import com.sagar.model.City;
import com.sagar.payload.request.CityRequest;
import com.sagar.payload.response.CityResponse;
import com.sagar.repository.CityRepository;
import com.sagar.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService
{
    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) throws Exception {
        log.info("Creating city with code: {}", request.getCityCode());
        if(cityRepository.existsByCityCode(request.getCityCode()))
        {
            throw new Exception("city with given code already exists");
        }
        City city = CityMapper.toEntity(request);
        City result = cityRepository.save(city);
        return CityMapper.toResponse(result);

    }

    @Override
    public CityResponse getCityById(Long id) throws Exception {
        log.info("Fetching city with id: {}", id);
        City city = cityRepository.findById(id).orElseThrow(() -> new Exception("City not found with give id: " + id));
        return CityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest request) throws Exception {
        log.info("Updating city with id: {}", id);
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("City not found with given id: " + id)
        );
        if(cityRepository.existsByCityCodeAndIdNot(request.getCityCode(), id))
        {
            throw new Exception("city with given code already exists");
        }
        City updatedCity = cityRepository.save(CityMapper.updateEntity(city,request));
        return CityMapper.toResponse(updatedCity);
    }

    @Override
    public void deleteCityById(Long id) throws Exception {
        log.info("Deleting city with id: {}", id);
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("City not found with given id: " + id)
        );
        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        log.info("Fetching all cities with pageable: {}", pageable);
        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        log.info("Searching cities for keyword: {}", keyword);
       return cityRepository.searchByKeyword(keyword, pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        log.info("Fetching cities for countryCode: {}", countryCode);
        return cityRepository.findByCountryCodeIgnoreCase(countryCode, pageable).map(CityMapper::toResponse);
    }

    @Override
    public boolean cityExists(String cityCode) {
        log.info("Checking if city exists with code: {}", cityCode);
        return cityRepository.existsByCityCode(cityCode);
    }
}
