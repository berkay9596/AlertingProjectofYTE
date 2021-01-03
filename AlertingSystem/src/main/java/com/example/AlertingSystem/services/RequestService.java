package com.example.AlertingSystem.services;


import com.example.AlertingSystem.model.Alert;
import com.example.AlertingSystem.model.AlertStatus;
import com.example.AlertingSystem.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final AlertRepository alertRepository;

    private final RestTemplate restTemplate;

    @Async
    public void asyncRequestWorker(Alert alert, LocalDateTime requestingTime) {
        try {
            ResponseEntity<String> result = restTemplate.exchange(
                    alert.getUrl(),
                    alert.getHttpMethod(),
                    null,
                    String.class);
            AlertStatus alertStatus = new AlertStatus(null, requestingTime, 0);
            System.out.println("Request named: " + alert.getAlertName() + " returned: " + result.getStatusCode().toString());
            if (result.getStatusCode() == HttpStatus.OK) {
                alertStatus.setStatus(1);
            }
            alert.getAlertStatuses().add(alertStatus);
        } catch (Exception e) {
            System.err.println("Request named: " + alert.getAlertName() + " failed with: " + e.getMessage());
            AlertStatus alertStatus = new AlertStatus(null, requestingTime, 0);
            alert.getAlertStatuses().add(alertStatus);
        }
        alertRepository.save(alert);
    }

}