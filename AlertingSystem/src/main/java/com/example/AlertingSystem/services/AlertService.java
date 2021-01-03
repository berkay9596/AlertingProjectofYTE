package com.example.AlertingSystem.services;


import com.example.AlertingSystem.model.Alert;
import com.example.AlertingSystem.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.OrderBy;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    public void addAlert(Alert newAlert) {
        newAlert.setPeriodLeft(newAlert.getPeriod());
        this.alertRepository.save(newAlert);
    }

    public List<Alert> getAlerts() {
        return alertRepository.findAll(Sort.by("id"));
    }

    public Alert getOneById(Long id) {
        Optional<Alert> alert = alertRepository.findById(id);
        if (alert.isPresent()) {
            return alert.get();
        } else {
            return null;
        }
    }
}

  