package com.bahikhata.up_time.controller;


import com.bahikhata.up_time.dto.UptimeResponse;
import com.bahikhata.up_time.entity.User;
import com.bahikhata.up_time.entity.UserSession;
import com.bahikhata.up_time.service.interfaces.SessionService;
import com.bahikhata.up_time.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    private final UserService userService;
    private final SessionService sessionService;

    public DashboardController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/uptime")
    @PreAuthorize( "hasRole('USER')")
    public ResponseEntity<UptimeResponse> getCurrentUptime(Authentication authentication){
        User user=userService.findByUsername(authentication.getName());
        Duration currentSessionDuration=sessionService.getCurrentSessionDuration(user);
        Duration todayUptime=sessionService.getTotalUptime(user, LocalDate.now(),LocalDate.now());
        Duration weekUptime=sessionService.getTotalUptime(user, LocalDate.now().minusDays(6),LocalDate.now());
        UptimeResponse response=new UptimeResponse();
        response.setCurrentSessionDuration(formatDuration(currentSessionDuration));
        response.setCurrentSessionMinutes(currentSessionDuration.toMinutes());
        response.setTotalTodayMinutes(todayUptime.toMinutes());
        response.setTotalTodayUptime(formatDuration(todayUptime));
        response.setTotalWeekMinutes(weekUptime.toMinutes());
        response.setTotalWeekUptime(formatDuration(weekUptime));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sessions/recent")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserSession>> getRecentSessions(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<UserSession> sessions = sessionService.getRecentSessions(user, 10);

        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/uptime/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UptimeResponse> getUserUptimeByUsername(@PathVariable String username) {
        try {
            User target = userService.findByUsername(username);
            Duration currentSessionDuration = sessionService.getCurrentSessionDuration(target);
            Duration todayUptime = sessionService.getTotalUptime(target, LocalDate.now(), LocalDate.now());
            Duration weekUptime = sessionService.getTotalUptime(target, LocalDate.now().minusDays(6), LocalDate.now());

            UptimeResponse response = new UptimeResponse();
            response.setCurrentSessionDuration(formatDuration(currentSessionDuration));
            response.setCurrentSessionMinutes(currentSessionDuration.toMinutes());
            response.setTotalTodayMinutes(todayUptime.toMinutes());
            response.setTotalTodayUptime(formatDuration(todayUptime));
            response.setTotalWeekMinutes(weekUptime.toMinutes());
            response.setTotalWeekUptime(formatDuration(weekUptime));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String formatDuration(Duration duration){
        long hours=duration.toHours();
        long minutes=duration.toMinutesPart();
        long seconds=duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
