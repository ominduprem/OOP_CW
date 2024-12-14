package com.ticketing.ticketdistributor.service;

import com.ticketing.ticketdistributor.model.TicketConfig;
import com.ticketing.ticketdistributor.repository.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class ConfigService {
    private final Logger logger = LoggerFactory.getLogger(ConfigService.class);

    @Autowired
    private ConfigRepository configRepo;

    private AtomicInteger remainingTickets = new AtomicInteger(0);
    @Autowired
    private ConfigRepository configRepository;

    public TicketConfig saveConfiguration(TicketConfig config) {
        validateConfig(config);
        remainingTickets.set(config.getTotalTickets());
        TicketConfig savedConfig = configRepository.save(config);
        logger.info("Configuration saved successfully: {}", savedConfig);
        return savedConfig;
    }

    public TicketConfig getLatestConfig() {
        return configRepository.findTopByOrderByIdDesc();
    }

    public int getRemainingTickets() {
        return remainingTickets.get();
    }

    public void decrementRemainingTickets(int count) {
        remainingTickets.updateAndGet(current -> Math.max(0, current - count));
    }

    private void validateConfig(TicketConfig config) {
        if (config.getTotalTickets() <= 0) {
            throw new IllegalArgumentException("Total tickets must be positive");
        }
        if (config.getMaxCapacity() <= 0 || config.getMaxCapacity() > config.getTotalTickets()) {
            throw new IllegalArgumentException("Invalid max capacity");
        }
        if (config.getNoVendors() <= 0) {
            throw new IllegalArgumentException("Must have at least one vendor");
        }
        if (config.getNoCustomers() <= 0) {
            throw new IllegalArgumentException("Must have at least one customer");
        }
        if (config.getReleaseRate() <= 0) {
            throw new IllegalArgumentException("Release rate must be positive");
        }
        if (config.getRetrievalRate() <= 0) {
            throw new IllegalArgumentException("Purchase rate must be positive");
        }
    }

    public void setRemainingTickets(int totalTickets) {
        remainingTickets.set(totalTickets);
    }
}