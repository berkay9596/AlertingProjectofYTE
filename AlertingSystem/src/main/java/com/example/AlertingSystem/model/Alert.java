package com.example.AlertingSystem.model;

import lombok.*;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.Set;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    @Id
    @GeneratedValue
    private Long id;
    private String alertName;
    private String url;
    private HttpMethod httpMethod;
    private Long period;
    private Long periodLeft;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "alertStatus_id")
    private Set<AlertStatus> alertStatuses;
}
