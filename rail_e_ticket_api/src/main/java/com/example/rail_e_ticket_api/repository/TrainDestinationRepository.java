package com.example.rail_e_ticket_api.repository;

import com.example.rail_e_ticket_api.entity.TrainDestination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.management.OperatingSystemMXBean;
import java.util.Date;
import java.util.Optional;

public interface TrainDestinationRepository extends JpaRepository<TrainDestination, Long> {
    Optional<TrainDestination> findByDepartureDateAndArriveDateAndPriceId(Date departureDate, Date arriveDate, Long priceId);
}
