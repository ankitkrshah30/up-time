package com.bahikhata.up_time.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document(collection="user_session")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {

    @Id
    private String id;
    @DBRef
    private User user;
    private String sessionId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String ipAddress;
    private String userAgent;
    private String deviceInfo;
    private boolean active;
    @CreatedDate
    private LocalDateTime createdAt;
    private Map<String,Object> metaData=new HashMap<>();

    public UserSession(User user, String sessionId, String ipAddress, String userAgent) {
        this.user = user;
        this.sessionId = sessionId;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.active=true;
        this.loginTime=LocalDateTime.now();
    }

    public Duration getCurrentDuration(){
        LocalDateTime endTime = logoutTime != null ? logoutTime : LocalDateTime.now();
        return Duration.between(loginTime, endTime);
    }

    public String getFormattedDuration(){
        Duration duration= getCurrentDuration();
        long hours=duration.toHours();
        long minutes=duration.toMinutesPart();
        long seconds=duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
