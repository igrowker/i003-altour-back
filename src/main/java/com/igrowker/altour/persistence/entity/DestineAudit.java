package com.igrowker.altour.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DestineAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateTime;
    private Double lat;
    private Double lng;
    private Integer maxDistance;
    private String preference;
    private Integer maxCrowdLevel;
    private Integer busyMin;
    private Boolean cachedResult;
}
