// Vendor.java
package com.ticketing.ticketdistributor.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ticketing.ticketdistributor.model.ActivityLog;
import com.ticketing.ticketdistributor.service.ConfigService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Vendor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Vendor.class);
    private final TicketPool ticketPool;
    private final int releaseRate;
    private final int vendorId;
    private final ConfigService configService;
    private final TicketWebHandler ticketWebHandler;

    public Vendor(TicketPool ticketPool,
                  int releaseRate,
                  int vendorId,
                  ConfigService configService,
                  TicketWebHandler ticketWebHandler) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.vendorId = vendorId;
        this.configService = configService;
        this.ticketWebHandler = ticketWebHandler;
    }

    private void sendActivityMessage(String message) {
        ActivityLog activity = new ActivityLog(
                "Vendor",
                vendorId,
                message
        );
        ticketWebHandler.broadcastActivity(activity);
        logger.info("Vendor {}: {}", vendorId, message);
    }

    @Override
    public void run() {
        int ticketCount = 1;
        try {
            sendActivityMessage("Started vendor service");

            while (configService.getRemainingTickets() > 0 && !Thread.interrupted()) {
                int actualReleaseRate = Math.min(releaseRate, configService.getRemainingTickets());
                if (actualReleaseRate > 0) {
                    for (int i = 0; i < actualReleaseRate; i++) {
                        String ticketNumber = String.format("TICKET-V%d-%d", vendorId, ticketCount++);
                        ticketPool.addTicket(ticketNumber);
                    }
                    configService.decrementRemainingTickets(actualReleaseRate);
                    sendActivityMessage("Released " + actualReleaseRate + " tickets. Remaining: " + configService.getRemainingTickets());
                }
                Thread.sleep(1000);
            }

            sendActivityMessage("Finished - no more tickets to release");

        } catch (InterruptedException e) {
            sendActivityMessage("Service interrupted");
            Thread.currentThread().interrupt();
        }
    }
}