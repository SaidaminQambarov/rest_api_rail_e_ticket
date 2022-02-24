package com.example.rail_e_ticket_api.repository;

import com.example.rail_e_ticket_api.entity.TrainDestination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface TrainDestinationRepository extends JpaRepository<TrainDestination, UUID> {
    Optional<TrainDestination> findByDepartureDateAndArriveDate(LocalDateTime departureDate, LocalDateTime arriveDate);
}
