package com.critmon.pulse_check_api.service;

import com.critmon.pulse_check_api.model.Monitor;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class AlertService {
    public void fireAlert(Monitor monitor) {
        System.out.println("{\"ALERT\": \"Device " + monitor.getId() + " is down!\", \"time\": \"" + Instant.now() + "\", \"email\": \"" + monitor.getAlertEmail() + "\"}");
    }
}
