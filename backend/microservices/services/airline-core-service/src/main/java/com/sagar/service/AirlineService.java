
package com.sagar.service;

import com.sagar.enums.AirlineStatus;
import com.sagar.payload.request.AirlineRequest;
import com.sagar.payload.response.AirlineDropdownItem;
import com.sagar.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId);

    AirlineResponse getAirlineByOwner(Long ownerId) throws Exception;

    AirlineResponse updateAirline(AirlineRequest airlineRequest, Long ownerId) throws Exception;

    AirlineResponse getAirlineById(Long id) throws Exception;

    Page<AirlineResponse> getAllAirlines(Pageable pageable);

    void deleteAirline(Long id, Long ownerId) throws Exception;

    AirlineResponse changeStatus(Long airlineId, AirlineStatus status) throws Exception;

    List<AirlineDropdownItem> getAirlineDropdownItems();

}
