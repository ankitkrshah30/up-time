package com.bahikhata.up_time.service.impl;

import com.bahikhata.up_time.entity.User;
import com.bahikhata.up_time.entity.UserActivity;
import com.bahikhata.up_time.entity.UserSession;
import com.bahikhata.up_time.repository.UserActivityRepository;
import com.bahikhata.up_time.repository.UserSessionRepository;
import com.bahikhata.up_time.service.interfaces.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private final UserSessionRepository sessionRepository;
    private final UserActivityRepository activityRepository;

    public SessionServiceImpl(UserSessionRepository sessionRepository, UserActivityRepository activityRepository) {
        this.sessionRepository = sessionRepository;
        this.activityRepository = activityRepository;
    }

    public UserSession createSession(User user, HttpServletRequest request){
        endActiveSession(user);
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String sessionId = UUID.randomUUID().toString();

        UserSession session=new UserSession(user,sessionId,ipAddress,userAgent);
        session=sessionRepository.save(session);
        logActivity(user,session,"LOGIN","/api/auth/login","POST");
        return session;
    }

    public void endActiveSession(User user) {
        List<UserSession> activeSessions = sessionRepository.findByUserAndActiveTrue(user);

        LocalDateTime now = LocalDateTime.now();
        for (UserSession session : activeSessions) {
            session.setLogoutTime(now);
            session.setActive(false);
            sessionRepository.save(session);

            // Log logout activity
            logActivity(user, session, "LOGOUT", "/api/auth/logout", "POST");
        }
    }

    public Duration getCurrentSessionDuration(User user) {
        return sessionRepository.findByUserAndActiveTrue(user)
                .stream()
                .findFirst()
                .map(UserSession::getCurrentDuration)
                .orElse(Duration.ZERO);
    }

    public String getCurrentSessionFormattedDuration(User user) {
        return sessionRepository.findByUserAndActiveTrue(user)
                .stream()
                .findFirst()
                .map(UserSession::getFormattedDuration)
                .orElse("00:00:00");
    }

    public Duration getTotalUptime(User user, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime start = fromDate.atStartOfDay();
        LocalDateTime end = toDate.atTime(23, 59, 59);

        List<UserSession> sessions = sessionRepository.findByUserAndLoginTimeBetween(user, start, end);

        return sessions.stream()
                .map(session -> {
                    LocalDateTime loginTime = session.getLoginTime();
                    LocalDateTime logoutTime = session.getLogoutTime();

                    // Handle cases where the session is still active
                    if (logoutTime == null) {
                        logoutTime = LocalDateTime.now();
                    }

                    // Ensure times are within the requested range
                    if (loginTime.isBefore(start)) loginTime = start;
                    if (logoutTime.isAfter(end)) logoutTime = end;

                    return Duration.between(loginTime, logoutTime);
                })
                .reduce(Duration.ZERO, Duration::plus);
    }

    public List<UserSession> getRecentSessions(User user, int limit) {
        return sessionRepository.findTop10ByUserOrderByLoginTimeDesc(user);
    }

    public void logActivity(User user, UserSession session, String activityType, String endpoint, String method) {
        UserActivity activity = new UserActivity(user, session, activityType, endpoint, method);
        activityRepository.save(activity);
    }

    public String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
    }
}
