package com.ticketing.ticketdistributor.repository;

import com.ticketing.ticketdistributor.model.TicketConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<TicketConfig, Long> {
    TicketConfig findTopByOrderByIdDesc();
}