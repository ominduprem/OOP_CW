package com.ticketing.ticketdistributor.controller;

import com.ticketing.ticketdistributor.model.TicketConfig;
import com.ticketing.ticketdistributor.service.ConfigService;
import com.ticketing.ticketdistributor.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
@CrossOrigin
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private ConfigService configService;

    @PostMapping("/start")
    public ResponseEntity<String> startSystem() {
        try {
            TicketConfig config = configService.getLatestConfig();
            if (config == null) {
                return ResponseEntity.badRequest().body("No configuration found");
            }
            ticketService.startSystem(config);
            return ResponseEntity.ok("System started successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to start system: " + e.getMessage());
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        try {
            ticketService.stopSystem();
            return ResponseEntity.ok("System stopped successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to stop system: " + e.getMessage());
        }
    }
}
