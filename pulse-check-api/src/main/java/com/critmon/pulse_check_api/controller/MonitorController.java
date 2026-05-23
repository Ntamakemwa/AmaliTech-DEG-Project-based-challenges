package com.critmon.pulse_check_api.controller;

import com.critmon.pulse_check_api.model.AlertLog;
import com.critmon.pulse_check_api.model.Monitor;
import com.critmon.pulse_check_api.service.AlertLogService;
import com.critmon.pulse_check_api.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/monitors")
public class MonitorController {

    private final MonitorService monitorService;
    private final AlertLogService alertLogService;

    public MonitorController(MonitorService monitorService, AlertLogService alertLogService) {
        this.monitorService = monitorService;
        this.alertLogService = alertLogService;
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

    @GetMapping("/{id}/history")
    public ResponseEntity<List<AlertLog>> getHistory(@PathVariable String id) {
        return ResponseEntity.ok(alertLogService.getHistory(id));
    }
}