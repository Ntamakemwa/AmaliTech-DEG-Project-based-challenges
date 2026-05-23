package com.critmon.pulse_check_api.model;

import java.time.Instant;

public class AlertLog {
    private String deviceId;
    private String message;
    private Instant timestamp;

    public AlertLog(String deviceId, String message) {
        this.deviceId = deviceId;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public String getDeviceId() { return deviceId; }
    public String getMessage() { return message; }
    public Instant getTimestamp() { return timestamp; }
}

