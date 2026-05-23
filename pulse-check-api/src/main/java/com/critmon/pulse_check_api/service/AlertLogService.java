package com.critmon.pulse_check_api.service;

import com.critmon.pulse_check_api.model.AlertLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AlertLogService {

    private final Map<String, List<AlertLog>> logs = new ConcurrentHashMap<>();

    public void log(String deviceId, String message) {
        logs.computeIfAbsent(deviceId, k -> new ArrayList<>())
                .add(new AlertLog(deviceId, message));
    }

    public List<AlertLog> getHistory(String deviceId) {
        return logs.getOrDefault(deviceId, Collections.emptyList());
    }
}