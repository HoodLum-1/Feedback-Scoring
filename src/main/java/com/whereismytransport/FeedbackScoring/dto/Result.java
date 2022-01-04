package com.whereismytransport.FeedbackScoring.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Result {
    private String agencyId;
    private String routeName;
    private String dayOfWeek;
    private BigDecimal averageScore;

    @Override
    public String toString() {
        return String.format("%s - %s %s %s", agencyId, routeName, dayOfWeek, averageScore);
    }
}
