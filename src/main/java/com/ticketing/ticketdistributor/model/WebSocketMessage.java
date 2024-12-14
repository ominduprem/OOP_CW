package com.ticketing.ticketdistributor.model;

public class WebSocketMessage {
    private final String type;
    private final Object payload;

    public WebSocketMessage(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    // Getters
    public String getType() { return type; }
    public Object getPayload() { return payload; }
}