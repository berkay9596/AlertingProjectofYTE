package com.example.AlertingSystem.controllers;

import com.example.AlertingSystem.model.Alert;
import com.example.AlertingSystem.services.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/alerts")
    public List<Alert> getAlert() {
        return alertService.getAlerts();
    }

    @PostMapping("/alerts")
    public void addAlert(@RequestBody Alert alert) {
        alertService.addAlert(alert);
    }

    @GetMapping("/alert/{id}")
    public Alert getAlertById(@PathVariable Long id) {
        return alertService.getOneById(id);

     
    }
}
