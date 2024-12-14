package com.ticketing.ticketdistributor.config;

import com.ticketing.ticketdistributor.component.Customer;
import com.ticketing.ticketdistributor.component.TicketPool;
import com.ticketing.ticketdistributor.component.Vendor;
import com.ticketing.ticketdistributor.service.ConfigService;
import com.ticketing.ticketdistributor.component.TicketWebHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Bean
    @Scope("prototype")
    public Vendor vendor(TicketPool ticketPool, int releaseRate, int vendorId, ConfigService configService, TicketWebHandler ticketWebHandler) {
        return new Vendor(ticketPool, releaseRate, vendorId, configService, ticketWebHandler);
    }

    @Bean
    @Scope("prototype")
    public Customer customer(TicketPool ticketPool, int purchaseInterval, int customerId, TicketWebHandler ticketWebHandler) {
        return new Customer(ticketPool, purchaseInterval, customerId, ticketWebHandler);
    }
}