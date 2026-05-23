package com.critmon.pulse_check_api.service;

import com.critmon.pulse_check_api.MonitorNotFoundException;
import com.critmon.pulse_check_api.model.Monitor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitorService {

    private final Map<String, Monitor> monitors = new ConcurrentHashMap<>();
    private final AlertService alertService;

    public MonitorService(AlertService alertService) {
        this.alertService = alertService;
    }

    public Monitor register(String id, int timeout, String alertEmail) {
        Monitor monitor = new Monitor(id, timeout, alertEmail);
        monitors.put(id, monitor);
        return monitor;
    }

    public Monitor heartbeat(String id) {
        Monitor monitor = monitors.get(id);
        if (monitor == null) throw new MonitorNotFoundException(id);
        monitor.setStatus("active");
        monitor.setLastHeartbeat(Instant.now());
        monitor.setExpiresAt(Instant.now().plusSeconds(monitor.getTimeout()));
        return monitor;
    }

    public Monitor pause(String id) {
        Monitor monitor = monitors.get(id);
        if (monitor == null) throw new MonitorNotFoundException(id);
        monitor.setStatus("paused");
        return monitor;
    }

    public Monitor getMonitor(String id) {
        Monitor monitor = monitors.get(id);
        if (monitor == null) throw new MonitorNotFoundException(id);
        return monitor;
    }

    public Collection<Monitor> getAllMonitors() {
        return monitors.values();
    }

    @Scheduled(fixedRate = 1000)
    public void checkExpiredMonitors() {
        monitors.values().forEach(monitor -> {
            if ("active".equals(monitor.getStatus()) &&
                    Instant.now().isAfter(monitor.getExpiresAt())) {
                monitor.setStatus("down");
                alertService.fireAlert(monitor);
            }
        });
    }
}
