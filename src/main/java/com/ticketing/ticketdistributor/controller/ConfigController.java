package com.ticketing.ticketdistributor.controller;

import com.ticketing.ticketdistributor.model.TicketConfig;
import com.ticketing.ticketdistributor.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configure")
@CrossOrigin
public class ConfigController {
    private final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ConfigService configService;

    @PostMapping("/add")
    public ResponseEntity<?> addConfig(@RequestBody TicketConfig config) {
        try {
            logger.info("Received configuration request: {}", config);
            TicketConfig savedConfig = configService.saveConfiguration(config);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            logger.error("Error saving configuration: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/totTickets")
    public int getStatus() {
        TicketConfig config = configService.getLatestConfig();
        if (config != null) {
            return config.getTotalTickets();
        } else {
            return 0;
        }
    }
}