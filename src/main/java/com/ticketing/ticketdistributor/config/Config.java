package com.ticketing.ticketdistributor.config;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_tickets")
    private int totalTickets;

    @Column(name = "max_capacity")
    private int maxCapacity;

    @Column(name = "vendor_count")
    private int vendorCount;

    @Column(name = "customer_count")
    private int customerCount;

    @Column(name = "release_rate")
    private int releaseRate;

    @Column(name = "purchase_rate")
    private int purchaseRate;

    // Getters and Setters
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

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getReleaseRate() {
        return releaseRate;
    }

    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    public int getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(int purchaseRate) {
        this.purchaseRate = purchaseRate;
    }
}