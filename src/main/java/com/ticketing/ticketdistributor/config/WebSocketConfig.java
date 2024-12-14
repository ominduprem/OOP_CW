package com.ticketing.ticketdistributor.config;

import com.ticketing.ticketdistributor.component.TicketWebHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Bean
    public TicketWebHandler ticketWebSocketHandler() {
        return new TicketWebHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ticketWebSocketHandler(), "/ws/tickets")
                .setAllowedOrigins("*");  // Configure appropriately for production
    }
}

