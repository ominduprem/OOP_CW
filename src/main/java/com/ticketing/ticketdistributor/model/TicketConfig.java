package com.ticketing.ticketdistributor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_config")
public class TicketConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalTickets;
    private int maxCapacity;
    private int noVendors;
    private int noCustomers;
    private int releaseRate;
    private int retrievalRate;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getNoVendors() {
        return noVendors;
    }

    public void setNoVendors(int noVendors) {
        this.noVendors = noVendors;
    }

    public int getNoCustomers() {
        return noCustomers;
    }

    public void setNoCustomers(int noCustomers) {
        this.noCustomers = noCustomers;
    }

    public int getReleaseRate() {
        return releaseRate;
    }

    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    public int getRetrievalRate() {
        return retrievalRate;
    }

    public void setRetrievalRate(int retrievalRate) {
        this.retrievalRate = retrievalRate;
    }
}