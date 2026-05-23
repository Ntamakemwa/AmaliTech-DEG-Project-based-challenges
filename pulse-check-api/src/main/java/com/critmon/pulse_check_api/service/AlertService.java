package com.critmon.pulse_check_api.service;

import com.critmon.pulse_check_api.model.Monitor;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class AlertService {

    private final AlertLogService alertLogService;

    public AlertService(AlertLogService alertLogService) {
        this.alertLogService = alertLogService;
    }

    public void fireAlert(Monitor monitor) {
        String message = "Device " + monitor.getId() + " is down!";
        System.out.println("{\"ALERT\": \"" + message + "\", \"time\": \"" + Instant.now() + "\", \"email\": \"" + monitor.getAlertEmail() + "\"}");
        alertLogService.log(monitor.getId(), message);
    }
}
