package com.ticketing.ticketdistributor.model;

import java.time.LocalDateTime;

public class TicketStatus {
    private final int totalTickets;
    private final int availableTickets;
    private final int soldTickets;
    private final String timestamp;

    public TicketStatus(int totalTickets, int availableTickets, int soldTickets) {
        this.totalTickets = totalTickets;
        this.availableTickets = availableTickets;
        this.soldTickets = soldTickets;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Getters
    public int getTotalTickets() { return totalTickets; }
    public int getAvailableTickets() { return availableTickets; }
    public int getSoldTickets() { return soldTickets; }
    public String getTimestamp() { return timestamp; }
}
