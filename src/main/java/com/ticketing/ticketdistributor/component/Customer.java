package com.ticketing.ticketdistributor.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ticketing.ticketdistributor.model.ActivityLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Customer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);
    private final TicketPool ticketPool;
    private final int retrievalInterval;
    private final int customerId;
    private final TicketWebHandler ticketWebHandler;


    public Customer(TicketPool ticketPool,
                    int retrievalInterval,
                    int customerId,
                    TicketWebHandler ticketWebHandler) {
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
        this.customerId = customerId;
        this.ticketWebHandler = ticketWebHandler;
    }

    private void sendActivityMessage(String message) {
        ActivityLog activity = new ActivityLog(
                "Customer",
                customerId,
                message
        );
        ticketWebHandler.broadcastActivity(activity);
        logger.info("Customer {}: {}", customerId, message);
    }

    @Override
    public void run() {
        try {
            sendActivityMessage("Started customer service");

            while (!Thread.interrupted()) {
                String ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    sendActivityMessage("Retrieved ticket: " + ticket);
                } else {
                    sendActivityMessage("Waiting for tickets...");
                }
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            sendActivityMessage("Service interrupted");
            Thread.currentThread().interrupt();
        }
    }
}