package com.sagar.payload.response;

import com.sagar.embeddable.Support;
import com.sagar.enums.AirlineStatus;
import com.sagar.payload.dto.UserDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AirlineResponse {

    private Long id;
    private String iataCode;
    private String icaoCode;
    private String name;
    private String alias;
    private String logoUrl;
    private String website;
    private AirlineStatus status;
    private String alliance;
    private Long ownerId;
    private UserDto owner;
    private Long updateById;
    private CityResponse headquartersCity;
    private Support support;
    private Instant createdAt;
    private Instant updatedAt;
}
