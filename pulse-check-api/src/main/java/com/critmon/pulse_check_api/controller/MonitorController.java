package com.critmon.pulse_check_api.controller;

import com.critmon.pulse_check_api.model.Monitor;
import com.critmon.pulse_check_api.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/monitors")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping
    public ResponseEntity<Monitor> register(@RequestBody Map<String, Object> body) {
        String id = (String) body.get("id");
        int timeout = (Integer) body.get("timeout");
        String alertEmail = (String) body.get("alert_email");
        Monitor monitor = monitorService.register(id, timeout, alertEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(monitor);
    }

    @PostMapping("/{id}/heartbeat")
    public ResponseEntity<Monitor> heartbeat(@PathVariable String id) {
        Monitor monitor = monitorService.heartbeat(id);
        return ResponseEntity.ok(monitor);
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<Monitor> pause(@PathVariable String id) {
        Monitor monitor = monitorService.pause(id);
        return ResponseEntity.ok(monitor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monitor> getMonitor(@PathVariable String id) {
        Monitor monitor = monitorService.getMonitor(id);
        return ResponseEntity.ok(monitor);
    }

    @GetMapping
    public ResponseEntity<Collection<Monitor>> getAllMonitors() {
        return ResponseEntity.ok(monitorService.getAllMonitors());
    }
}
