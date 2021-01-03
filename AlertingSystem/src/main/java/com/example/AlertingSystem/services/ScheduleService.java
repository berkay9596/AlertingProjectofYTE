package com.example.AlertingSystem.services;
import com.example.AlertingSystem.model.Alert;
import com.example.AlertingSystem.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final AlertRepository alertRepository;

    private final RequestService requestService;

    @Scheduled(fixedDelay = 1000)
    public void scheduleAlerts() {
        List<Alert> alerts= alertRepository.findAll(Sort.by("id"));
        LocalDateTime requestingTime = LocalDateTime.now();
        if (alerts.size() > 0) {
            alerts.forEach(alert -> {
                if (alert.getPeriodLeft() == 0L) {
                    alert.setPeriodLeft(alert.getPeriod());
                    requestService.asyncRequestWorker(alert, requestingTime);
                } else {
                    alert.setPeriodLeft(alert.getPeriodLeft() - 1L);
                    alertRepository.save(alert);
                }
            });
        }
    }
}
