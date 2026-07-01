package com.sagar.service.impl;

import com.sagar.mapper.CityMapper;
import com.sagar.model.City;
import com.sagar.payload.request.CityRequest;
import com.sagar.payload.response.CityResponse;
import com.sagar.repository.CityRepository;
import com.sagar.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService
{
    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) throws Exception {
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
        City city = cityRepository.findById(id).orElseThrow(() -> new Exception("City not found with give id: " + id));
        return CityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest request) throws Exception {
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
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("City not found with given id: " + id)
        );
        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
       return cityRepository.searchByKeyword(keyword, pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode, pageable).map(CityMapper::toResponse);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }
}
