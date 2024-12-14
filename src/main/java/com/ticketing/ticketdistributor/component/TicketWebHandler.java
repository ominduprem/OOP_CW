package com.ticketing.ticketdistributor.component;

import com.ticketing.ticketdistributor.model.ActivityLog;
import com.ticketing.ticketdistributor.model.TicketStatus;
import com.ticketing.ticketdistributor.model.WebSocketMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TicketWebHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(TicketWebHandler.class);
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
        logger.info("WebSocket connection established: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
        logger.info("WebSocket connection closed: {}", session.getId());
    }

    public void broadcastStatus(TicketStatus status) {
        WebSocketMessage message = new WebSocketMessage("STATUS", status);
        broadcast(message);
    }

    public void broadcastActivity(ActivityLog activity) {
        WebSocketMessage message = new WebSocketMessage("ACTIVITY", activity);
        broadcast(message);
    }

    private void broadcast(WebSocketMessage message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            TextMessage textMessage = new TextMessage(jsonMessage);

            for (WebSocketSession session : sessions.values()) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(textMessage);
                        logger.debug("Message sent to session {}: {}", session.getId(), jsonMessage);
                    } catch (IOException e) {
                        logger.error("Error sending message to session {}: {}", session.getId(), e.getMessage());
                        sessions.remove(session.getId());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error broadcasting message: {}", e.getMessage());
        }
    }
}