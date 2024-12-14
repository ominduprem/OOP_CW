package com.ticketing.ticketdistributor.component;

import com.ticketing.ticketdistributor.model.TicketStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class TicketPool {
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());
    private static final Logger logger = LogManager.getLogger(TicketPool.class);
    private int maxCapacity;
    private int totalTickets;
    private int soldTickets = 0;
    private volatile boolean isRunning = true;

    @Qualifier("ticketWebHandler")
    @Autowired
    private TicketWebHandler webSocketHandler;

    public void initialize(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        broadcastStatus();
    }

    public synchronized void addTicket(String ticket) {
        if (!isRunning || tickets.size() >= maxCapacity) return;

        tickets.add(ticket);
        logger.info("Ticket added: " + ticket);
        broadcastStatus();
    }

    public synchronized String removeTicket() {
        if (!isRunning || tickets.isEmpty()) return null;

        String ticket = tickets.remove(0);
        soldTickets++;
        logger.info("Ticket sold: " + ticket);

        if (soldTickets >= totalTickets) {
            isRunning = false;
        }

        broadcastStatus();
        return ticket;
    }

    private void broadcastStatus() {
        TicketStatus status = new TicketStatus(totalTickets, tickets.size(), soldTickets);
        webSocketHandler.broadcastStatus(status);
    }
}