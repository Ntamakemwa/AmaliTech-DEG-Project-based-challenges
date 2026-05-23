package com.critmon.pulse_check_api;

public class MonitorNotFoundException extends RuntimeException {
    public MonitorNotFoundException(String id) {
        super("Monitor not found: " + id);
    }
}