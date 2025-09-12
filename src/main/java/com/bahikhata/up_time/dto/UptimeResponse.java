package com.bahikhata.up_time.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UptimeResponse {

    private String currentSessionDuration;
    private long currentSessionMinutes;
    private String totalTodayUptime;
    private long totalTodayMinutes;
    private String totalWeekUptime;
    private long totalWeekMinutes;
}
