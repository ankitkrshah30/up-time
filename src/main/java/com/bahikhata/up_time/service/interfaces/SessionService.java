package com.bahikhata.up_time.service.interfaces;

import com.bahikhata.up_time.entity.User;
import com.bahikhata.up_time.entity.UserSession;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface SessionService {
    UserSession createSession(User user, HttpServletRequest request);
    void endActiveSession(User user);
    Duration getCurrentSessionDuration(User user);
    String getCurrentSessionFormattedDuration(User user);
    Duration getTotalUptime(User user, LocalDate fromDate,LocalDate toDate);
    List<UserSession> getRecentSessions(User user, int limit);
    void logActivity(User user, UserSession session, String activityType, String endpoint, String method);
    String getClientIpAddress(HttpServletRequest request);
}
