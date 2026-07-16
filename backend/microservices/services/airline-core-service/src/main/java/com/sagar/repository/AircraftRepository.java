package com.sagar.repository;

import com.sagar.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    List<Aircraft> findByAirlineId(Long airlineId);

    boolean existsByCode(String code);

    Aircraft findByIdAndAirlineId(Long id, Long airlineId);

}
