package com.ticketing.ticketdistributor.service;

import com.ticketing.ticketdistributor.component.Customer;
import com.ticketing.ticketdistributor.component.TicketPool;
import com.ticketing.ticketdistributor.component.Vendor;
import com.ticketing.ticketdistributor.component.TicketWebHandler;
import com.ticketing.ticketdistributor.model.ActivityLog;
import com.ticketing.ticketdistributor.model.TicketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    private boolean systemRunning = false;
    private Thread[] vendorThreads;
    private Thread[] customerThreads;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    private TicketWebHandler ticketWebHandler;

    @Autowired
    private ApplicationContext applicationContext;

    public void startSystem(TicketConfig config) {
        if (systemRunning) {
            logger.info("System is already running.");
            ticketWebHandler.broadcastActivity(
                    new ActivityLog("System", 0, "System is already running")
            );
            return;
        }

        if (!validateConfiguration(config)) {
            logger.warn("Invalid configuration");
            ticketWebHandler.broadcastActivity(
                    new ActivityLog("System", 0, "Invalid configuration")
            );
            return;
        }

        configService.setRemainingTickets(config.getTotalTickets());
        ticketPool.initialize(config.getMaxCapacity(), config.getTotalTickets());

        int ratePerVendor = Math.max(1, config.getReleaseRate() / config.getNoVendors());

        vendorThreads = new Thread[config.getNoVendors()];
        customerThreads = new Thread[config.getNoCustomers()];

        // Create Vendor threads using Spring context
        for (int i = 0; i < config.getNoVendors(); i++) {
            Vendor vendor = applicationContext.getBean(Vendor.class,
                    ticketPool, ratePerVendor, i + 1, configService, ticketWebHandler);
            vendorThreads[i] = new Thread(vendor);
            vendorThreads[i].start();
        }

        // Create Customer threads using Spring context
        for (int i = 0; i < config.getNoCustomers(); i++) {
            Customer customer = applicationContext.getBean(Customer.class,
                    ticketPool, 1000 / config.getRetrievalRate(), i + 1, ticketWebHandler);
            customerThreads[i] = new Thread(customer);
            customerThreads[i].start();
        }

        systemRunning = true;
        String startMessage = "System started with " + config.getTotalTickets() + " total tickets";
        logger.info(startMessage);
        ticketWebHandler.broadcastActivity(
                new ActivityLog("System", 0, startMessage)
        );
    }

    private boolean validateConfiguration(TicketConfig config) {
        if (config == null) return false;
        if (config.getTotalTickets() <= 0) return false;
        if (config.getMaxCapacity() <= 0 || config.getMaxCapacity() > config.getTotalTickets()) return false;
        if (config.getNoVendors() <= 0) return false;
        if (config.getNoCustomers() <= 0) return false;
        return true;
    }

    public void stopSystem() {
        if (!systemRunning) {
            logger.info("System is not running.");
            ticketWebHandler.broadcastActivity(
                    new ActivityLog("System", 0, "System is not running")
            );
            return;
        }

        if (vendorThreads != null) {
            for (Thread thread : vendorThreads) {
                if (thread != null && thread.isAlive()) {
                    thread.interrupt();
                }
            }
        }

        if (customerThreads != null) {
            for (Thread thread : customerThreads) {
                if (thread != null && thread.isAlive()) {
                    thread.interrupt();
                }
            }
        }

        systemRunning = false;
        ticketPool = null;
        vendorThreads = null;
        customerThreads = null;

        String stopMessage = "System stopped successfully";
        logger.info(stopMessage);
        ticketWebHandler.broadcastActivity(
                new ActivityLog("System", 0, stopMessage)
        );
    }

    public boolean isRunning() {
        return systemRunning;
    }
}