package com.critmon.pulse_check_api.model;

import java.time.Instant;

public class Monitor {
    private String id;
    private int timeout;
    private String alertEmail;
    private String status; // active, down, paused
    private Instant lastHeartbeat;
    private Instant expiresAt;

    public Monitor(String id, int timeout, String alertEmail) {
        this.id = id;
        this.timeout = timeout;
        this.alertEmail = alertEmail;
        this.status = "active";
        this.lastHeartbeat = Instant.now();
        this.expiresAt = Instant.now().plusSeconds(timeout);
    }

    // Getters and Setters
    public String getId() { return id; }
    public int getTimeout() { return timeout; }
    public String getAlertEmail() { return alertEmail; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Instant getLastHeartbeat() { return lastHeartbeat; }
    public void setLastHeartbeat(Instant lastHeartbeat) { this.lastHeartbeat = lastHeartbeat; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
