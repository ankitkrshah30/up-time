package com.bahikhata.up_time.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document(collection="user_activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActivity {

    @Id
    private String id;
    @DBRef
    private User user;
    @DBRef
    private UserSession session;
    private String activityType;
    private String endPoint;
    private String method;
    private Map<String, Object> metadata = new HashMap<>();

    @CreatedDate
    private LocalDateTime timestamp;

    public UserActivity(User user, UserSession session, String activityType, String endPoint, String method) {
        this.user = user;
        this.session = session;
        this.activityType = activityType;
        this.endPoint = endPoint;
        this.method = method;
    }
}
