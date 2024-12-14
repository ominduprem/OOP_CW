package com.ticketing.ticketdistributor.model;

import java.time.LocalDateTime;

public class ActivityLog {
    private final String actorType;
    private final int actorId;
    private final String message;
    private final String timestamp;

    public ActivityLog(String actorType, int actorId, String message) {
        this.actorType = actorType;
        this.actorId = actorId;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Getters
    public String getActorType() { return actorType; }
    public int getActorId() { return actorId; }
    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }
}

