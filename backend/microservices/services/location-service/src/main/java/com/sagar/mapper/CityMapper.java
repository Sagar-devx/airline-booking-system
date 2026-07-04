package com.sagar.mapper;

import com.sagar.model.City;
import com.sagar.payload.request.CityRequest;
import com.sagar.payload.response.CityResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class CityMapper {

    public static City toEntity(CityRequest request)
    {
          if(request == null) return null;

          return City.builder()
                  .name(request.getName())
                  .cityCode(request.getCityCode())
                  .countryCode(request.getCountryCode())
                  .countryName(request.getCountryName())
                  .regionCode(request.getRegionCode())
                  .timeZoneId(request.getTimeZoneId())
                  .build();
    }

    public static CityResponse toResponse(City city)
    {
        if(city == null) return null;
        String offsetStr = null;
        if (city.getTimeZoneId() != null && !city.getTimeZoneId().isBlank())
        {
            try
            {
                ZoneId zone = ZoneId.of(city.getTimeZoneId().trim());
                ZoneOffset offset = zone.getRules().getOffset(Instant.now());
                offsetStr = offset.getId();
            }
            catch (Exception e)
            {
                offsetStr = city.getTimeZoneId();
            }
        }
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .cityCode(city.getCityCode())
                .countryCode(city.getCountryCode())
                .countryName(city.getCountryName())
                .regionCode(city.getRegionCode())
                .timeZoneOffset(offsetStr)
                .build();
    }

    public static City updateEntity(City city, CityRequest request) {
        if (request.getName() != null) {
            city.setName(request.getName().trim());
        }

        if (request.getCityCode() != null) {
            city.setCityCode(request.getCityCode().toUpperCase().trim());
        }

        if (request.getCountryCode() != null) {
            city.setCountryCode(request.getCountryCode().toUpperCase().trim());
        }

        if (request.getCountryName() != null) {
            city.setCountryName(request.getCountryName().trim());
        }

        if (request.getRegionCode() != null) {
            city.setRegionCode(request.getRegionCode().toUpperCase().trim());
        }

        if (request.getTimeZoneId() != null) {
            city.setTimeZoneId(request.getTimeZoneId().trim());
        }

        return city;
    }

}
