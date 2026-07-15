package com.sagar.model;

import com.sagar.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, length = 50)
    private String manufacturer;

    @Column(nullable = false)
    private Integer seatingCapacity;

    @Column(name = "business_seats")
    @Builder.Default
    private Integer businessSeats = 0;

    @Column(name = "economy_seats")
    @Builder.Default
    private Integer economySeats = 0;

    @Column(name = "premium_economy_seats")
    @Builder.Default
    private Integer premiumEconomySeats = 0;

    @Column(name = "first_class_seats")
    @Builder.Default
    private Integer firstClassSeats = 0;

    @Column(name = "cruising_speed_kmh")
    private Integer cruisingSpeedKmh;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;

    private LocalDate nextMaintenanceDate;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AircraftStatus status = AircraftStatus.ACTIVE;

    @Builder.Default
    private Boolean isAvailable = true;

    @ManyToOne
    @ToString.Exclude
    private Airline airline;

    private Long currentAirportId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = true)
    private Instant updatedAt;

    public Integer getTotalSeats() {
        return (economySeats != null ? economySeats : 0)
                + (premiumEconomySeats != null ? premiumEconomySeats : 0)
                + (businessSeats != null ? businessSeats : 0)
                + (firstClassSeats != null ? firstClassSeats : 0);
    }

    public boolean isOperational() {
        return AircraftStatus.ACTIVE.equals(status)
                && Boolean.TRUE.equals(isAvailable);
    }

    public Boolean requiresMaintenance() {
        return nextMaintenanceDate != null && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }
}
